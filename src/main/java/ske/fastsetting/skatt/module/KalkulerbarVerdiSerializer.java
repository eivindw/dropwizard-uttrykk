package ske.fastsetting.skatt.module;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ske.fastsetting.skatt.domene.KalkulerbarVerdi;

import java.io.IOException;

public class KalkulerbarVerdiSerializer extends StdSerializer<KalkulerbarVerdi> {

    public KalkulerbarVerdiSerializer() {
        super(KalkulerbarVerdi.class);
    }

    @Override
    public void serialize(KalkulerbarVerdi value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(value.toString());
    }
}
