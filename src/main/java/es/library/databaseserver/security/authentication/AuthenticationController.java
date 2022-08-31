package es.library.databaseserver.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.security.model.JWTTokenPair;
import es.library.databaseserver.security.model.LoginCredentials;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping(path = "/signup")
	public JWTTokenPair signUp(@RequestBody Perfil perfil) {
		return authenticationService.signUp(perfil);
	}
	
	@PostMapping(path = "/login")
	public JWTTokenPair login(@RequestBody LoginCredentials credentials) {
		return authenticationService.login(credentials);
	}
	
	@PostMapping(path = "/logout/{username}")
	public void logout(@PathVariable(name = "username") String username) {
		authenticationService.logout(username);
	}
	
	@GetMapping(path = "/token/refresh/{token}")
	public JWTTokenPair refreshTokenPair(@PathVariable(name = "token") String token) {
		return authenticationService.refreshTokenPair(token);
	}
	
	@DeleteMapping(path = "/delete/{username}")
	public void deletePerfil(@PathVariable(name = "username") String username) {
		authenticationService.deleteProfile(username);
	}
}
