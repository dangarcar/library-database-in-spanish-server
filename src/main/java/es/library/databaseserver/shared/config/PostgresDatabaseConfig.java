package es.library.databaseserver.shared.config;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

@Configuration
public class PostgresDatabaseConfig {
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
			logger.info("Se creó la tabla de los contenidos");
		};
	}
	
	@Bean("setupPerfil")
	CommandLineRunner setupPerfilDatabase(@Qualifier("baseDB") DataSource ds) {
		return args -> {
			Resource resource = new ClassPathResource("/sql/SetupPerfiles.sql");
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
			populator.execute(ds);
			logger.info("Se creó la tabla de los perfiles");
		};
	}
	
	@Bean
	CommandLineRunner setupPrestamosDatabase(@Qualifier("baseDB") DataSource ds) {
		return args -> {
			Resource resource = new ClassPathResource("/sql/SetupPrestamos.sql");
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
			populator.execute(ds);
			logger.info("Se creó la tabla de los préstamos");
		};
	}
}
