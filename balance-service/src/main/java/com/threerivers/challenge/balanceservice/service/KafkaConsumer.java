package com.threerivers.challenge.balanceservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.threerivers.challenge.balanceservice.model.Balance;
import com.threerivers.challenge.balanceservice.repository.BalanceRepository;

@Service
public class KafkaConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
	private BalanceRepository balanceRepository;

	@KafkaListener(topics = "${kafka.topic.update.name}", groupId = "${kafka.consumer.group.id}")
	public void receiveData(Balance balance){
		logger.info("Consumed balance update: {}", balance.toString());
		balanceRepository.save(balance);
	}

	public KafkaConsumer(BalanceRepository balanceRepository) {
		super();
		this.balanceRepository = balanceRepository;
	}

}
