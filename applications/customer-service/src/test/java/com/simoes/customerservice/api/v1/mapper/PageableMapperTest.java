package com.simoes.customerservice.api.v1.mapper;

import com.simoes.customerservice.api.v1.dto.SortParamDTO;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class PageableMapperTest {

	@Test
	public void whenAscSortParamDTOThenReturnSort() {
		SortParamDTO dto = SortParamDTO
			.builder()
			.direction(null)
			.orders(Collections.singletonList("name"))
			.build();

		Sort sort = PageableMapper.orderToSort(dto);

		assertThat(sort.get().findAny()).isPresent();
		assertThat(sort.get().findAny().get().getProperty()).isEqualTo("name");
		assertThat(sort.get().findAny().get().getDirection().isAscending()).isEqualTo(true);
	}

	@Test
	public void whenDescSortParamDTOThenReturnSort() {
		SortParamDTO dto = SortParamDTO
			.builder()
			.direction("DESC")
			.orders(Collections.singletonList("name"))
			.build();

		Sort sort = PageableMapper.orderToSort(dto);

		assertThat(sort.get().findAny()).isPresent();
		assertThat(sort.get().findAny().get().getProperty()).isEqualTo("name");
		assertThat(sort.get().findAny().get().getDirection().isAscending()).isEqualTo(false);
	}

	@Test
	public void whenAleatorySortParamDTOThenReturnASCSort() {
		SortParamDTO dto = SortParamDTO
			.builder()
			.direction("ALEATORY")
			.orders(Collections.singletonList("name"))
			.build();

		Sort sort = PageableMapper.orderToSort(dto);

		assertThat(sort.get().findAny()).isPresent();
		assertThat(sort.get().findAny().get().getProperty()).isEqualTo("name");
		assertThat(sort.get().findAny().get().getDirection().isAscending()).isEqualTo(true);
	}

}
