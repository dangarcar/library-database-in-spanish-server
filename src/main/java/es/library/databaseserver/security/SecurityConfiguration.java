package es.library.databaseserver.security;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private Logger logger = LogManager.getLogger(SecurityConfiguration.class);
	
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
		return http.cors().and().csrf().disable()
				
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			
			.authorizeRequests()
			.antMatchers(
					"/auth/login/**",
					"/auth/signup/**",
					"/auth/token/refresh/**",
					"/contenidos/search/**")
				.permitAll()
			.antMatchers(
					"/auth/logout/**",
					"/auth/delete/**",
					"/prestar/**",
					"/devolver/**")
				.hasRole("USER")
			.antMatchers(
					"/prestamos/search/**",
					"/perfiles/search/**",
					"/contenidos/**")
			.hasRole("STAFF")
			
			//El admin puede acceder a cualquier método
			.anyRequest()
				.hasRole("ADMIN")
			.and()
	
			.exceptionHandling().authenticationEntryPoint((request, response, e) -> {
				logger.info("Usuario no está autorizado para hacer {}", request.getPathInfo());
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("APPLICATION/JSON");
				response.getWriter().write("{ \"error\": \"No está autorizado para hacer esto\"}");
			})
			.and()
	
			.addFilterBefore(new PerfilAuthorizationFilter(jwtUtils,perfilUserDetailsService), UsernamePasswordAuthenticationFilter.class)
			
			.build();
	}
}
