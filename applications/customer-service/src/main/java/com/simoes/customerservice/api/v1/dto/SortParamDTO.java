package com.simoes.customerservice.api.v1.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortParamDTO {

    @ApiParam(name = "direction", value = "Direção em que ordenação será aplicada")
    private String direction;

    @ApiParam(name = "orders", value = "Atributos para ordernação")
    private List<String> orders;

}
