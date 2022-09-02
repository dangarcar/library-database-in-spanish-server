package es.library.databaseserver.security.authentication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.crud.service.PerfilService;
import es.library.databaseserver.security.JWTUtils;
import es.library.databaseserver.security.exceptions.ExpiredRefreshTokenException;
import es.library.databaseserver.security.exceptions.NotValidPasswordException;
import es.library.databaseserver.security.exceptions.RefreshTokenNotFoundException;
import es.library.databaseserver.security.model.JWTTokenPair;
import es.library.databaseserver.security.model.LoginCredentials;
import es.library.databaseserver.security.refresh.RefreshToken;
import es.library.databaseserver.security.refresh.service.RefreshTokenService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private PerfilService perfilService;
	
	private Logger logger = LogManager.getLogger(AuthenticationController.class);
	
	@Override
	public JWTTokenPair signUp(Perfil perfil) {
		String rawPassword = perfil.getContrasena();
		JWTTokenPair response;
		//Inserto el perfil en la base de datos
		Long id = perfilService.insertPerfil(perfil).getID();
		
		try {
			response = this.login(new LoginCredentials(
					perfil.getCorreoElectronico(), 
					rawPassword));
		} catch (Exception e) {
			perfilService.deletePerfilByID(id);
			throw e;
		}
		
		logger.info("El perfil {} se registró correctamente {}", perfil.getCorreoElectronico());
		
		//Retorno los tokens
		return response;
	}
	
	@Override
	public JWTTokenPair login(LoginCredentials credentials) {
		JWTTokenPair pair = null;
		
		try {
			UserDetails user = userDetailsService.loadUserByUsername(credentials.getUsername());
			
			if (!passwordEncoder.matches(credentials.getPassword(),user.getPassword()))
				throw new NotValidPasswordException("La contraseña "+credentials.getPassword()+" no se corresponde a la contraseña de la base de datos");
			
			logger.info("Usuario {} se logó",user.getUsername());
			
			UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(
					credentials.getUsername(), 
					credentials.getPassword(), 
					user.getAuthorities());
			
			authenticationManager.authenticate(authenticationToken);
			
			String accessToken = jwtUtils.generateTokenFromUser(user);
			
			String refreshToken;
			
			//Si existe el refresh token lo busco, si no lo creo
			try {
				refreshToken = refreshTokenService.getRefreshTokenByUsername(credentials.getUsername()).getToken();
			}
			catch (ExpiredRefreshTokenException e) {
				logger.debug("El refresh token expiró, se procederá a crear uno nuevo");
				refreshTokenService.deleteRefreshTokenByUsername(credentials.getUsername());
				refreshToken = refreshTokenService.createNewTokenFromUsername(credentials.getUsername()).getToken();
			}
			catch (RefreshTokenNotFoundException e) {
				logger.debug("El refresh token no existe "+e.getClass()+" "+e.getMessage());
				refreshToken = refreshTokenService.createNewTokenFromUsername(credentials.getUsername()).getToken();
			}
			
			pair = new JWTTokenPair(accessToken, refreshToken);
		} 
		catch (UsernameNotFoundException e) {
			logger.error("Error al autenticar al perfil {}, usuario no encontrado en la base de datos", credentials.getUsername());
			throw e;
		}
		catch (AuthenticationException e) {
			logger.error("Error al autenticar al perfil "+credentials.getUsername());
			throw e;
		}
		
		return pair;
	}
	
	@Override
	public void logout(String username) {		
		refreshTokenService.deleteRefreshTokenByUsername(username);
	}
	
	@Override
	public void deleteProfile(String username) {
		Perfil perfil = perfilService.getPerfilByUsername(username);
		
		perfilService.deletePerfilByID(perfil.getID());
		
		try {
			refreshTokenService.deleteRefreshTokenByUsername(username);
		} 
		catch (RefreshTokenNotFoundException e) {
			//No hay que hacer nada
			logger.debug("El refresh token no ha sido borrado porque no existía en usuario {}",username);
		}
		catch (Exception e) {
			refreshTokenService.createNewTokenFromUsername(username);
			perfilService.insertPerfil(perfil);
			throw e;
		}
		
		logger.info("Se eliminó el usuario {}",username);
	}
	
	@Override
	public JWTTokenPair refreshTokenPair(String token) {
		RefreshToken refreshTokenOld = refreshTokenService.getRefreshTokenByID(token);
		
		//Si el token es correcto
		UserDetails user = userDetailsService.loadUserByUsername(refreshTokenOld.getUsername());
		
		String accessToken = jwtUtils.generateTokenFromUser(user);
		
		RefreshToken refreshTokenNew = refreshTokenService.generateRefreshTokenFromUsername(refreshTokenOld.getUsername());
		
		refreshTokenService.updateRefreshTokenByUsername(refreshTokenNew, refreshTokenNew.getUsername());
		
		return new JWTTokenPair(
				accessToken, 
				refreshTokenNew.getToken());
	}

}