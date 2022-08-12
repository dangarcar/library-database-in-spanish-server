package es.library.databaseserver.shared.exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError globalExceptionHandler(Exception e, WebRequest r) {
		e.printStackTrace();
		return new ApiError(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				ZonedDateTime.now(), 
				"Unexpected server error");
	}
	
}
