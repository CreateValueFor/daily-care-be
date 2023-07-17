package com.example.dailycarebe.auth;

import com.example.dailycarebe.auth.authentication.AppUserDetails;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public class CustomJwtAuthentication implements Authentication {

    private final AppUserDetails userDetails;

    private final String tokenString;

    private final String username;

    private final Collection<? extends GrantedAuthority> authorities;

    private boolean authenticated;

    @JsonCreator
    public CustomJwtAuthentication(@JsonProperty("details") AppUserDetails userDetails
      , String tokenString
      , String username
      , @JsonDeserialize(contentAs = SimpleGrantedAuthority.class) Collection<? extends GrantedAuthority> authorities
      , boolean authenticated) {
        this.userDetails = userDetails;
        this.tokenString = tokenString;
        this.username = username;
        this.authorities = authorities;
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getTokenString() {
        return tokenString;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot mark this as authenticated!");
        }

        this.authenticated = false;
    }
}
