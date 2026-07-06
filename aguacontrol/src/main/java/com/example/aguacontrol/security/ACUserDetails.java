package com.example.aguacontrol.security;

import com.example.aguacontrol.model.Rol;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class ACUserDetails implements UserDetails {
    private final Long id;
    private final String username;
    private final String email;
    private final String password;

    private final Set<Rol> roles;
    private final List<? extends GrantedAuthority> authorities;

    @Override
    @NullMarked
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
