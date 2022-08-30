package es.library.databaseserver.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private PerfilUserDetailsService perfilUserDetailsService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*");
			}
		};
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {		
		http.cors().and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//TODO: Endpoints públicos
		http.authorizeRequests().antMatchers(
				"/auth/login/**",
				"/auth/signup/**",
				"/auth/token/refresh/**")
			.permitAll();
		
		//TODO: Endpoints de usuario
		http.authorizeRequests().antMatchers(
				"/auth/logout/**",
				"/auth/delete/**")
			.hasRole("USER");
		
		//TODO: Endpoints de trabajadores
		http.authorizeRequests().antMatchers(
				)
			.hasRole("STAFF");
		
		//TODO: Endpoints de administradores
		http.authorizeRequests().antMatchers(
				)
			.hasRole("ADMIN");
		
		http.authorizeRequests().anyRequest().authenticated();
		
		http.exceptionHandling().authenticationEntryPoint((request, response, e) -> {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No está autorizado para hacer esto");
		});
		
		http.addFilterBefore(new PerfilAuthorizationFilter(jwtUtils,perfilUserDetailsService), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
