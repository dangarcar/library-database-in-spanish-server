package es.library.databaseserver.security.refresh.dao.implementations;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.security.exceptions.DatabaseRefreshTokenException;
import es.library.databaseserver.security.exceptions.ExpiredRefreshTokenException;
import es.library.databaseserver.security.exceptions.RefreshTokenNotFoundException;
import es.library.databaseserver.security.refresh.RefreshToken;
import es.library.databaseserver.security.refresh.dao.RefreshTokenDAO;

@Repository
public class RefreshTokenSQLiteRepo implements RefreshTokenDAO{

	@Autowired
	@Qualifier("tokenJDBC")
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public Optional<RefreshToken> getRefreshTokenByID(String id) {
		final String sqlString = "SELECT Token,ExpDate,Username FROM Tokens WHERE Token LIKE :token";
		
		var result = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("token", id), refreshTokenRowMapper);

		if(result.isEmpty()) return Optional.empty();
		
		return Optional.ofNullable(result.get(0));
	}

	@Override
	public RefreshToken insertRefreshToken(RefreshToken refreshToken) {
		final String sqlString = "INSERT INTO Tokens (Token,ExpDate,Username) VALUES(:token,:expDate,:username)";
		
		final int i = jdbcTemplate.update(sqlString, new MapSqlParameterSource()
				.addValue("token", refreshToken.getToken())
				.addValue("expDate", refreshToken.getExpDate().toEpochMilli())
				.addValue("username", refreshToken.getUsername()));
		
		if(i == 0) throw new DatabaseRefreshTokenException("El refresh token no ha sido añadido a la base de datos por alguna razón");
		
		return this.getRefreshTokenByID(refreshToken.getToken()).orElseThrow(
				() -> new DatabaseRefreshTokenException("El refresh token no ha sido añadido a la base de datos por alguna razón"));
	}

	@Override
	public Optional<RefreshToken> getRefreshTokenByUsername(String username) {
		final String sqlString = "SELECT Token,ExpDate,Username FROM Tokens WHERE Username LIKE :username";
		
		var result = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("username", username), refreshTokenRowMapper);

		if(result.isEmpty()) return Optional.empty();
		
		return Optional.ofNullable(result.get(0));
	}

	@Override
	public void deleteRefreshTokenByToken(String id) {
		final String sqlString = "DELETE FROM Tokens WHERE Token LIKE :token";
		
		var a = this.getRefreshTokenByID(id);
		
		a.ifPresentOrElse(
				(t) -> jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("token", id)), 
				() -> { throw new RefreshTokenNotFoundException("No se ha encontrado el token "+id);}
			);
	}

	@Override
	public void deleteRefreshTokenByUsername(String username) {
		final String sqlString = "DELETE FROM Tokens WHERE Username LIKE :username";
		
		var a = this.getRefreshTokenByUsername(username);
		
		a.ifPresentOrElse(
				(t) -> jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("username", username)), 
				() -> { throw new RefreshTokenNotFoundException("No se ha encontrado el token del usuario"+username);}
			);
	}
	
	@Override
	public RefreshToken updateRefreshTokenByUsername(RefreshToken refreshToken, String username) {
		final String sqlString = "UPDATE Tokens SET "
				+ "Token = :token"
				+ ",ExpDate = :expDate"
				+ ",Username = :username"
				+ " WHERE Username LIKE :username";
		
		var a = this.getRefreshTokenByUsername(username);
		
		a.ifPresentOrElse(
				(t) -> jdbcTemplate.update(sqlString, new MapSqlParameterSource()
						.addValue("token", refreshToken.getToken())
						.addValue("expDate", refreshToken.getExpDate().toEpochMilli())
						.addValue("username", refreshToken.getUsername())), 
				() -> { throw new RefreshTokenNotFoundException("No se ha encontrado el token del usuario"+username);}
			);
		
		return this.getRefreshTokenByID(refreshToken.getToken()).orElseThrow(
				() -> new DatabaseRefreshTokenException("El refresh token no ha sido añadido a la base de datos por alguna razón"));
	}
	
	private RowMapper<RefreshToken> refreshTokenRowMapper = (rs, rowNum) -> {
			if(rs.getLong("ExpDate") == 0) 
				throw new ExpiredRefreshTokenException("El token esta expirado");
			
			return new RefreshToken(
					rs.getString("Token"), 
					Instant.ofEpochMilli(rs.getLong("ExpDate")), 
					rs.getString("Username")
				);
	};
	
}
