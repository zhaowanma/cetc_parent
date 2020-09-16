package com.cetc.authentication.service.impl;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

import java.util.UUID;

public class RandomAuthenticationKeyGenerator implements AuthenticationKeyGenerator {
    @Override
    public String extractKey(OAuth2Authentication oAuth2Authentication) {
        return UUID.randomUUID().toString();
    }
}
