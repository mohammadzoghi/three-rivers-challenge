package com.threerivers.challenge.eventsimulator.sender;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import com.threerivers.challenge.eventsimulator.model.Transaction;
import com.threerivers.challenge.eventsimulator.model.Update;

@Component
public class KafkaSender {
	
	@Value("${kafka.topic.update.name}")
	private String updateTopic;

	@Value("${kafka.topic.transaction.name}")
	private String transactionTopic;
	
	private KafkaTemplate<String, String> kafkaTemplate;

	public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendUpdate(Update update) {
		Map<String, Object> headers = new HashMap<>();
		headers.put(KafkaHeaders.TOPIC, updateTopic);
		kafkaTemplate.send(new GenericMessage<Update>(update, headers));
	}
	
	public void sendTransaction(Transaction transaction) {
		Map<String, Object> headers = new HashMap<>();
		headers.put(KafkaHeaders.TOPIC, transactionTopic);
		kafkaTemplate.send(new GenericMessage<Transaction>(transaction, headers));
	}
	
}
