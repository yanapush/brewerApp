package com.yanapush.BrewerApp.constant;

import org.springframework.stereotype.Component;

@Component
public class MessageConstants {
    public final String SUCCESS_ADDING = "%s was successfully added";
    public final String ERROR_ADDING = "%s wasn't added";
    public final String ERROR_DELETING = "%s wasn't deleted";
    public final String SUCCESS_DELETING = "%s was successfully deleted";
    public final String IS_FORBIDDEN = "deleting %s is forbidden";
    public final String ERROR_GETTING = "no  such %s";
    public final String ERROR_GETTING_BY_ID = "no %s with id=%d";

    public static final String VALIDATION_MESSAGE_MIN = "value should be greater or equals 1";
    public static final String VALIDATION_MESSAGE_MAX = "value should be less or equals 5";
    public final String VALIDATION_NAME = "Field must have a name";
    public static final String VALIDATION_FIELD = "Field must be initialized";
}



