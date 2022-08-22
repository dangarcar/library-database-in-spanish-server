package es.library.databaseserver.perfil;

import java.util.ArrayList;
import java.util.List;

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
}
