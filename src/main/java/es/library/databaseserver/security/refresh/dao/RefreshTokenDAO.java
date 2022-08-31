package es.library.databaseserver.security.refresh.dao;

import java.util.Optional;

import es.library.databaseserver.security.refresh.RefreshToken;

public interface RefreshTokenDAO {
	
	public Optional<RefreshToken> getRefreshTokenByID(String id);
	
	public Optional<RefreshToken> getRefreshTokenByUsername(String username);
	
	public RefreshToken insertRefreshToken(RefreshToken refreshToken);
	
	public void deleteRefreshTokenByToken(String id);
	
	public void deleteRefreshTokenByUsername(String username);
	
	public RefreshToken updateRefreshTokenByUsername(RefreshToken refreshToken, String username);
	
}
