package com.springsport.core.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class OAuth2ClientConfig {

    @Value("${app.security.oauth2.client.id}")
    private String clientId;

    @Value("${app.security.oauth2.client.redirectUri}")
    private String redirectUri;

    @Value("${app.security.oauth2.client.authorizationUri}")
    private String authorizationUri;

    @Value("${app.security.oauth2.client.tokenUri}")
    private String tokenUri;

    @Value("${app.security.oauth2.client.userInfoUri}")
    private String userInfoUri;

    @Value("${app.security.oauth2.client.userNameAttribute:sub}")
    private String userNameAttribute;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("my-client-dev")
                .clientId(clientId)
                .clientSecret("")
                .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(redirectUri)
                .scope("openid", "profile")
                .authorizationUri(authorizationUri)
                .tokenUri(tokenUri)
                .userInfoUri(userInfoUri)
                .userNameAttributeName(userNameAttribute)
                .clientName("OAuth2 Client")
                .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }
}
