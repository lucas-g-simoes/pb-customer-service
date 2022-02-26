package com.simoes.customerservice.api.v1.dto.customer;

import com.simoes.customerservice.api.v1.dto.CollectionBaseDTO;
import io.swagger.annotations.ApiModel;

import java.util.Collection;

@ApiModel("CustomerResponseList")
public class CustomerResponseListDTO extends CollectionBaseDTO<CustomerResponseDTO> {

	public CustomerResponseListDTO(Collection<CustomerResponseDTO> items, boolean hasNext) {
		super(items, hasNext);
	}

}
