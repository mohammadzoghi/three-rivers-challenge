package com.threerivers.challenge.balanceservice.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

	public LocalDateTimeSerializer() {
		super(LocalDateTime.class);
	}
	
	@Override
    public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(value.format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
