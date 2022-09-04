package es.library.databaseserver.security.authentication;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.security.model.JWTTokenPair;
import es.library.databaseserver.security.model.LoginCredentials;

public interface AuthenticationService {

	public JWTTokenPair refreshTokenPair(String token);
	
	public JWTTokenPair signUp(Perfil perfil);
	
	public JWTTokenPair login(LoginCredentials credentials);
	
	public void logout(String username);

	public void deleteProfile(String username);
	
}
