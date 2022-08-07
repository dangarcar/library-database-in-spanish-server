package es.library.databaseserver.perfil.search.dao.implementations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.crud.dao.rowmappers.IdRowMapper;
import es.library.databaseserver.perfil.search.dao.PerfilSearchDAO;

@Repository
public class PerfilSearchSQLiteRepo implements PerfilSearchDAO{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Long> getPerfilesByNombre(String nombre) {
		final String sqlString = "SELECT ID FROM Perfiles WHERE Nombre LIKE :nombre";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("nombre", "%"+nombre+"%"), new IdRowMapper());
	}

	@Override
	public List<Long> getPerfilesByEmail(String email) {
		final String sqlString = "SELECT ID FROM Perfiles WHERE CorreoElectronico LIKE :email";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("email", "%"+email+"%"), new IdRowMapper());
	}

	@Override
	public List<Long> getPerfilesByNacimiento(String nacimiento) {
		final String sqlString = "SELECT ID FROM Perfiles WHERE FechaDeNacimiento LIKE :nacimiento";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("nacimiento", "%"+nacimiento.toString()+"%"), new IdRowMapper());
	}

	@Override
	public List<Long> getPerfilesByPrompt(String prompt) {
		final String sqlString = "SELECT ID FROM BusquedaPerfiles WHERE BusquedaPerfiles = :word ORDER BY RANK;";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("word", prompt), new IdRowMapper());
	}

	@Override
	public List<Long> getAllAdmins() {
		final String sqlString = "SELECT ID FROM Perfiles WHERE Admin = 1";
		
		return jdbcTemplate.query(sqlString, new IdRowMapper());
	}

	public List<Long> getPerfilesBetweenTwoBirthDates(LocalDate from, LocalDate to) {
		final String sqlString = "SELECT ID FROM Perfiles WHERE FechaDeNacimiento BETWEEN :from AND :to";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource()
				.addValue("from", from.format(DateTimeFormatter.ISO_LOCAL_DATE))
				.addValue("to", to.format(DateTimeFormatter.ISO_LOCAL_DATE)), new IdRowMapper());
	}
	
}
