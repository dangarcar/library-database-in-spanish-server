package es.library.databaseserver.security.model;

import java.util.Objects;

public class JWTTokenPair {

	private final String accessToken;
	private final String refreshToken;
	
	public JWTTokenPair(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {return accessToken;}

	public String getRefreshToken() {return refreshToken;}

	@Override
	public int hashCode() {
		return Objects.hash(accessToken, refreshToken);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JWTTokenPair other = (JWTTokenPair) obj;
		return Objects.equals(accessToken, other.accessToken) && Objects.equals(refreshToken, other.refreshToken);
	}

	@Override
	public String toString() {
		return "JWTTokenPair [accessToken=" + accessToken + ", refreshToken=" + refreshToken + "]";
	}
	
}
