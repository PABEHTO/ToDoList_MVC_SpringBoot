package com.apress.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
    USER,
    ADMIN;

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority("ROLE_" + this.name());
    }
}
