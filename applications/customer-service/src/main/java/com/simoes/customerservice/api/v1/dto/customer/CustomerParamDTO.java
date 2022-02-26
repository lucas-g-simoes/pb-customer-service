package com.simoes.customerservice.api.v1.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerParamDTO {

	@ApiParam(name = "name", value = "Nome do Cliente")
	private String name;

	@ApiParam(name = "code", value = "Código do Cliente")
	private String code;

	@JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiParam(name = "birthdate", value = "Data de Nascimento do Cliente")
	private LocalDate birthdate;

}
