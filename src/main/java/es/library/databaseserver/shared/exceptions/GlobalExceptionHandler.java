package es.library.databaseserver.shared.exceptions;

import java.time.ZonedDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError globalExceptionHandler(Exception e, WebRequest r) {
		logger.error("",e);
		return new ApiError(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				ZonedDateTime.now(), 
				"Unexpected server error");
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
	public ApiError notImplementedHandler(HttpRequestMethodNotSupportedException e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.NOT_IMPLEMENTED.value(), 
				ZonedDateTime.now(), 
				"Lo que está intentado hacer no es posible");
	}
}
