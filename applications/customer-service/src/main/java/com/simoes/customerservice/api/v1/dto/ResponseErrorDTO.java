package com.simoes.customerservice.api.v1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * DTO HttpResponse Error
 *
 * @author Lucas Gabriel Rodrigues Simões
 */
@Data
@Builder
@ApiModel("ResponseError")
public class ResponseErrorDTO {

	@ApiModelProperty(value = "Titulo do erro")
	private String title;

	@ApiModelProperty(value = "Mensagem detalhada do erro")
	private String message;

	@ApiModelProperty(value = "Tempo exato do erro")
	private String timestamp;

	@ApiModelProperty(value = "URI da request")
	private String path;

}
