package com.yanapush.BrewerApp.entity;


import javax.persistence.*;

import javax.persistence.Id;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private boolean enabled;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Role> roles;


    public User() {
    }

    public String getUsername() {
        return username;
    }

    public boolean isEnabled() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        System.out.println("++++++++++ password is " + password);
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(Recipe recipe) {
        if (recipes == null) {
            recipes = new LinkedList<>();
        }
        recipes.add(recipe);
    }

    @Override
    public String toString() {
        return "User{" + "username='" + username + '\'' + '}';
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new LinkedList<>();
        }
        roles.add(role);
    }
}
