package com.threerivers.challenge.transactionservice.config;

import org.springframework.kafka.support.serializer.JsonDeserializer;

public class CustomJsonDeserializer<T> extends JsonDeserializer<T> {
	public CustomJsonDeserializer(Class<? super T> targetType) {
		// defaults from superclass
		super(targetType);

		// add our packages
		this.ignoreTypeHeaders();
	}
}