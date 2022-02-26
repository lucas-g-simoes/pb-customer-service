package com.simoes.customerservice.service.customer;

import br.com.caelum.stella.validation.CPFValidator;
import com.simoes.customerservice.domain.Customer;
import com.simoes.customerservice.exception.BadRequestException;
import com.simoes.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CustomerPersistenceService {

	private final CustomerRepository repository;

	private final CustomerReadService readService;

	public CustomerPersistenceService(CustomerRepository repository, CustomerReadService readService) {
		this.repository = repository;
		this.readService = readService;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer create(Customer customer) {
		this.validateCPF(customer.getCode());
		this.validateAge(customer.getBirthdate());

		this.checkExistCPF(customer.getCode());

		return this.repository.save(customer);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer update(Customer customer) {
		Customer entity = this.readService.findById(customer.getId());

		this.validateCPF(customer.getCode());
		this.validateAge(customer.getBirthdate());

		if (!entity.getCode().equals(customer.getCode())) {
			this.checkExistCPF(customer.getCode());
		}

		return this.repository.save(customer);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Customer patch(String id, Customer customer) {
		Customer entity = this.readService.findById(id);

		if (customer.getName() != null) {
			entity.setName(customer.getName());
		}

		if (customer.getBirthdate() != null) {
			this.validateAge(customer.getBirthdate());
			entity.setBirthdate(customer.getBirthdate());
		}

		if (customer.getCode() != null) {
			this.validateCPF(customer.getCode());
			if (!entity.getCode().equals(customer.getCode())) {
				this.checkExistCPF(customer.getCode());
			}
			entity.setCode(customer.getCode());
		}

		return this.repository.save(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String id) {
		Customer entity = this.readService.findById(id);
		entity.setActive(false);
		this.repository.save(entity);
	}

	private void checkExistCPF(String code) {
		if (this.readService.findByCode(code).isPresent()) {
			throw new BadRequestException("Erro na criação", "Já existe um cliente com o CPF enviado");
		}
	}

	private void validateCPF(String code) {
		try {
			CPFValidator cpfValidator = new CPFValidator();
			cpfValidator.assertValid(code);
		} catch (Exception e) {
			throw new BadRequestException("Erro na criação", "CPF inválido");
		}
	}

	private void validateAge(LocalDate birthdate) {
		long years = birthdate.until(LocalDate.now(), ChronoUnit.YEARS);
		if (years < 18) {
			throw new BadRequestException("Erro na criação", "O cliente precisa ser maior de idade");
		}
	}

}
