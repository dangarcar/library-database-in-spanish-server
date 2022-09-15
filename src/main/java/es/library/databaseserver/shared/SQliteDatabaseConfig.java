package es.library.databaseserver.shared;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.Roles;
import es.library.databaseserver.perfil.crud.dao.PerfilDAO;
import es.library.databaseserver.perfil.search.dao.PerfilSearchDAO;

@Configuration
public class SQliteDatabaseConfig {
	private Logger logger = LogManager.getLogger(getClass());
	
	//Base database
	
	@Bean(name = "baseDB")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource baseDb() {
		logger.info("Se creó la base de datos de la biblioteca.");
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "baseJDBC")
	public NamedParameterJdbcTemplate baseJdbc(@Qualifier("baseDB") DataSource ds) {
		return new NamedParameterJdbcTemplate(ds);
	}

	@Bean
	CommandLineRunner setupContenidoDatabase(@Qualifier("baseDB") DataSource ds) {
		return args -> {
			Resource resource = new ClassPathResource("/sql/SetupContenidos.sql");
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
			populator.execute(ds);
		};
	}
	
	@Bean("setupPerfil")
	CommandLineRunner setupPerfilDatabase(@Qualifier("baseDB") DataSource ds) {
		return args -> {
			Resource resource = new ClassPathResource("/sql/SetupPerfiles.sql");
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
			populator.execute(ds);
		};
	}
	
	@Bean
	CommandLineRunner setupPrestamosDatabase(@Qualifier("baseDB") DataSource ds) {
		return args -> {
			Resource resource = new ClassPathResource("/sql/SetupPrestamos.sql");
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
			populator.execute(ds);
		};
	}
	
	@Bean
	CommandLineRunner setupContenidoTriggers(@Qualifier("baseDB") DataSource ds) {
		return args -> {
			Resource resource = new ClassPathResource("/sql/SetupContenidoTriggers.sql");
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
			populator.setSeparator(";;");
			populator.execute(ds);
		};
	}
	
	@Bean
	CommandLineRunner setupPerfilTriggers(@Qualifier("baseDB") DataSource ds) {
		return args -> {
			Resource resource = new ClassPathResource("/sql/SetupPerfilesTriggers.sql");
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
			populator.setSeparator(";;");
			populator.execute(ds);
		};
	}
	
	@Bean
	@Autowired
	CommandLineRunner createRootAdmin(PerfilDAO perfilDAO,PerfilSearchDAO perfilSearchDAO) {
		return args -> {
			Perfil admin = new Perfil(null, 
					"Base Admin", 
					LocalDate.of(1, 1, 1), 
					"admin", 
					"admin", 
					Roles.ROLE_ADMIN);
			
			//Si no existe ningún administrador en la bbdd
			if(perfilSearchDAO.getPerfilesByRole(Roles.ROLE_ADMIN).isEmpty()) {
				perfilDAO.insertPerfil(admin);
				logger.info("Se creó el administrador con usuario {} y contraseña {}", admin.getCorreoElectronico(), admin.getContrasena());
			}
			else logger.info("Ya había administradores en la base de datos, por lo que no ha hecho falta crear uno");
		};
	}
	
	//Token database
	
	@Bean(name = "tokenDB")
	@ConfigurationProperties(prefix = "spring.tokens")
	public DataSource tokenDb() {
		logger.info("Se creó la base de datos de los tokens.");
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "tokenJDBC")
	public NamedParameterJdbcTemplate tokenJdbc(@Qualifier("tokenDB") DataSource ds) {
		return new NamedParameterJdbcTemplate(ds);
	}
	
	@Bean
	CommandLineRunner setupTokenDatabase(@Qualifier("tokenDB") DataSource ds) {
		return args -> {
			Resource resource = new ClassPathResource("/sql/SetupTokens.sql");
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
			populator.execute(ds);
		};
	}
	
}
