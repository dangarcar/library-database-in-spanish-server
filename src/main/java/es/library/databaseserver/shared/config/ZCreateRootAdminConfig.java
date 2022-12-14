package es.library.databaseserver.shared.config;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.Roles;
import es.library.databaseserver.perfil.crud.dao.PerfilDAO;
import es.library.databaseserver.perfil.search.dao.PerfilSearchDAO;

// Esta clase tiene este nombre tan raro porque parece que spring ejecuta los CommandLineRunners en orden alfabético, y la anotación @Order ni la interfaz Ordered funcionaba
@Configuration
public class ZCreateRootAdminConfig{
	private Logger logger = LogManager.getLogger(getClass());
	
	@Bean
	@Autowired
	CommandLineRunner zcreateRootAdmin(PerfilDAO perfilDAO,PerfilSearchDAO perfilSearchDAO,PasswordEncoder passwordEncoder) {
		return args -> {
			Perfil admin = new Perfil(null, 
					"Base Admin", 
					LocalDate.of(1, 1, 1), 
					"admin", 
					passwordEncoder.encode("admin"), 
					Roles.ROLE_ADMIN);
			
			//Si no existe ningún administrador en la bbdd
			if(perfilSearchDAO.getPerfilesByRole(Roles.ROLE_ADMIN).isEmpty()) {
				perfilDAO.insertPerfil(admin);
				logger.info("Se creó el administrador con usuario {} y contraseña {}", admin.getCorreoElectronico(), "admin");
			}
			else logger.info("Ya había administradores en la base de datos, por lo que no ha hecho falta crear uno");
		};
	}
}
