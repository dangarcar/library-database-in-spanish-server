package es.library.databaseserver.security.exceptions;

import java.time.ZonedDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import es.library.databaseserver.shared.exceptions.ApiError;

@RestControllerAdvice
public class SecurityExceptionHandler {

	private Logger logger = LogManager.getLogger(SecurityExceptionHandler.class);
	
	@ExceptionHandler({
		ExpiredTokenException.class, 
		TokenExpiredException.class})
	@ResponseStatus(value = HttpStatus.GONE)
	public ApiError goneExceptionHandler(Exception e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.GONE.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler({
		NotValidPasswordException.class, 
		AuthenticationException.class,
		JWTDecodeException.class,
		JWTVerificationException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ApiError badRequestExceptionHandler(Exception e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.BAD_REQUEST.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler({
		RefreshTokenNotFoundException.class,
		UsernameNotFoundException.class})
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ApiError notFoundExceptionHandler(Exception e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.NOT_FOUND.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler(DatabaseRefreshTokenException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiError databaseRefreshTokenExceptionHandler(DatabaseRefreshTokenException e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
	
	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ApiError authorizationExceptionHandler(AuthorizationException e, WebRequest r) {
		logger.warn("",e);
		return new ApiError(
				HttpStatus.UNAUTHORIZED.value(), 
				ZonedDateTime.now(), 
				e.getMessage());
	}
}
