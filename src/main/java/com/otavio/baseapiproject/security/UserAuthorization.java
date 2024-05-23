package com.otavio.baseapiproject.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class UserAuthorization implements AuthorizationManager<RequestAuthorizationContext> {
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
//        UserDetails uSerDetails = (UserDetails) authentication.get().getPrincipal();
//        Map<String ,String> variables = object.getVariables();
//        Long userid = Long.parseLong(variables.get("id"));
        return new AuthorizationDecision(true);
    }
}
