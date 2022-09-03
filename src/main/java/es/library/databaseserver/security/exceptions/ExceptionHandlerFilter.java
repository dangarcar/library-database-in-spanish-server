package es.library.databaseserver.security.exceptions;

import java.io.IOException;
import java.time.ZonedDateTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;

import es.library.databaseserver.shared.exceptions.ApiError;

public class ExceptionHandlerFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} 
		catch (JWTVerificationException e) {
			writeError(HttpStatus.GONE.value(), e, response, e.getMessage()+" Vaya a /refresh/token para conseguir un nuevo token o vuelva a /login");
		}
		catch (RuntimeException e) {
			writeError(HttpStatus.UNAUTHORIZED.value(), e, response);
		}
	}
	
	public void writeError(int code, Exception e, HttpServletResponse response) throws JsonProcessingException, IOException {
		ApiError error = new ApiError(code, ZonedDateTime.now(), e.getMessage());
		response.setStatus(error.getStatusCode());
		response.setContentType("APPLICATION/JSON");
		response.getWriter().write(error.getJsonString());
	}
	
	public void writeError(int code, Exception e, HttpServletResponse response, String message) throws JsonProcessingException, IOException {
		ApiError error = new ApiError(code, ZonedDateTime.now(), message);
		response.setStatus(error.getStatusCode());
		response.setContentType("APPLICATION/JSON");
		response.getWriter().write(error.getJsonString());
	}
}
