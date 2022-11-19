package es.library.databaseserver.perfil.search.dao.implementations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.perfil.Roles;
import es.library.databaseserver.perfil.search.dao.PerfilSearchDAO;
import es.library.databaseserver.shared.Utils;

@Repository
public class PerfilSearchPostgresRepo implements PerfilSearchDAO {

	@Autowired
	@Qualifier("baseJDBC")
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Long> getPerfilesByPrompt(String prompt) {		
		final String query = "to_tsquery(:word)";
		final String vector = "to_tsvector('spanish',coalesce(cast(\"ID\" as VARCHAR),'')||' '||coalesce(\"Nombre\",'')||' '||coalesce(\"CorreoElectronico\",''))";
		final String sqlString = "SELECT \"ID\", ts_rank("+vector+", "+query+") AS \"RANK\" FROM \"Perfiles\" WHERE "+vector+" @@ "+query+" ORDER BY \"RANK\";";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("word", prompt), Utils.idRowMapper);
	}
	
	@Override
	public List<Long> getPerfilesByNombre(String nombre) {
		final String sqlString = "SELECT \"ID\" FROM \"Perfiles\" WHERE \"Nombre\" LIKE :nombre";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("nombre", "%"+nombre+"%"), Utils.idRowMapper);
	}

	@Override
	public List<Long> getPerfilesByEmail(String email) {
		final String sqlString = "SELECT \"ID\" FROM \"Perfiles\" WHERE \"CorreoElectronico\" LIKE :email";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("email", "%"+email+"%"), Utils.idRowMapper);
	}

	@Override
	public List<Long> getPerfilesByNacimiento(String nacimiento) {
		final String sqlString = "SELECT \"ID\" FROM \"Perfiles\" WHERE \"FechaDeNacimiento\" LIKE :nacimiento";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("nacimiento", "%"+nacimiento.toString()+"%"), Utils.idRowMapper);
	}

	public List<Long> getPerfilesBetweenTwoBirthDates(LocalDate from, LocalDate to) {
		final String sqlString = "SELECT \"ID\" FROM \"Perfiles\" WHERE \"FechaDeNacimiento\" BETWEEN :from AND :to";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource()
				.addValue("from", from.format(DateTimeFormatter.ISO_LOCAL_DATE))
				.addValue("to", to.format(DateTimeFormatter.ISO_LOCAL_DATE)), Utils.idRowMapper);
	}

	@Override
	public List<Long> getPerfilesByRole(Roles roles) {
		final String sqlString = "SELECT \"ID\" FROM \"Perfiles\" WHERE \"Roles\" = :roles";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("roles", roles.toString()), Utils.idRowMapper);
	}
}