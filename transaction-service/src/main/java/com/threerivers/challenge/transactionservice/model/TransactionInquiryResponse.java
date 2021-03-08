package com.threerivers.challenge.transactionservice.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionInquiryResponse {
	private List<Transaction> transactions = new ArrayList<>();
	private long count;
	private int pageCount;
	
	public TransactionInquiryResponse(List<Transaction> transactions, long count, int pageCount) {
		super();
		this.transactions = transactions;
		this.count = count;
		this.pageCount = pageCount;
	}

	public TransactionInquiryResponse() {
		super();
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	
	
}
