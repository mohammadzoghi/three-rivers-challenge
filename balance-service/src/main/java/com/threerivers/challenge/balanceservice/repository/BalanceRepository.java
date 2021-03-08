package com.threerivers.challenge.balanceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threerivers.challenge.balanceservice.model.Balance;


public interface BalanceRepository extends JpaRepository<Balance, Long>{
	Balance findByAccountNumber(String accountNumber);
}
