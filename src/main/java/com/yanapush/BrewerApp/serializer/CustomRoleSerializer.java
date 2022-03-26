package com.yanapush.BrewerApp.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.yanapush.BrewerApp.entity.Role;

import java.io.IOException;

public class CustomRoleSerializer extends StdSerializer<Role> {

    public CustomRoleSerializer() {
        this(null);
    }

    public CustomRoleSerializer(Class<Role> t) {
        super(t);
    }

    @Override
    public void serialize(Role value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("authority", value.getAuthority());
        jgen.writeEndObject();
    }
}