package com.simoes.customerservice.exception;

/**
 * Excessão base para excessões customizadas.
 *
 * @author Lucas Gabriel Rodrigues Simões
 */
public class BaseException extends RuntimeException {

	private String title;

	private Integer status;

	public BaseException(String title, Integer status, String message) {
		super(message);

		this.title = title;
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public Integer getStatus() {
		return status;
	}
}
