package com.example.dailycarebe.auth.dto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomSocialAuthentication implements Authentication {
    private final UserDetails userDetails;

    private final String username;

    private final Collection<? extends GrantedAuthority> authorities;

    private boolean authenticated;

    public CustomSocialAuthentication(UserDetails userDetails, String username, Collection<? extends GrantedAuthority> authorities, boolean authenticated) {
        this.userDetails = userDetails;
        this.username = username;
        this.authorities = authorities;
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
