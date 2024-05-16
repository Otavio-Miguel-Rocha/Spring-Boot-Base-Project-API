package com.otavio.baseapiproject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class FilterAuthentication extends OncePerRequestFilter {
    private final AuthenticationService authenticationService;
    private final SecurityContextRepository securityContextRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtUtil jwtUtils = new JwtUtil();
        CookieUtil cookieUtils = new CookieUtil();
        if(!isPublicRouter(request)){
            Cookie cookie;
            try {
                cookie = cookieUtils.getCookie(request, "JWT");
            } catch (Exception e) {
                response.setStatus(401);
                return;
            }

            String token = cookie.getValue();
            //Validates the token and creation of authenticate user
            String email = jwtUtils.getUsername(token);
            UserDetails userDetails = authenticationService.loadUserByUsername(email);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            userDetails.getPassword(),
                            userDetails.getAuthorities());

            //Save this temporarily in the Security Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response);

            //Renovação do JWT/Cookie
            response.addCookie(cookieUtils.generateCookieJWT(userDetails));
        }


        filterChain.doFilter(request, response);
    }

    private boolean isPublicRouter(HttpServletRequest request){
        String method = request.getMethod();
        String uri = request.getRequestURI();
        return uri.equals("/api/login") && method.equals("POST");
    }
}
