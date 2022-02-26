package com.simoes.customerservice.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * DTO de listagem base da aplicação.
 *
 * @author Lucas Simões
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class CollectionBaseDTO<T extends ObjectBaseDTO> extends ObjectBaseDTO {

    /**
     * Itens a serem retornados.
     */
    private Collection<T> items;

    /**
     * Indica se possui mais itens na próxima página.
     */
    private boolean hasNext;

}
