package es.library.databaseserver.security.refresh;

import java.time.Instant;
import java.util.Objects;

public class RefreshToken {

	private String token;
	private Instant expDate;
	private String username;
		
	public RefreshToken(String token, Instant expDate, String username) {
		this.token = token;
		this.expDate = expDate;
		this.username = username;
	}
	
	public String getToken() {return token;}
	public void setToken(String token) {this.token = token;}
	
	public Instant getExpDate() {return expDate;}
	public void setExpDate(Instant expDate) {this.expDate = expDate;}
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}

	@Override
	public int hashCode() {
		return Objects.hash(expDate, token, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefreshToken other = (RefreshToken) obj;
		return Objects.equals(expDate, other.expDate) && Objects.equals(token, other.token)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "RefreshToken [token=" + token + ", expDate=" + expDate + ", username=" + username + "]";
	}
}
