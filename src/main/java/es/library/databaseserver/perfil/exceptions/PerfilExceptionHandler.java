package es.library.databaseserver.perfil.exceptions;

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
public class PerfilExceptionHandler {

	private Logger logger = LogManager.getLogger(PerfilExceptionHandler.class);
	
	@ExceptionHandler(PerfilNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ApiError perfilNotFoundExceptionHandler(PerfilNotFoundException e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.NOT_FOUND.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler(IllegalPerfilException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ApiError illegalPerfilExceptionHandler(IllegalPerfilException e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.BAD_REQUEST.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler(DatabasePerfilException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError databasePerfilExceptionHandler(DatabasePerfilException e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler(EmailAlreadyExistPerfilException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ApiError emailAlreadyExistPerfilExceptionHandler(EmailAlreadyExistPerfilException e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.CONFLICT.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
}