package com.simoes.customerservice.service;

import com.simoes.customerservice.domain.Customer;
import com.simoes.customerservice.exception.BadRequestException;
import com.simoes.customerservice.exception.BaseException;
import com.simoes.customerservice.service.customer.CustomerPersistenceService;
import com.simoes.customerservice.service.customer.CustomerReadService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

public class CustomerPersistenceServiceTest {

	@InjectMocks
	private CustomerPersistenceService persistenceService;

	@Mock
	private CustomerReadService readService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void whenInvalidCPFThenBadRequestException() {
		Customer customer = Customer
			.builder()
			.code("000.000.000-00")
			.build();

		BaseException result = (BaseException) catchThrowable(() -> persistenceService.create(customer));

		assertThat(result).isExactlyInstanceOf(BadRequestException.class);
		assertThat(result.getStatus()).isEqualTo(400);
		assertThat(result.getTitle()).isEqualTo("Erro na criação");
		assertThat(result.getMessage()).isEqualTo("CPF inválido");
	}

	@Test
	public void whenCustomerUnderAgeThenBadRequestException() {
		Customer customer = Customer
			.builder()
			.code("628.055.120-22")
			.birthdate(LocalDate.parse("2010-01-01"))
			.build();

		BaseException result = (BaseException) catchThrowable(() -> persistenceService.create(customer));

		assertThat(result).isExactlyInstanceOf(BadRequestException.class);
		assertThat(result.getStatus()).isEqualTo(400);
		assertThat(result.getTitle()).isEqualTo("Erro na criação");
		assertThat(result.getMessage()).isEqualTo("O cliente precisa ser maior de idade");
	}

	@Test
	public void whenUpdateCustomerWithExistCPFThenBadRequestException() {
		Customer customer = Customer
			.builder()
			.id("uuid")
			.code("628.055.120-22")
			.birthdate(LocalDate.parse("2000-01-01"))
			.build();

		when(readService.findById(customer.getId())).thenReturn(Customer.builder().code("214.944.120-98").build());
		when(readService.findByCode(customer.getCode())).thenReturn(Optional.of(Customer.builder().build()));

		BaseException result = (BaseException) catchThrowable(() -> persistenceService.update(customer));

		assertThat(result).isExactlyInstanceOf(BadRequestException.class);
		assertThat(result.getStatus()).isEqualTo(400);
		assertThat(result.getTitle()).isEqualTo("Erro na criação");
		assertThat(result.getMessage()).isEqualTo("Já existe um cliente com o CPF enviado");
	}

}
