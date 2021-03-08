package com.threerivers.challenge.transactionservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.threerivers.challenge.transactionservice.model.Transaction;
import com.threerivers.challenge.transactionservice.repository.TransactionRepository;

@Service
public class KafkaConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
	private TransactionRepository transactionDAO;

	@KafkaListener(topics = "${kafka.topic.transaction.name}", groupId = "${kafka.consumer.group.id}")
	public void receiveData(Transaction transaction){
		logger.info("consumed transaction: {}", transaction);
		transactionDAO.save(transaction);
	}

	public KafkaConsumer(TransactionRepository transactionDAO) {
		super();
		this.transactionDAO = transactionDAO;
	}

}
