package com.yanapush.BrewerApp.security;

public class UserInfo {

    private String username;

    private Object roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public Object getRoles() {
        return roles;
    }

    public void setRoles(Object roles) {
        this.roles = roles;
    }
}
