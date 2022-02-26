package com.simoes.customerservice.api.v1.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simoes.customerservice.api.v1.dto.ObjectBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("CustomerUpdate")
public class CustomerUpdateDTO extends ObjectBaseDTO {

	@NotNull @NotEmpty
	@ApiModelProperty(value = "Nome", required = true)
	private String name;

	@NotNull @NotEmpty
	@ApiModelProperty(value = "CPF", required = true)
	private String code;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "Data de Aniversario", required = true)
	private LocalDate birthdate;

}
