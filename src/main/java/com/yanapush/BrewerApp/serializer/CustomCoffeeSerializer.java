package com.yanapush.BrewerApp.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.yanapush.BrewerApp.entity.Coffee;

import java.io.IOException;

public class CustomCoffeeSerializer extends StdSerializer<Coffee> {

    public CustomCoffeeSerializer() {
        this(null);
    }

    public CustomCoffeeSerializer(Class<Coffee> t) {
        super(t);
    }

    @Override
    public void serialize(
            Coffee value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeStringField("coffee_name", value.getCoffee_name());
        jgen.writeStringField("country", value.getCountry());
        jgen.writeStringField("process", value.getProcess());
        jgen.writeStringField("description", value.getDescription());
        jgen.writeEndObject();
    }
}