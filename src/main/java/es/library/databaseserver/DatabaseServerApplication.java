package es.library.databaseserver;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@SpringBootApplication
public class DatabaseServerApplication{

	public static void main(String[] args) {
		SpringApplication.run(DatabaseServerApplication.class, args);
	}
	
	@Bean(name = "baseDB")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource baseDb() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "baseJDBC")
	public NamedParameterJdbcTemplate baseJdbc(@Qualifier("baseDB") DataSource ds) {
		return new NamedParameterJdbcTemplate(ds);
	}
	
	@Bean(name = "tokenDB")
	@ConfigurationProperties(prefix = "spring.tokens")
	public DataSource tokenDb() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "tokenJDBC")
	public NamedParameterJdbcTemplate tokenJdbc(@Qualifier("tokenDB") DataSource ds) {
		return new NamedParameterJdbcTemplate(ds);
	}
	
}
