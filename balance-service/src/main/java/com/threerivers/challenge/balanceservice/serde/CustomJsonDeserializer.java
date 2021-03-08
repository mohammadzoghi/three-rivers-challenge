package com.threerivers.challenge.balanceservice.serde;

import org.springframework.kafka.support.serializer.JsonDeserializer;

public class CustomJsonDeserializer<T> extends JsonDeserializer<T> {
	public CustomJsonDeserializer(Class<? super T> targetType) {
		super(targetType);

		// add our packages
		this.ignoreTypeHeaders();
	}
}