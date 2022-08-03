package es.library.databaseserver.prestamos.crud.dao.implementations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.crud.dao.PrestamoDAO;
import es.library.databaseserver.prestamos.exceptions.DatabasePrestamoException;
import es.library.databaseserver.prestamos.exceptions.PrestamoNotFoundException;

@Repository
public class PrestamoSQLiteRepo implements PrestamoDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Long> getAllPrestamosID() {
		final String sqlString = "SELECT ID FROM Prestamos";
		
		return jdbcTemplate.query(sqlString, new IdRowMapper());
	}

	@Override
	public Optional<Prestamo> getPrestamoByID(Long ID) {
		final String sqlString = "SELECT ID,IDContenido,IDPerfil,FechaHoraPrestamo,FechaHoraDevolucion,DiasDePrestamo,Devuelto FROM Prestamos WHERE ID = :id";
		
		var prestamos = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), new PrestamoRowMapper());
		
		if(prestamos.isEmpty()) return Optional.empty();
		
		return Optional.ofNullable(prestamos.get(0));
	}

	private long getIdLastInserted() {
		final String sqlString = "SELECT seq AS ID FROM sqlite_sequence WHERE name = 'Prestamos';";
		
		var id = jdbcTemplate.query(sqlString, new IdRowMapper());
		
		if(id.isEmpty()) return -1L;
		
		return id.get(0);
	}
	
	@Override
	public Prestamo insertPrestamo(Prestamo prestamo) {
		final String sqlString = "INSERT INTO Prestamos(IDContenido,IDPerfil,FechaHoraPrestamo,FechaHoraDevolucion,DiasDePrestamo,Devuelto) "+
				"VALUES(:idContenido,:idPerfil,:fechaHoraPrestamo,:fechaHoraDevolucion,:diasDePrestamo,:devuelto)";

		final int i = jdbcTemplate.update(sqlString, new MapSqlParameterSource()
				.addValue("idContenido", prestamo.getIDContenido())
				.addValue("idPerfil", prestamo.getIDPerfil())
				.addValue("fechaHoraPrestamo", prestamo.getFechaHoraPrestamo())
				.addValue("fechaHoraDevolucion", prestamo.getFechaHoraDevolucion())
				.addValue("diasDePrestamo", prestamo.getDiasdePrestamo())
				.addValue("devuelto", prestamo.isDevuelto())
			);

		if(i == 0) throw new DatabasePrestamoException("El prestamo no ha sido insertado en la base de datos por alguna razon");

		long idContenido = getIdLastInserted();

		return this.getPrestamoByID(idContenido).orElseThrow(
				() -> new DatabasePrestamoException("El prestamo no ha sido insertado en la base de datos por alguna razon"));
	}
	

	@Override
	public void deletePrestamoByID(Long ID) throws PrestamoNotFoundException {
		final String sqlString = "DELETE FROM Prestamos WHERE ID = :id";
		
		var a = this.getPrestamoByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID));
		}
		else {
			throw new PrestamoNotFoundException("No existe tal prestamo para ser borrado");
		}
	}

	@Override
	public Prestamo updatePrestamoByID(Long ID, Prestamo prestamo) throws PrestamoNotFoundException {
		final String sqlString = "UPDATE Prestamos SET "
				+ " IDContenido = :idContenido,"
				+ " IDPerfil = :idPerfil,"
				+ " FechaHoraPrestamo = fechaHoraPrestamo,"
				+ " FechaHoraDevolucion = :fechaHoraDevolucion,"
				+ " DiasDePrestamo = :diasDePrestamo,"
				+ " Devuelto = :devuelto"
				+ " WHERE ID = :id";
		
		var a = this.getPrestamoByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource()
					.addValue("idContenido", prestamo.getIDContenido())
					.addValue("idPerfil", prestamo.getIDPerfil())
					.addValue("fechaHoraPrestamo", prestamo.getFechaHoraPrestamo())
					.addValue("fechaHoraDevolucion", prestamo.getFechaHoraDevolucion())
					.addValue("diasDePrestamo", prestamo.getDiasdePrestamo())
					.addValue("devuelto", prestamo.isDevuelto())
					.addValue("id", ID)
				);
		}
		else {
			throw new PrestamoNotFoundException("No existe tal prestamo para ser actualizado");
		}
			
		return this.getPrestamoByID(ID).orElse(null);
	}

	private class PrestamoRowMapper implements RowMapper<Prestamo>{

		@Override
		public Prestamo mapRow(ResultSet rs, int rowNum) throws SQLException {
			var p = new Prestamo(
					rs.getLong("ID"),
					rs.getLong("IDContenido"),
					rs.getLong("IDPerfil"),
					rs.getString("FechaHoraPrestamo") != null? LocalDateTime.parse(rs.getString("FechaHoraPrestamo")):null,
					rs.getString("FechaHoraDevolucion")!= null? LocalDateTime.parse(rs.getString("FechaHoraDevolucion")):null,
					rs.getInt("DiasDePrestamo"),
					rs.getBoolean("Devuelto")
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
