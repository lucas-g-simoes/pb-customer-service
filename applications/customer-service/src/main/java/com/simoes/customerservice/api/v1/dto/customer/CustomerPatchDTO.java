package com.simoes.customerservice.api.v1.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("CustomerPatch")
public class CustomerPatchDTO {

	@ApiModelProperty(value = "Nome")
	private String name;

	@ApiModelProperty(value = "CPF")
	private String code;

	@JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "Data de Aniversario")
	private LocalDate birthdate;

}
