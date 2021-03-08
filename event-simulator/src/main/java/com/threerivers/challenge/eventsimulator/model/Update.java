package com.threerivers.challenge.eventsimulator.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Update {
	
	private String accountNumber;
	private LocalDateTime lastUpdateTimestamp;
	private BigDecimal balance;
	
	
	
	public Update() {
		super();
	}
	
	public Update(String accountNumber, BigDecimal balance) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.lastUpdateTimestamp = LocalDateTime.now();
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public LocalDateTime getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}
	public void setLastUpdateTimestamp(LocalDateTime lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	

}
