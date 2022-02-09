package com.yanapush.BrewerApp.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yanapush.BrewerApp.serializer.CustomRoleSerializer;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@JsonSerialize(using = CustomRoleSerializer.class)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String authority;
    @ManyToOne()
    @JoinColumn(name = "username")
    private User user;


    public Role() {}

    public int getId() {
        return id;
    }

    public void setId(int authority_id) {
        this.id = authority_id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String name) {
        this.authority = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
