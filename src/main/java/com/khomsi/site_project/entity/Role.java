package com.khomsi.site_project.entity;

import org.springframework.security.core.GrantedAuthority;


/* https://stackoverflow.com/questions/19525380/difference-between-role-and-grantedauthority-in-spring-security */
public enum Role implements GrantedAuthority {

    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
