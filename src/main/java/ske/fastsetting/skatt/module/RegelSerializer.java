package ske.fastsetting.skatt.module;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ske.fastsetting.skatt.domene.Regel;

import java.io.IOException;

public class RegelSerializer extends StdSerializer<Regel> {

    public RegelSerializer() {
        super(Regel.class);
    }

    @Override
    public void serialize(Regel value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectField("navn", value.kortnavnOgParagraf());
        jgen.writeObjectField("uri", value.uri());
        jgen.writeEndObject();
    }
}
