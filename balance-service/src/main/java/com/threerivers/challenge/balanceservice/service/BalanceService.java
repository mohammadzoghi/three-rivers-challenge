package com.threerivers.challenge.balanceservice.service;

import org.springframework.stereotype.Service;

import com.threerivers.challenge.balanceservice.model.Balance;
import com.threerivers.challenge.balanceservice.repository.BalanceRepository;

@Service
public class BalanceService {
	
	BalanceRepository balanceRepository;
	
	public Balance getBalance(String accountNumber) {
		return balanceRepository.findByAccountNumber(accountNumber);
	}

	public BalanceService(BalanceRepository balanceRepository) {
		super();
		this.balanceRepository = balanceRepository;
	};
	

}
