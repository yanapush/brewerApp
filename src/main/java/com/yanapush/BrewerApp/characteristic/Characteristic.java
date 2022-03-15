package com.yanapush.BrewerApp.characteristic;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yanapush.BrewerApp.BaseEntity;
import com.yanapush.BrewerApp.recipe.Recipe;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "characteristics")
@JsonSerialize(using = CustomCharacteristicSerializer.class)
@Data
public class Characteristic extends BaseEntity {

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
}
