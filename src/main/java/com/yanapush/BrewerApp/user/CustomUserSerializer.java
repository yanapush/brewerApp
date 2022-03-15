package com.yanapush.BrewerApp.user;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomUserSerializer extends StdSerializer<User> {

    public CustomUserSerializer() {
        this(null);
    }

    public CustomUserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(
            User value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("username", value.getUsername());
        jgen.writeStringField("email", value.getEmail());
        jgen.writeStringField("password", value.getPassword());
        jgen.writeEndObject();
    }
}