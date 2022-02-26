package com.simoes.customerservice.api.v1.mapper;

import com.simoes.customerservice.api.v1.dto.customer.*;
import com.simoes.customerservice.domain.Customer;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.Period;
import java.util.stream.Collectors;

public class CustomerMapper {

	private static final ModelMapper mapper = new ModelMapper();

	static {
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public static CustomerResponseDTO fromEntity(Customer entity) {
		CustomerResponseDTO customer = mapper.map(entity, CustomerResponseDTO.class);
		customer.setAge(calculateAge(entity.getBirthdate()));
		return customer;
	}

	public static CustomerResponseListDTO fromEntities(Page<Customer> items) {
		return new CustomerResponseListDTO(
			items
				.getContent()
				.stream()
				.map(CustomerMapper::fromEntity)
				.collect(Collectors.toList())
			, items.hasNext());
	}

	public static Customer fromCreateDTO(CustomerCreateDTO dto) {
		return mapper.map(dto, Customer.class);
	}

	public static Customer fromUpdateDTO(String id, CustomerUpdateDTO dto) {
		Customer customer = Customer.builder().id(id).active(true).build();

		mapper.map(dto, customer);

		return customer;
	}

	public static Customer fromPatchDTO(CustomerPatchDTO dto) {
		return mapper.map(dto, Customer.class);
	}

	private static Integer calculateAge(LocalDate birthdate) {
		return Period.between(birthdate, LocalDate.now()).getYears();
	}

}
