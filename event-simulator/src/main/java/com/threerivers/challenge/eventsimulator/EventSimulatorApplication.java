package com.threerivers.challenge.eventsimulator;

import org.springframework.context.ApplicationContext;

import com.threerivers.challenge.eventsimulator.producer.TransactionProducer;
import com.threerivers.challenge.eventsimulator.producer.UpdateProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventSimulatorApplication {

	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EventSimulatorApplication.class, args);
		Thread updareProducer = context.getBean(UpdateProducer.class);
		updareProducer.start();
		Thread transactionProducer = context.getBean(TransactionProducer.class);
		transactionProducer.start();
	}

}
