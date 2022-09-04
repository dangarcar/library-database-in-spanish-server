package es.library.databaseserver.security.refresh.service;

import es.library.databaseserver.security.exceptions.DatabaseRefreshTokenException;
import es.library.databaseserver.security.exceptions.ExpiredTokenException;
import es.library.databaseserver.security.exceptions.RefreshTokenNotFoundException;
import es.library.databaseserver.security.refresh.RefreshToken;

public interface RefreshTokenService {

	public RefreshToken getRefreshTokenByID(String id) throws RefreshTokenNotFoundException, ExpiredTokenException;
	
	public RefreshToken getRefreshTokenByUsername(String username) throws RefreshTokenNotFoundException, ExpiredTokenException;
	
	/**
	 * Crea el usuario y lo inserta en la base de datos
	 * @param username
	 * @return
	 */
	public RefreshToken createNewTokenFromUsername(String username) throws DatabaseRefreshTokenException;
	
	/**
	 * Crea el usuario pero no lo inserta en la base de datos
	 * @param username
	 * @return
	 */
	public RefreshToken generateRefreshTokenFromUsername(String username);
	
	public void deleteRefreshTokenByToken(String token) throws RefreshTokenNotFoundException;
	
	public void deleteRefreshTokenByUsername(String username) throws RefreshTokenNotFoundException;
	
	public RefreshToken updateRefreshTokenByUsername(RefreshToken refreshToken, String username) throws DatabaseRefreshTokenException, RefreshTokenNotFoundException;
	
}
