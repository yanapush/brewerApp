package com.yanapush.BrewerApp.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vladmihalcea.hibernate.type.json.JsonType;
import com.yanapush.BrewerApp.BaseEntity;
import com.yanapush.BrewerApp.coffee.Coffee;
import com.yanapush.BrewerApp.characteristic.Characteristic;
import com.yanapush.BrewerApp.user.User;
import com.yanapush.BrewerApp.characteristic.CustomCharacteristicSerializer;
import com.yanapush.BrewerApp.coffee.CustomCoffeeSerializer;
import com.yanapush.BrewerApp.user.CustomUserSerializer;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Parameter;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames =
        { "recipe_name", "author" }) })
@TypeDefs(@TypeDef(name = "json", typeClass = JsonType.class))
//@Data
@Setter
@Getter
public class Recipe extends BaseEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
//    @SequenceGenerator(name = "my_seq", sequenceName = "my_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = MessageConstants.VALIDATION_RECIPE_NAME)
    private String recipe_name;

    @Positive
    private int grind_size;

    @Positive (message = MessageConstants.VALIDATION_COFFEE_WEIGHT)
    private double coffee_weight;

    @Positive (message = MessageConstants.VALIDATION_WATER_VOLUME)
    private int water_volume;

    @Length(max = 230, message = MessageConstants.VALIDATION_RECIPE_DESCRIPTION)
    private String description;

    @NotNull(message = MessageConstants.VALIDATION_BREWER)
    private String brewer;

    @ColumnDefault("94")
    @PositiveOrZero
    private int water_temperature;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<@NotNull Step> steps;

    @Transient
    @JsonIgnore
    ObjectMapper objectMapper = new ObjectMapper();

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonSerialize(using = CustomCoffeeSerializer.class)
//    @NotNull(message = MessageConstants.VALIDATION_COFFEE)
    private Coffee coffee;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.REFRESH})
    @JoinColumn(name = "author")
    @JsonSerialize(using = CustomUserSerializer.class)
    @NotNull(message = MessageConstants.VALIDATION_AUTHOR)
    private User author;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "characteristic", referencedColumnName = "id")
    @JsonSerialize(using = CustomCharacteristicSerializer.class)
    private Characteristic characteristic;

    public void addStep(Step step) {
        if (steps == null) {
            steps = new ArrayList<>();
        }
        steps.add(step);
    }
}