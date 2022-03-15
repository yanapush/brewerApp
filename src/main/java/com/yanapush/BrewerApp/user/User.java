package com.yanapush.BrewerApp.user;


import com.yanapush.BrewerApp.BaseEntity;
import com.yanapush.BrewerApp.user.role.Role;
import com.yanapush.BrewerApp.recipe.Recipe;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users", schema = "public")
@Getter
@Setter
public class User extends BaseEntity {
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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
}
