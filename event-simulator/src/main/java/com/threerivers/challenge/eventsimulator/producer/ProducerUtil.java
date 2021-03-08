package com.threerivers.challenge.eventsimulator.producer;

import java.math.BigDecimal;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.threerivers.challenge.eventsimulator.model.TransactionType;

@Component
public class ProducerUtil {
	
	@Value("${challenge.util.account-numbers}")
	private String[] accountNumbers;

	public String selectAccountNumber() {
		int index = new Random().nextInt(accountNumbers.length);
		return accountNumbers[index];
	}
	
	public BigDecimal generateAmount() {
		return new BigDecimal(Math.random() * 10000);
	}
	
	public TransactionType generateType() {
		return TransactionType.values()[new Random().nextInt(2)];
	}
}
