package com.yanapush.BrewerApp.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.yanapush.BrewerApp.entity.Characteristic;

import java.io.IOException;

public class CustomCharacteristicSerializer extends StdSerializer<Characteristic> {

    public CustomCharacteristicSerializer() {
        this(null);
    }

    public CustomCharacteristicSerializer(Class<Characteristic> t) {
        super(t);
    }

    @Override
    public void serialize(Characteristic value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("characteristic_id", value.getId());
        jgen.writeNumberField("acidity", value.getAcidity());
        jgen.writeNumberField("sweetness", value.getSweetness());
        jgen.writeNumberField("density", value.getDensity());
        jgen.writeNumberField("bitterness", value.getBitterness());
        jgen.writeEndObject();
    }
}
