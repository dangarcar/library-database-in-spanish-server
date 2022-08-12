package es.library.databaseserver.contenido.exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import es.library.databaseserver.shared.exceptions.ApiError;

@RestControllerAdvice
public class ContenidoExceptionHandler {

	@ExceptionHandler(ContenidoNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ApiError contenidoNotFoundExceptionHandler(ContenidoNotFoundException e, WebRequest r) {
		e.printStackTrace();
		return new ApiError(
				HttpStatus.NOT_FOUND.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler(value =  {
			IllegalContenidoException.class,
			NotValidSoporteException.class,
			NotValidTypeContenidoException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ApiError badContenidoExceptionHandler(Exception e, WebRequest r) {
		e.printStackTrace();
		return new ApiError(
				HttpStatus.BAD_REQUEST.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler(DatabaseContenidoException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError databaseContenidoExceptionHandler(DatabaseContenidoException e, WebRequest r) {
		e.printStackTrace();
		return new ApiError(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler(ContenidoAlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ApiError contenidoAlreadyExistsExceptionHandler(ContenidoAlreadyExistsException e, WebRequest r) {
		e.printStackTrace();
		return new ApiError(
				HttpStatus.CONFLICT.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
}