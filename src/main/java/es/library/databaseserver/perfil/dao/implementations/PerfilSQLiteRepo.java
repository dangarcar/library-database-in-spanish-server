package es.library.databaseserver.perfil.dao.implementations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.dao.PerfilDAO;
import es.library.databaseserver.perfil.exceptions.DatabasePerfilException;
import es.library.databaseserver.perfil.exceptions.PerfilNotFoundException;

@Repository
public class PerfilSQLiteRepo implements PerfilDAO{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Long> getAllPerfilesID() {
		final String sqlString = "SELECT ID FROM Perfiles";
		
		return jdbcTemplate.query(sqlString, new IdRowMapper());
	}

	@Override
	public Optional<Perfil> getPerfilByID(Long ID) {
		final String sqlString = "SELECT ID,Nombre,FechaDeNacimiento,CorreoElectronico,Password,Admin FROM Perfiles WHERE ID = :id";
		
		var perfiles = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), new PerfilRowMapper());
		
		if(perfiles.isEmpty()) return Optional.empty();
		
		return Optional.ofNullable(perfiles.get(0));
	}

	private long getIdLastInserted() {
		final String sqlString = "SELECT seq AS ID FROM sqlite_sequence WHERE name = 'Perfiles';";
		
		var id = jdbcTemplate.query(sqlString, new IdRowMapper());
		
		if(id.isEmpty()) return -1L;
		
		return id.get(0);
	}
	
	@Override
	public Perfil insertPerfil(Perfil perfil) {
		final String sqlString = "INSERT INTO Perfiles(Nombre,FechaDeNacimiento,CorreoElectronico,Password,Admin) "+
				"VALUES(:nombre,:fechaDeNacimiento,:correoElectronico,:password,:admin)";

		final int i = jdbcTemplate.update(sqlString, new MapSqlParameterSource()
				.addValue("nombre", perfil.getNombre())
				.addValue("fechaDeNacimiento", perfil.getFechaNacimiento())
				.addValue("correoElectronico", perfil.getCorreoElectronico())
				.addValue("password", perfil.getContrasena())
				.addValue("admin", perfil.isAdmin())
			);

		if(i == 0) throw new DatabasePerfilException("El perfil no ha sido insertado en la base de datos por alguna razon");

		long idContenido = getIdLastInserted();

		return this.getPerfilByID(idContenido).orElseThrow(
				() -> new DatabasePerfilException("El perfil no ha sido insertado en la base de datos por alguna razon"));
	}

	@Override
	public void deletePerfilByID(Long ID) throws PerfilNotFoundException{
		final String sqlString = "DELETE FROM Perfiles WHERE ID = :id";
		
		var a = this.getPerfilByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID));
		}
		else {
			throw new PerfilNotFoundException("No existe tal contenido para ser borrado");
		}
	}

	@Override
	public Perfil updatePerfilByID(Long ID, Perfil perfil) throws PerfilNotFoundException{
		final String sqlString = "UPDATE Perfiles SET "
				+ " Nombre = :nombre,"
				+ " FechaDeNacimiento = :fechaDeNacimiento,"
				+ " CorreoElectronico = :correoElectronico,"
				+ " Password = :password,"
				+ " Admin = :admin"
				+ " WHERE ID = :id";
		
		var a = this.getPerfilByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource()
					.addValue("nombre", perfil.getNombre())
					.addValue("fechaDeNacimiento", perfil.getFechaNacimiento())
					.addValue("correoElectronico", perfil.getCorreoElectronico())
					.addValue("password", perfil.getContrasena())
					.addValue("admin", perfil.isAdmin())
					.addValue("id", ID)
				);
		}
		else {
			throw new PerfilNotFoundException("No existe tal contenido para ser actualizado");
		}
		
		return this.getPerfilByID(ID).orElse(null);
	}
	
	private class PerfilRowMapper implements RowMapper<Perfil>{

		@Override
		public Perfil mapRow(ResultSet rs, int rowNum) throws SQLException {
			var p = new Perfil(
					rs.getLong("ID"),
					rs.getString("Nombre"),
					rs.getString("FechaDeNacimiento")!= null? LocalDate.parse(rs.getString("FechaDeNacimiento"), DateTimeFormatter.ofPattern("yyyy-MM-dd")):null,
					rs.getString("CorreoElectronico"),
					rs.getString("Password"),
					rs.getBoolean("Admin")
				);
			return p;
		}
		
	}
	
	private class IdRowMapper implements RowMapper<Long> {

		@Override
		public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getLong("ID");
		}
		
	}
	
}
