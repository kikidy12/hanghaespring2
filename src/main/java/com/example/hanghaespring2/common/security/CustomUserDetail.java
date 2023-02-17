package com.example.hanghaespring2.common.security;

import com.example.hanghaespring2.common.entity.User;
import com.example.hanghaespring2.common.entity.UserRoleEnum;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


public class CustomUserDetail implements UserDetails {
    private final User user;

    private final String username;

    private final String password;

    public CustomUserDetail(User user, String username, String password) {
        this.user = user;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum role = user.getRole();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getAuthority());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

