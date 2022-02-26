package com.simoes.customerservice.repository;

import com.simoes.customerservice.domain.Customer;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class CustomerSpecification {

	public static Specification<Customer> name(String name) {
		return ((root, query, builder) -> builder.like(root.get("name"), "%" + name + "%"));
	}

	public static Specification<Customer> code(String code) {
		return ((root, query, builder) -> builder.equal(root.get("code"), code));
	}

	public static Specification<Customer> birthdate(LocalDate birthdate) {
		return ((root, query, builder) -> builder.equal(root.get("birthdate"), birthdate));
	}

}
