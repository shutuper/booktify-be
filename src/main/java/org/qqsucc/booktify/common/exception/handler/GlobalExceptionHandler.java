package org.qqsucc.booktify.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.qqsucc.booktify.common.exception.AccessDeniedException;
import org.qqsucc.booktify.common.exception.BusinessException;
import org.qqsucc.booktify.common.exception.InvalidTokenException;
import org.qqsucc.booktify.common.exception.NotFoundException;
import org.qqsucc.booktify.common.exception.handler.response.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleBusinessException(BusinessException exception) {
		printToLog(exception);
		return new ErrorMessage(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), exception.getMessage());
	}


	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handle(BindException exception) {
		printToLog(exception);
		return new ErrorMessage(
				System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				exception.getBindingResult().getFieldErrors()
						.stream()
						.map(error -> error.getField() + ": " + error.getDefaultMessage())
						.collect(Collectors.joining("; "))
		);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handle(MethodArgumentNotValidException exception) {
		printToLog(exception);

		return new ErrorMessage(
				System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				exception.getBindingResult().getFieldErrors()
						.stream()
						.map(error -> error.getField() + ": " + error.getDefaultMessage())
						.collect(Collectors.joining("; "))
		);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
		printToLog(exception);

		return new ErrorMessage(
				System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				exception.getMostSpecificCause().getMessage()
		);
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleBusinessException(NotFoundException exception) {
		printToLog(exception);
		return new ErrorMessage(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}

	@ExceptionHandler(InvalidTokenException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorMessage handleInvalidTokenException(InvalidTokenException exception) {
		printToLog(exception);
		return new ErrorMessage(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorMessage handleInvalidTokenException(AuthenticationException exception) {
		printToLog(exception);
		return new ErrorMessage(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorMessage handleAccessDeniedException(AccessDeniedException exception) {
		printToLog(exception);
		return new ErrorMessage(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), exception.getMessage());
	}

	@ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorMessage handleAccessDeniedException(org.springframework.security.access.AccessDeniedException exception) {
		printToLog(exception);
		return new ErrorMessage(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), exception.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(JDBCConnectionException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public ErrorMessage psqlExceptionHandler(JDBCConnectionException exception) {
		printToLog(exception);
		return new ErrorMessage(
				System.currentTimeMillis(), HttpStatus.SERVICE_UNAVAILABLE.value(),
				exception.getMessage()
		);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handleNotFoundException(Exception exception) {
		printToLog(exception);

		return new ErrorMessage(
				System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				exception.getMessage()
		);
	}

	private void printToLog(Exception exception) {
		boolean isBusinessException = exception instanceof BusinessException;

		if (isBusinessException) {
			log.warn(exception.getMessage(), exception);
			return;
		}

		log.error(exception.getMessage(), exception);
	}

}
