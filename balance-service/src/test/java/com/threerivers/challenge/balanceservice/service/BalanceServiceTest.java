package com.threerivers.challenge.balanceservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.threerivers.challenge.balanceservice.model.Balance;
import com.threerivers.challenge.balanceservice.repository.BalanceRepository;

@SpringBootTest
public class BalanceServiceTest {

	@Autowired
	private BalanceService balanceService;

	@MockBean
	private BalanceRepository balanceRepository;

	@Test
	public void getBalanceMustCallFindBalanceByAccountNumber() {
		Balance expectedBalance = new Balance("123", new BigDecimal(100.00));

		when(balanceRepository.findByAccountNumber(any(String.class))).thenReturn(expectedBalance);

		Balance balance = balanceService.getBalance("123");

		verify(balanceRepository, times(1)).findByAccountNumber("123");
		assertEquals(expectedBalance, balance);

	}
}
