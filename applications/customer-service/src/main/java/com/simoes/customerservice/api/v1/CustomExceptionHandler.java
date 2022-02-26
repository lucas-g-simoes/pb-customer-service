package com.simoes.customerservice.api.v1;

import com.simoes.customerservice.api.v1.dto.ResponseErrorDTO;
import com.simoes.customerservice.exception.BadRequestException;
import com.simoes.customerservice.exception.BaseException;
import com.simoes.customerservice.exception.NotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.UnexpectedTypeException;
import java.time.Instant;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private final MessageSource messageSource;

	public CustomExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(UnexpectedTypeException.class)
	protected ResponseEntity<ResponseErrorDTO> unexpectedTypeException(HttpServletRequest request, NotFoundException e) {
		return buildResponse(request, e);
	}

	@ExceptionHandler(NotFoundException.class)
	protected ResponseEntity<ResponseErrorDTO> notFoundException(HttpServletRequest request, NotFoundException e) {
		return buildResponse(request, e);
	}

	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<ResponseErrorDTO> badRequestException(HttpServletRequest request, BadRequestException e) {
		return buildResponse(request, e);
	}

	private ResponseEntity<ResponseErrorDTO> buildResponse(HttpServletRequest request, Exception e) {
		HttpStatus status;
		String title;

		if (e instanceof BaseException) {
			status = HttpStatus.valueOf(((BaseException) e).getStatus());
			title = ((BaseException) e).getTitle();
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			title = "Internal Server Error";
		}

		return ResponseEntity.status(status)
			.body(ResponseErrorDTO.builder()
				.title(title)
				.message(e.getMessage())
				.timestamp(Instant.now().toString())
				.path(request.getRequestURI())
				.build()
			);
	}

}
