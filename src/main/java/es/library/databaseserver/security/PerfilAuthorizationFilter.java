package es.library.databaseserver.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class PerfilAuthorizationFilter extends OncePerRequestFilter{

	private Logger logger = LogManager.getLogger(PerfilAuthorizationFilter.class);
	
	private static final String PREFIX = "Bearer ";
	
	private UserDetailsService perfilUserDetailsService;
	
	private JWTUtils jwtUtils;

	public PerfilAuthorizationFilter(JWTUtils jwtUtils, UserDetailsService perfilUserDetailsService) {
		this.jwtUtils = jwtUtils;
		this.perfilUserDetailsService = perfilUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {		
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(authorizationHeader != null && !authorizationHeader.isBlank() && authorizationHeader.startsWith(PREFIX)) {
			
			String token = authorizationHeader.substring(PREFIX.length());
			
			if(token != null && !token.isBlank()) {
				try {					
					String username = jwtUtils.validateTokenAndGetUsername(token);
					
					User user = (User) perfilUserDetailsService.loadUserByUsername(username);
					
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
							UsernamePasswordAuthenticationToken.authenticated(username, user.getPassword(), user.getAuthorities());
					
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				} 
				catch (JWTVerificationException e) {
					logger.error("El token no se ha podido validar", e);
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El token no es válido");
				}
				catch (UsernameNotFoundException e) {
					logger.error("El token era válido, pero no existía en la base de datos", e);
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El usuario no existe en la base de datos");
				}
				catch (Exception e) {
					logger.error("Ha ocurrido un error mientras se validaba el acceso", e);
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ha habido un error mientras se verificaba el acceso");
				}
			}
			
			else {
				logger.error("El token detrás del prefijo no era válido");
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El token no es válido");
			}
			
		}
		
		filterChain.doFilter(request, response);
	}

}
