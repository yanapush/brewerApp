package com.yanapush.BrewerApp.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yanapush.BrewerApp.serializer.CustomCharacteristicSerializer;
import com.yanapush.BrewerApp.constant.MessageConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @OneToOne(mappedBy = "characteristic", cascade = CascadeType.ALL)
    private Recipe recipe;

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
