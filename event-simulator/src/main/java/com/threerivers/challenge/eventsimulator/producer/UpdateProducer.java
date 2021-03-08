package com.threerivers.challenge.eventsimulator.producer;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.threerivers.challenge.eventsimulator.sender.KafkaSender;
import com.threerivers.challenge.eventsimulator.model.Update;

@Component
public class UpdateProducer extends Thread{
	private KafkaSender kafkaSender;
	private ProducerUtil producerUtil;
	
	public UpdateProducer(KafkaSender kafkaSender, ProducerUtil producerUtil) {
		super();
		this.kafkaSender = kafkaSender;
		this.producerUtil = producerUtil;
	}

	public void run(){
		while(true) {
	       try {
	    	   String accountNumber = producerUtil.selectAccountNumber();
	    	   BigDecimal balance = producerUtil.generateAmount();
	    	   Update update = new Update(accountNumber, balance);
	    	   kafkaSender.sendUpdate(update);
	    	   System.out.println("update");
	    	   sleep(5000);
	       } catch (InterruptedException e) {
			e.printStackTrace();
	       }
		}
	}
	
	
}
