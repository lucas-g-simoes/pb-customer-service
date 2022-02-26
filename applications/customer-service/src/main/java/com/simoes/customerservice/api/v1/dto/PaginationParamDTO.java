package com.simoes.customerservice.api.v1.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Objeto padrão de paginação.
 *
 * @author Lucas Gabriel Rodrigues Simões
 */
@Data
public class PaginationParamDTO {

    @ApiParam(name = "page", defaultValue = "0", value = "Número da página", example = "0")
    private Integer page = 0;

    @ApiParam(name = "pageSize", defaultValue = "1000", value = "Número de informações por página", example = "1000")
    private Integer pageSize = 1000;

}
