package com.simoes.customerservice.exception;

/**
 * Excessão customizada para BadRequest.
 *
 * @author Lucas Gabriel Rodrigues Simões
 */
public class BadRequestException extends BaseException {

	private static final String title = "Bad Request";

	private static final Integer status = 400;

	public BadRequestException(String title, String message) {
		super(title, status, message);
	}

}
