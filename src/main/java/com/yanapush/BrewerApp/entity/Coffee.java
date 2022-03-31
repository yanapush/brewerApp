package com.yanapush.BrewerApp.entity;

import com.yanapush.BrewerApp.constant.MessageConstants;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = MessageConstants.VALIDATION_FIELD)
    @Column(unique = true)
    private String coffee_name;
    private String country;
    @NotNull(message = MessageConstants.VALIDATION_FIELD)
    private String process;
    @Length(max = 50, message = MessageConstants.VALIDATION_FIELD)
    private String description;

    @OneToMany(mappedBy = "coffee", cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.REMOVE})
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
