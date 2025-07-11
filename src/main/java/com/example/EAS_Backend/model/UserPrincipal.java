package com.example.EAS_Backend.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class UserPrincipal implements UserDetails {
    private final AdminUsers adminUsers;

    public UserPrincipal(AdminUsers adminUsers) {
        this.adminUsers = adminUsers;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+adminUsers.getRole()));
    }

    @Override
    public String getPassword() {
        return adminUsers.getPassword();
    }

    @Override
    public String getUsername() {
        return adminUsers.getUserName();
    }
}
