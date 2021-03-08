package com.threerivers.challenge.balanceservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threerivers.challenge.balanceservice.model.Balance;
import com.threerivers.challenge.balanceservice.service.BalanceService;

@WebMvcTest(BalanceController.class)
public class BalanceControllerTest {

	@Autowired
	private MockMvc mockMVC;

	@MockBean
	private BalanceService balanceService;

	@Test
	public void balanceEndpointMustReturnTheBalance() throws Exception {
		LocalDateTime updateTime = LocalDateTime.now();
		Balance balance = new Balance();
		balance.setAccountNumber("123");
		balance.setBalance(new BigDecimal(100.00));
		balance.setLastUpdateTimestamp(updateTime);

		String jsonResponse = new ObjectMapper().writeValueAsString(balance);

		when(balanceService.getBalance(any(String.class))).thenReturn(balance);

		mockMVC.perform(get("/balance/123").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().json(jsonResponse, false));

		verify(balanceService, times(1)).getBalance(eq("123"));
	}

	@Test
	public void balanceEndpointMustReturnNotFoundWhenAccountNotFound() throws Exception {

		when(balanceService.getBalance(any(String.class))).thenReturn(null);

		mockMVC.perform(get("/balance/123").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isNotFound());

		verify(balanceService, times(1)).getBalance(eq("123"));
	}
}
