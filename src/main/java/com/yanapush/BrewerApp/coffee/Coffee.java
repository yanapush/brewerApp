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
@EqualsAndHashCode
@NoArgsConstructor
public class Coffee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = MessageConstants.VALIDATION_COFFEE_NAME)
    @Column( unique = true)
    private String coffee_name;
    private String country;
    @NotNull(message = MessageConstants.VALIDATION_COFFEE_PROCESS)
    private String process;
    @Length(max = 50, message = MessageConstants.VALIDATION_COFFEE_DESCRIPTION)
    private String description;

    @OneToMany(mappedBy = "coffee", cascade = CascadeType.ALL)
    private List<Recipe> recipes;

    public Coffee(String coffee_name, String country, String process) {
        this.coffee_name = coffee_name;
        this.country = country;
        this.process = process;
    }

    public Coffee(int id, String coffee_name, String country, String process) {
        this.id = id;
        this.coffee_name = coffee_name;
        this.country = country;
        this.process = process;
    }

    public void addRecipe(Recipe recipe) {
        if (recipes == null) {
            recipes = new LinkedList<>();
        }
        recipes.add(recipe);
    }
}
