package com.threerivers.challenge.balanceservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threerivers.challenge.balanceservice.util.LocalDateTimeSerializer;

@Entity
public class Balance {
	
	@Id
	private String accountNumber;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime lastUpdateTimestamp;
	private BigDecimal balance;
	
	
	
	public Balance() {
		super();
	}
	
	public Balance(String accountNumber, BigDecimal balance) {
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

	@Override
	public String toString() {
		return String.format("{accountNumber=%s, lastUpdateTimestamp=%s, balance=%s}", accountNumber,
				lastUpdateTimestamp, balance);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((lastUpdateTimestamp == null) ? 0 : lastUpdateTimestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Balance other = (Balance) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (lastUpdateTimestamp == null) {
			if (other.lastUpdateTimestamp != null)
				return false;
		} else if (!myDateEquals(lastUpdateTimestamp, other.lastUpdateTimestamp))
			return false;
		return true;
	}
	
	private boolean myDateEquals(LocalDateTime date1, LocalDateTime date2) {
		return (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth()
				&& date1.getDayOfMonth() == date2.getDayOfMonth() && date1.getHour() == date2.getHour()
				&& date1.getMinute() == date2.getMinute() && date1.getSecond() == date2.getSecond());
	}


}
