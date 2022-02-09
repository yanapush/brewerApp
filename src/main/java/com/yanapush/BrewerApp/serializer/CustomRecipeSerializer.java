package com.yanapush.BrewerApp.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.yanapush.BrewerApp.entity.Recipe;

import java.io.IOException;

public class CustomRecipeSerializer extends StdSerializer<Recipe> {

    public CustomRecipeSerializer() {
        this(null);
    }

    public CustomRecipeSerializer(Class<Recipe> t) {
        super(t);
    }

    @Override
    public void serialize(Recipe value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("recipe_id", value.getRecipe_id());
        jgen.writeStringField("recipe_name", value.getRecipe_name());
        jgen.writeNumberField("grind_size", value.getGrind_size());
        jgen.writeNumberField("coffee_weight", value.getCoffee_weight());
        jgen.writeNumberField("water_volume", value.getWater_volume());
        jgen.writeStringField("description", value.getDescription());
        jgen.writeNumberField("water_temperature", value.getWater_temperature());
        jgen.writeObjectField("steps", value.getSteps());
        jgen.writeStringField("coffee", value.getCoffee().getCoffee_name());
        jgen.writeStringField("author", value.getAuthor().getUsername());
        jgen.writeObject(value.getCharacteristic());
        jgen.writeEndObject();
    }
}