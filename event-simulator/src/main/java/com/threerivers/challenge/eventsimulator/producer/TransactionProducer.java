package com.threerivers.challenge.eventsimulator.producer;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.threerivers.challenge.eventsimulator.model.Transaction;
import com.threerivers.challenge.eventsimulator.model.TransactionType;
import com.threerivers.challenge.eventsimulator.sender.KafkaSender;

@Component
public class TransactionProducer extends Thread{
	private KafkaSender kafkaSender;
	private ProducerUtil producerUtil;
	
	public TransactionProducer(KafkaSender kafkaSender, ProducerUtil producerUtil) {
		super();
		this.kafkaSender = kafkaSender;
		this.producerUtil = producerUtil;
	}
	public void run(){
		while(true) {
	       try {
	    	   String accountNumber = producerUtil.selectAccountNumber();
	    	   BigDecimal amount = producerUtil.generateAmount();
	    	   TransactionType type = producerUtil.generateType();
	    	   Transaction transaction = new Transaction(accountNumber, type, amount);
	    	   kafkaSender.sendTransaction(transaction);
	    	   System.out.println("transaction");
	    	   sleep(5000);
	       } catch (InterruptedException e) {
			e.printStackTrace();
	       }
		}
	}
}
