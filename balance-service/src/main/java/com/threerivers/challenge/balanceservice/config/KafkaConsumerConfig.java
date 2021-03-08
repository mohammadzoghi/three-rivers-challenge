package com.threerivers.challenge.balanceservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import com.threerivers.challenge.balanceservice.model.Balance;
import com.threerivers.challenge.balanceservice.serde.CustomJsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${kafka.bootstrap.servers}")
	private String kafkaServer;

	@Value("${kafka.consumer.group.id}")
	private String kafkaGroupId;

	@Bean
	public ConsumerFactory<String, Balance> consumerConfig() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomJsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(config, null, new CustomJsonDeserializer<Balance>(Balance.class));
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Balance>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Balance> listener = new ConcurrentKafkaListenerContainerFactory<String, Balance>();
		listener.setConsumerFactory(consumerConfig());
		return listener;
	}
}
