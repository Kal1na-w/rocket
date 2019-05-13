package ua.od.atomspace.rocket.domain;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,MENTOR,ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
