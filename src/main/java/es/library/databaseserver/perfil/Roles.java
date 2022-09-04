package es.library.databaseserver.perfil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Roles {
	
	ROLE_USER(new ArrayList<Roles>(List.of(  ))),
	
	ROLE_STAFF(new ArrayList<Roles>(List.of( ROLE_USER ))),
	
	ROLE_ADMIN(new ArrayList<Roles>(List.of( ROLE_USER,ROLE_STAFF )));
	
	private List<Roles> rolesInvolved;
	
	private Roles(List<Roles> rolesInvolved) {
		this.rolesInvolved = rolesInvolved;
		this.rolesInvolved.add(this);
	}
	
	public List<Roles> getRolesInvolved() {
		return rolesInvolved;
	}
	
	public List<GrantedAuthority> getGrantedAuthorities() {
		return rolesInvolved.stream()
				.map(r -> new SimpleGrantedAuthority(r.toString()))
				.collect(Collectors.toList());
	}
}
