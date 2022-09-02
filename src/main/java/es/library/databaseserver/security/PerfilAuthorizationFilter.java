package es.library.databaseserver.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;


import es.library.databaseserver.security.exceptions.AuthorizationException;

public class PerfilAuthorizationFilter extends OncePerRequestFilter{
	
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
					
					UserDetails user = perfilUserDetailsService.loadUserByUsername(username);
					
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
							UsernamePasswordAuthenticationToken.authenticated(username, user.getPassword(), user.getAuthorities());
					
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				} 
				catch (Exception e) {
					throw new AuthorizationException(e.getMessage(),e);
				}
			}
			
			else {
				throw new AuthorizationException("El token detrás del prefijo no era válido");
			}
			
		}
		
		filterChain.doFilter(request, response);
	}

}
