package com.yanapush.BrewerApp.coffee;

import com.yanapush.BrewerApp.BaseEntity;
import com.yanapush.BrewerApp.recipe.Recipe;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Coffee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coffee_id")
    private int id;
    @NotNull(message = MessageConstants.VALIDATION_COFFEE_NAME)
    @Column(unique = true)
    private String coffee_name;
    private String country;
    @NotNull(message = MessageConstants.VALIDATION_COFFEE_PROCESS)
    private String process;
    @Length(max = 50, message = MessageConstants.VALIDATION_COFFEE_DESCRIPTION)
    private String description;

    @OneToMany(mappedBy = "coffee", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Recipe> recipes;

    public void addRecipe(Recipe recipe) {
        if (recipes == null) {
            recipes = new LinkedList<>();
        }
        recipes.add(recipe);
    }
}
