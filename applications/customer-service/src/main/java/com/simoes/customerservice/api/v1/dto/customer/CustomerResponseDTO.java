package com.simoes.customerservice.api.v1.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simoes.customerservice.api.v1.dto.ObjectBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@ApiModel("CustomerResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerResponseDTO extends ObjectBaseDTO {

	@ApiModelProperty(value = "Id do cliente")
	private String id;

	@ApiModelProperty(value = "Nome do cliente")
	private String name;

	@ApiModelProperty(value = "CPF do cliente")
	private String code;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "Data de aniversário do cliente")
	private LocalDate birthdate;

	@ApiModelProperty(value = "Idade do cliente")
	private Integer age;

}
