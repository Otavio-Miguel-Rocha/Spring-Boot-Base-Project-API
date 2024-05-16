package com.otavio.baseapiproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class JwtUtil {
    public String generateToken(UserDetails userDetails){
        Algorithm algorithm =
                Algorithm.HMAC256("VARIAVEL_AMBIENTE");
        return JWT.create().
                withIssuer("VARIAVEL_AMBIENTE")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 300000))
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
    }


    public String getUsername(String token) {
        return JWT.decode(token).getSubject();
    }
}
