package com.simoes.customerservice.api.v1.mapper;


import com.simoes.customerservice.api.v1.dto.PaginationParamDTO;
import com.simoes.customerservice.api.v1.dto.SortParamDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author Lucas Simões
 */
public class PageableMapper {

    public static Pageable paramsToPageable(PaginationParamDTO pagination, SortParamDTO sort) {
        return PageRequest.of(pagination.getPage(), pagination.getPageSize(), PageableMapper.orderToSort(sort));
    }

    public static Sort orderToSort(SortParamDTO items) {
        if (items.getOrders() != null) {
            String direction = items.getDirection() == null ? "ASC" : items.getDirection().toUpperCase();
            direction = !direction.equals("ASC") && !direction.equals("DESC") ? "ASC" : direction;

            return Sort.by(Sort.Direction.valueOf(direction), items.getOrders().toArray(new String[0]));
        } else {
            return Sort.unsorted();
        }
    }

}
