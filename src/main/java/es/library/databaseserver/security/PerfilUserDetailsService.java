package es.library.databaseserver.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.crud.dao.PerfilDAO;

@Component
public class PerfilUserDetailsService implements UserDetailsService{

	@Autowired
	private PerfilDAO perfilDAO;
	
	private Logger logger = LogManager.getLogger(PerfilUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Perfil perfil = perfilDAO.getPerfilByUsername(username).orElseThrow(() -> 
			new UsernameNotFoundException ("El usuario " + username + " no existe en la base de datos"));
		
		logger.info("Usuario {} encontrado en la base de datos", username);
		
		return new User(
				username, 
				perfil.getContrasena(),
				perfil.getRole().getGrantedAuthorities());
	}

}
