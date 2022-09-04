package es.library.databaseserver.prestamos.exceptions;

import java.time.ZonedDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import es.library.databaseserver.shared.exceptions.ApiError;

@RestControllerAdvice
public class PrestamoExceptionHandler {

	private Logger logger = LogManager.getLogger(PrestamoExceptionHandler.class);
	
	@ExceptionHandler(PrestamoNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ApiError prestamoNotFoundExceptionHandler(PrestamoNotFoundException e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.NOT_FOUND.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler(IllegalPrestamoException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ApiError illegalPrestamoExceptionHandler(IllegalPrestamoException e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.BAD_REQUEST.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler(DatabasePrestamoException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError databasePrestamoExceptionHandler(DatabasePrestamoException e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler(PrestamoNotAllowedException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ApiError prestamoNotAllowedExceptionHandler(PrestamoNotAllowedException e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.CONFLICT.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
}