package com.simoes.customerservice.api.v1.mapper;

import com.simoes.customerservice.api.v1.dto.customer.CustomerParamDTO;
import com.simoes.customerservice.domain.Customer;
import com.simoes.customerservice.repository.CustomerSpecification;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationMapper {

	public static Specification<Customer> paramsToSpecification(CustomerParamDTO params) {
		Specification<Customer> specification = null;

		if (params.getName() != null) {
			specification = Specification.where(CustomerSpecification.name(params.getName()));
		}

		if (params.getCode() != null) {
			if (specification == null) {
				specification = Specification.where(CustomerSpecification.code(params.getCode()));
			} else {
				specification.and(CustomerSpecification.code(params.getCode()));
			}
		}

		if (params.getBirthdate() != null) {
			if (specification == null) {
				specification = Specification.where(CustomerSpecification.birthdate(params.getBirthdate()));
			} else {
				specification.and(CustomerSpecification.birthdate(params.getBirthdate()));
			}
		}

		return specification;
	}

}
