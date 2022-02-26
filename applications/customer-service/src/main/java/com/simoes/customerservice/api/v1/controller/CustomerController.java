package com.simoes.customerservice.api.v1.controller;

import com.simoes.customerservice.api.v1.dto.PaginationParamDTO;
import com.simoes.customerservice.api.v1.dto.SortParamDTO;
import com.simoes.customerservice.api.v1.dto.customer.*;
import com.simoes.customerservice.api.v1.mapper.CustomerMapper;
import com.simoes.customerservice.api.v1.mapper.PageableMapper;
import com.simoes.customerservice.api.v1.mapper.SpecificationMapper;
import com.simoes.customerservice.domain.Customer;
import com.simoes.customerservice.service.customer.CustomerPersistenceService;
import com.simoes.customerservice.service.customer.CustomerReadService;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/customers")
@Api(value = "Customer", tags = "Customer", description = "Customer Endpoints")
public class CustomerController {

	private final CustomerReadService readService;

	private final CustomerPersistenceService persistenceService;

	public CustomerController(CustomerReadService readService, CustomerPersistenceService persistenceService) {
		this.readService = readService;
		this.persistenceService = persistenceService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK", response = CustomerResponseListDTO.class),
		@ApiResponse(code = 400, message = "Erro na consulta dos clientes"),
		@ApiResponse(code = 500, message = "Ocorreu um erro interno no servidor")
	})
	@ApiOperation("Consulta de Clientes")
	public ResponseEntity<CustomerResponseListDTO> find(PaginationParamDTO pagination, SortParamDTO sort, CustomerParamDTO params) {
		Pageable pageable = PageableMapper.paramsToPageable(pagination, sort);

		return ResponseEntity
			.ok(
				CustomerMapper.fromEntities(readService.find(SpecificationMapper.paramsToSpecification(params), pageable))
			);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK", response = CustomerResponseDTO.class),
		@ApiResponse(code = 400, message = "Erro na consulta dos clientes"),
		@ApiResponse(code = 404, message = "Cliente não encontrado"),
		@ApiResponse(code = 500, message = "Ocorreu um erro interno no servidor")
	})
	@ApiOperation("Consulta de Cliente pelo Id")
	public ResponseEntity<CustomerResponseDTO> findById(
		@ApiParam(value = "ID", required = true, example = "uuid")
		@PathVariable("id") String id
	) {
		return ResponseEntity.ok(CustomerMapper.fromEntity(readService.findById(id)));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 201, message = "OK", response = CustomerResponseDTO.class),
		@ApiResponse(code = 400, message = "Erro na criacao do cliente"),
		@ApiResponse(code = 500, message = "Ocorreu um erro interno no servidor")
	})
	@ApiOperation("Criar um Cliente")
	public ResponseEntity<CustomerResponseDTO> create(
		@ApiParam(value = "Cliente", required = true) @RequestBody
		@Valid CustomerCreateDTO dto, UriComponentsBuilder uriBuilder
	) {
		Customer customer = this.persistenceService.create(CustomerMapper.fromCreateDTO(dto));
		return ResponseEntity
			.created(
				uriBuilder
					.path("/v1/customers/{id}")
					.buildAndExpand(customer.getId())
					.toUri()
			).body(CustomerMapper.fromEntity(customer));
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK", response = CustomerResponseDTO.class),
		@ApiResponse(code = 400, message = "Erro na alteração do Cliente"),
		@ApiResponse(code = 404, message = "Cliente não encontrado"),
		@ApiResponse(code = 500, message = "Ocorreu um erro interno no servidor")
	})
	@ApiOperation("Alteração do Cliente pelo ID")
	public ResponseEntity<CustomerResponseDTO> update(
		@ApiParam(value = "Id", required = true, example = "uuid") @PathVariable("id") String id,
		@ApiParam(value = "Cliente", required = true) @Valid @RequestBody CustomerUpdateDTO dto
	) {
		Customer customer = CustomerMapper.fromUpdateDTO(id, dto);
		return ResponseEntity.ok(CustomerMapper.fromEntity(persistenceService.update(customer)));
	}

	@PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({
		@ApiResponse(code = 200, message = "OK", response = CustomerResponseDTO.class),
		@ApiResponse(code = 400, message = "Erro na alteração do Cliente"),
		@ApiResponse(code = 404, message = "Cliente não encontrado"),
		@ApiResponse(code = 500, message = "Ocorreu um erro interno no servidor")
	})
	@ApiOperation("Alteração parcial do Cliente pelo ID")
	public ResponseEntity<CustomerResponseDTO> patch(
		@ApiParam(value = "Id", required = true, example = "uuid") @PathVariable("id") String id,
		@ApiParam(value = "Cliente", required = true) @Valid @RequestBody CustomerPatchDTO dto
	) {
		Customer customer = CustomerMapper.fromPatchDTO(dto);
		return ResponseEntity.ok(CustomerMapper.fromEntity(persistenceService.patch(id, customer)));
	}

	@DeleteMapping("/{id}")
	@ApiResponses({
		@ApiResponse(code = 204, message = "OK"),
		@ApiResponse(code = 400, message = "Erro na remoção do Cliente"),
		@ApiResponse(code = 404, message = "Cliente não encontrado"),
		@ApiResponse(code = 500, message = "Ocorreu um erro interno no servidor")
	})
	@ApiOperation("Remoção do Cliente pelo ID")
	public ResponseEntity<Void> delete(
		@ApiParam(value = "Id", required = true, example = "uuid") @PathVariable("id") String id
	) {
		this.persistenceService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
