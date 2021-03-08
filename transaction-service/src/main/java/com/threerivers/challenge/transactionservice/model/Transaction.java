package com.threerivers.challenge.transactionservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threerivers.challenge.transactionservice.util.LocalDateTimeSerializer;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue
	private long id;
	private String accountNumber;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime transactionTs;
	private TransactionType type;
	private BigDecimal amount;
	
	public Transaction(String accountNumber, TransactionType type, BigDecimal amount) {
		super();
		this.accountNumber = accountNumber;
		this.type = type;
		this.amount = amount;
		this.transactionTs =  LocalDateTime.now();
}
	
	public Transaction() {
		super();
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public LocalDateTime getTransactionTs() {
		return transactionTs;
	}
	public void setTransactionTs(LocalDateTime transactionTs) {
		this.transactionTs = transactionTs;
	}
	public TransactionType getType() {
		return type;
	}
	public void setType(TransactionType type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return String.format("{accountNumber=\"%s\", transactionTs=\"%s\", type=\"%s\", amount=%s}", accountNumber,
				transactionTs, type, amount);
	}
	
	
	
}
