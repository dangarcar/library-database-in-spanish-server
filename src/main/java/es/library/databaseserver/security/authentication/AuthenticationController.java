package es.library.databaseserver.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.security.model.JWTTokenPair;
import es.library.databaseserver.security.model.LoginCredentials;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping(path = "/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public JWTTokenPair signUp(@RequestBody Perfil perfil) {
		return authenticationService.signUp(perfil);
	}
	
	@PostMapping(path = "/login")
	@ResponseStatus(HttpStatus.CREATED)
	public JWTTokenPair login(@RequestBody LoginCredentials credentials) {
		return authenticationService.login(credentials);
	}
	
	@PutMapping(path = "/logout/{username}")
	public void logout(@PathVariable(name = "username") String username, Authentication authentication) {
		if(!authentication.getPrincipal().equals(username) && !authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().contains("ROLE_ADMIN")) {
			throw new BadCredentialsException("El usuario "+authentication.getPrincipal()+" ha intentado borrar al perfil "+username+" pero no es admin");
		}
		authenticationService.logout(username);
	}
	
	@GetMapping(path = "/token/refresh/{token}")
	public JWTTokenPair refreshTokenPair(@PathVariable(name = "token") String token) {
		return authenticationService.refreshTokenPair(token);
	}
	
	@DeleteMapping(path = "/delete/{username}")
	public void deletePerfil(@PathVariable(name = "username") String username, Authentication authentication) {
		if(!authentication.getPrincipal().equals(username) && !authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().contains("ROLE_ADMIN")) {
			throw new BadCredentialsException("El usuario "+authentication.getPrincipal()+" ha intentado borrar al perfil "+username+" pero no es admin");
		}
		authenticationService.deleteProfile(username);
	}
}
