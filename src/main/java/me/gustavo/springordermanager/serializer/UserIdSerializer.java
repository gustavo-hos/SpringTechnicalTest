package me.gustavo.springordermanager.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import me.gustavo.springordermanager.model.User;

import java.io.IOException;

public class UserIdSerializer extends StdSerializer<User> {

    public UserIdSerializer() {
        this(null);
    }

    protected UserIdSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("id");
        gen.writeNumber(value.getId());
        gen.writeEndObject();
    }
}
