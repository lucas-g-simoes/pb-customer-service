package com.simoes.customerservice.api.v1.controller;

import com.simoes.customerservice.IntegrationTestConfig;
import com.simoes.customerservice.api.v1.dto.customer.CustomerCreateDTO;
import com.simoes.customerservice.api.v1.dto.customer.CustomerPatchDTO;
import com.simoes.customerservice.api.v1.dto.customer.CustomerUpdateDTO;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.Month;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CustomerControllerIT extends IntegrationTestConfig {

	private static final String API_ROOT = "/v1/customers";

	@Test
	public void whenInitSetupThenReturnRecords() {
		given()
			.when()
			.get(API_ROOT)
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("items", hasSize(11));
	}

	@Test
	public void whenFindByCPFFilterWithInitSetupThenReturnCustomer() {
		given()
			.when()
			.param("code", "674.681.380-01")
			.get(API_ROOT)
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("items", notNullValue())
			.body("items[0].name", equalTo("Dexter Holland"))
			.body("items[0].code", equalTo("674.681.380-01"));
	}

	@Test
	public void whenFindByBirthdateFilterWithInitSetupThenReturnCustomer() {
		given()
			.when()
			.param("birthdate", "1958-08-07")
			.get(API_ROOT)
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("items", notNullValue())
			.body("items[0].name", equalTo("Bruce Dickinson"))
			.body("items[0].code", equalTo("521.977.120-55"));
	}

	@Test
	public void whenFindByNameFilterWithInitSetupThenReturnCustomers() {
		given()
			.when()
			.param("name", "er")
			.param("direction", "DESC")
			.param("orders", "birthdate")
			.get(API_ROOT)
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("items[0].name", equalTo("Dexter Holland"))
			.body("items[1].name", equalTo("Eddie Vedder"))
			.body("items[2].name", equalTo("Steven Tyler"));
	}

	@Test
	public void whenFindByInvalidAllQueryParamsThenReturnEmpty() {
		given()
			.when()
			.param("name", "Any")
			.param("birthdate", "2000-01-01")
			.param("code", "Any")
			.get(API_ROOT)
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("items", hasSize(0));

	}

	@Test
	public void whenFindAllByPaginateParamsThenReturnCustomersWithHasNext() {
		given()
			.when()
			.param("page", "0")
			.param("pageSize", "5")
			.get(API_ROOT)
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("hasNext", equalTo(true));

		given()
			.when()
			.param("page", "1")
			.param("pageSize", "5")
			.get(API_ROOT)
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("hasNext", equalTo(true));

		given()
			.when()
			.param("page", "2")
			.param("pageSize", "5")
			.get(API_ROOT)
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("hasNext", equalTo(false));
	}

	@Test
	public void whenFindByDaveThenReturnDave() {
		given()
			.when()
			.get(API_ROOT + "/602b829e-3014-49f1-ad2d-856a6be0bdf6")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo("Dave Grohl"))
			.body("code", equalTo("608.652.409-22"));
	}

	@Test
	public void whenFindByNonexistentIdThenReturnNotFound() {
		given()
			.when()
			.get(API_ROOT + "/nonexistent-uuid")
			.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void whenSuccessCreateCustomerThenReturnNewCustomer() {
		CustomerCreateDTO customer = CustomerCreateDTO
			.builder()
			.name("Joey Ramone")
			.code("307.456.550-09")
			.birthdate(LocalDate.of(1951, Month.MAY, 19))
			.build();

		given()
			.contentType("application/json")
			.body(customer)
			.when()
			.post(API_ROOT)
			.then()
			.statusCode(HttpStatus.CREATED.value())
			.header("Location", notNullValue())
			.body("name", equalTo(customer.getName()))
			.body("code", equalTo(customer.getCode()))
			.body("birthdate", equalTo("1951-05-19"));
	}

	@Test
	public void whenUpdateDaveThenReturnNirvanaGuy() {
		CustomerUpdateDTO customer = CustomerUpdateDTO
			.builder()
			.name("The Nirvana Guy")
			.code("608.652.409-22")
			.birthdate(LocalDate.of(1969, Month.JANUARY, 14))
			.build();

		given()
			.contentType("application/json")
			.body(customer)
			.when()
			.put(API_ROOT + "/602b829e-3014-49f1-ad2d-856a6be0bdf6")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo(customer.getName()))
			.body("code", equalTo(customer.getCode()));
	}

	@Test
	public void whenUpdateExistentCPFThenReturnBadRequest() {
		CustomerUpdateDTO customer = CustomerUpdateDTO
			.builder()
			.name("Dave Grohl")
			.code("804.854.019-32")
			.birthdate(LocalDate.of(1969, Month.JANUARY, 14))
			.build();

		given()
			.contentType("application/json")
			.body(customer)
			.when()
			.put(API_ROOT + "/602b829e-3014-49f1-ad2d-856a6be0bdf6")
			.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body( "message", equalTo("Já existe um cliente com o CPF enviado"));
	}

	@Test
	public void whenDeleteBruceThenReturnNoContent() {
		given()
			.when()
			.delete(API_ROOT + "/ad32f59a-1d8d-44da-8af7-b49872600b0f")
			.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}

	@Test
	public void whenPatchNameThenReturnCustomer() {
		CustomerPatchDTO customer = CustomerPatchDTO
			.builder()
			.name("Axl")
			.build();

		given()
			.contentType("application/json")
			.body(customer)
			.when()
			.patch(API_ROOT + "/bfd22431-2e38-4190-ac20-ff1c62eccaa7")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo(customer.getName()))
			.body("code", equalTo("860.292.380-51"))
			.body("birthdate", equalTo("1962-02-06"));
	}

	@Test
	public void whenPatchBirthdateThenReturnCustomer() {
		CustomerPatchDTO customer = CustomerPatchDTO
			.builder()
			.birthdate(LocalDate.of(2000, Month.JANUARY, 1))
			.build();

		given()
			.contentType("application/json")
			.body(customer)
			.when()
			.patch(API_ROOT + "/46f9b9cc-70e8-4416-80e3-4f9213ec2f88")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo("Steven Tyler"))
			.body("code", equalTo("669.175.570-72"))
			.body("birthdate", equalTo(customer.getBirthdate().toString()));
	}

	@Test
	public void whenPatchCPFThenReturnCustomer() {
		CustomerPatchDTO customer = CustomerPatchDTO
			.builder()
			.code("018.687.510-07")
			.build();

		given()
			.contentType("application/json")
			.body(customer)
			.when()
			.patch(API_ROOT + "/e3e9a2fe-6c42-426b-bfd2-67a15f0464e7")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", equalTo("Anthony Kiedis"))
			.body("code", equalTo(customer.getCode()))
			.body("birthdate", equalTo("1962-11-01"));
	}

	@Test
	public void whenPatchExistentCPFThenReturnBadRequest() {
		CustomerPatchDTO customer = CustomerPatchDTO
			.builder()
			.code("674.681.380-01")
			.build();

		given()
			.contentType("application/json")
			.body(customer)
			.when()
			.patch(API_ROOT + "/e3e9a2fe-6c42-426b-bfd2-67a15f0464e7")
			.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("message", equalTo("Já existe um cliente com o CPF enviado"));
	}

}
