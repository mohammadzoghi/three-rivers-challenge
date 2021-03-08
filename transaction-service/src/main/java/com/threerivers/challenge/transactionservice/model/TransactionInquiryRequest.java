package com.threerivers.challenge.transactionservice.model;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TransactionInquiryRequest {

	@NotNull
	private String accountNumber;

	@NotNull
	private LocalDateTime start;

	@NotNull
	private LocalDateTime end;

	private TransactionType type;

	@Min(value = 0, message = "Negative values are invalid for page Number")
	@NotNull
	private int pageNumber;

	@Min(value = 1, message = "Page size must be at least 1")
	@Max(value = 20, message = "Page size must be less than 20")
	@NotNull
	private int pageSize;

	public TransactionInquiryRequest() {
		super();
	}

	public TransactionInquiryRequest(String accountNumber, LocalDateTime start, LocalDateTime end, TransactionType type,
			int pageNumber, int pageSize) {
		super();
		this.accountNumber = accountNumber;
		this.start = start;
		this.end = end;
		this.type = type;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + pageNumber;
		result = prime * result + pageSize;
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		TransactionInquiryRequest other = (TransactionInquiryRequest) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!myDateEquals(end, other.end))
			return false;
		if (pageNumber != other.pageNumber)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!myDateEquals(start, other.start))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	private boolean myDateEquals(LocalDateTime date1, LocalDateTime date2) {
		return (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth()
				&& date1.getDayOfMonth() == date2.getDayOfMonth() && date1.getHour() == date2.getHour()
				&& date1.getMinute() == date2.getMinute() && date1.getSecond() == date2.getSecond());
	}

}
