package com.simoes.customerservice.service;

import com.simoes.customerservice.exception.BaseException;
import com.simoes.customerservice.exception.NotFoundException;
import com.simoes.customerservice.repository.CustomerRepository;
import com.simoes.customerservice.service.customer.CustomerReadService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

public class CustomerReadServiceTest {

	@InjectMocks
	private CustomerReadService readService;

	@Mock
	private CustomerRepository repository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void whenFindNonexistentCustomerThenNotFoundException() {
		String id = "any-uuid";
		when(repository.findById(id)).thenReturn(Optional.empty());
		BaseException result = (BaseException) catchThrowable(() -> readService.findById(id));

		assertThat(result).isExactlyInstanceOf(NotFoundException.class);
		assertThat(result.getStatus()).isEqualTo(404);
		assertThat(result.getTitle()).isEqualTo("Not Found");
		assertThat(result.getMessage()).isEqualTo(String.format("Customer not found: %s", id));
	}

}
