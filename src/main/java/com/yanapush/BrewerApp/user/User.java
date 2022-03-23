package com.yanapush.BrewerApp.user;


import com.yanapush.BrewerApp.BaseEntity;
import com.yanapush.BrewerApp.user.role.Role;
import com.yanapush.BrewerApp.recipe.Recipe;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users", schema = "public")
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(unique = true)
    @NotNull(message = MessageConstants.VALIDATION_USERNAME)
    private String username;

    @NotNull(message = MessageConstants.VALIDATION_PASSWORD)
    private String password;

    @Column(unique = true)
    @Email(message = MessageConstants.VALIDATION_EMAIL)
    private String email;

    @ColumnDefault("true")
    private boolean enabled;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<@NotNull Recipe> recipes;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<@NotNull Role> roles;

    public void addRecipe(Recipe recipe) {
        if (recipes == null) {
            recipes = new LinkedList<>();
        }
        recipes.add(recipe);
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new LinkedList<>();
        }
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }
}
