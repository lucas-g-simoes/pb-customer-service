package com.simoes.customerservice.service.customer;

import com.simoes.customerservice.domain.Customer;
import com.simoes.customerservice.exception.NotFoundException;
import com.simoes.customerservice.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerReadService {

	private final CustomerRepository repository;

	public CustomerReadService(CustomerRepository repository) {
		this.repository = repository;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<Customer> find(Specification<Customer> specification, Pageable pageable) {
		Page<Customer> customers;

		if (specification != null) {
			customers = this.repository.findAll(specification, pageable);
		} else {
			customers = this.repository.findAll(pageable);
		}

		return customers;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Customer findById(String id) {
		return this.repository.findById(id)
			.orElseThrow(() -> new NotFoundException(String.format("Customer not found: %s", id)));
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Optional<Customer> findByCode(String code) {
		return this.repository.findByCode(code);
	}

}
