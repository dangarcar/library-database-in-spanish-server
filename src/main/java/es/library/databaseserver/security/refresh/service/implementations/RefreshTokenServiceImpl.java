package es.library.databaseserver.security.refresh.service.implementations;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.library.databaseserver.security.exceptions.ExpiredTokenException;
import es.library.databaseserver.security.exceptions.RefreshTokenNotFoundException;
import es.library.databaseserver.security.refresh.RefreshToken;
import es.library.databaseserver.security.refresh.dao.RefreshTokenDAO;
import es.library.databaseserver.security.refresh.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

	private static final int TOKEN_LENGHT = 32;
	
	@Autowired
	private RefreshTokenDAO refreshTokenDAO;
	
	@Value("${jwt.refresh_token_expiration_time}")
	private long refreshTokenExpirationMillis;
	
	private Random rng = new SecureRandom();
	
	private Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
	
	@Override
	public RefreshToken getRefreshTokenByID(String id) {
		RefreshToken refreshToken = refreshTokenDAO.getRefreshTokenByID(id).orElseThrow(
				() -> new RefreshTokenNotFoundException("No se ha encontrado el token "+id));
		
		if(refreshToken.getExpDate().isBefore(Instant.now()))
			throw new ExpiredTokenException("El refresh token caducó el "+refreshToken.getExpDate()+", hace "+Duration.between(Instant.now(), refreshToken.getExpDate()));
		
		return refreshToken;
	}

	@Override
	public RefreshToken getRefreshTokenByUsername(String username) {
		RefreshToken refreshToken = refreshTokenDAO.getRefreshTokenByUsername(username).orElseThrow(
				() -> new RefreshTokenNotFoundException("No se ha encontrado el token del usuario"+username));
		
		if(refreshToken.getExpDate().isBefore(Instant.now()))
			throw new ExpiredTokenException("El refresh token caducó el "+refreshToken.getExpDate()+", hace "+Duration.between(Instant.now(), refreshToken.getExpDate()));
		
		return refreshToken;
	}

	@Override
	public RefreshToken createNewTokenFromUsername(String username) {		
		return refreshTokenDAO.insertRefreshToken(this.generateRefreshTokenFromUsername(username));
	}

	@Override
	public RefreshToken generateRefreshTokenFromUsername(String username) {
		return new RefreshToken(
				generateNewString(), 
				Instant.now().plusMillis(refreshTokenExpirationMillis), 
				username
			);
	}
	
	@Override
	public void deleteRefreshTokenByToken(String token) {
		refreshTokenDAO.deleteRefreshTokenByToken(token);
	}

	@Override
	public void deleteRefreshTokenByUsername(String username) {
		refreshTokenDAO.deleteRefreshTokenByUsername(username);
	}
	
	@Override
	public RefreshToken updateRefreshTokenByUsername(RefreshToken refreshToken, String username) {
		return refreshTokenDAO.updateRefreshTokenByUsername(refreshToken, username);
	}
	
	private String generateNewString() {
		byte[] token = new byte[TOKEN_LENGHT];
		
		rng.nextBytes(token);
		
		return encoder.encodeToString(token);
	}
	
}
