package com.yanapush.BrewerApp.characteristic;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yanapush.BrewerApp.recipe.Recipe;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "characteristics")
@JsonSerialize(using = CustomCharacteristicSerializer.class)
@Data
public class Characteristic {

    @Id
    private int id;

    @Min(value = 1, message = MessageConstants.VALIDATION_MESSAGE_MIN)
    @Max(value = 5, message = MessageConstants.VALIDATION_MESSAGE_MAX)
    private int acidity;

    @Min(value = 1, message = MessageConstants.VALIDATION_MESSAGE_MIN)
    @Max(value = 5, message = MessageConstants.VALIDATION_MESSAGE_MAX)
    private int sweetness;

    @Min(value = 1, message = MessageConstants.VALIDATION_MESSAGE_MIN)
    @Max(value = 5, message = MessageConstants.VALIDATION_MESSAGE_MAX)
    private int density;

    @Min(value = 1, message = MessageConstants.VALIDATION_MESSAGE_MIN)
    @Max(value = 5, message = MessageConstants.VALIDATION_MESSAGE_MAX)
    private int bitterness;

    @OneToOne(mappedBy = "characteristic", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @NotNull(message = MessageConstants.VALIDATION_RECIPE)
    private Recipe recipe;
}
