package es.library.databaseserver.prestamos.crud.dao.implementations;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.prestamos.Prestamo;
import es.library.databaseserver.prestamos.crud.dao.PrestamoDAO;
import es.library.databaseserver.prestamos.exceptions.DatabasePrestamoException;
import es.library.databaseserver.prestamos.exceptions.PrestamoNotFoundException;
import es.library.databaseserver.shared.Utils;

@Repository
public class PrestamoPostgresRepo implements PrestamoDAO {

	@Autowired
	@Qualifier("baseJDBC")
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Long> getAllPrestamosID() {
		final String sqlString = "SELECT \"ID\" FROM \"Prestamos\"";
		
		return jdbcTemplate.query(sqlString, Utils.idRowMapper);
	}

	@Override
	public Optional<Prestamo> getPrestamoByID(Long ID) {
		final String sqlString = "SELECT \"ID\",\"IDContenido\",\"IDPerfil\",\"FechaHoraPrestamo\",\"FechaHoraDevolucion\",\"DiasDePrestamo\",\"Devuelto\" FROM \"Prestamos\" WHERE \"ID\" = :id";
		
		var prestamos = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), prestamoRowMapper);
		
		if(prestamos.isEmpty()) return Optional.empty();
		
		return Optional.ofNullable(prestamos.get(0));
	}
	
	@Override
	public Prestamo insertPrestamo(Prestamo prestamo) {
		final String sqlString = "INSERT INTO \"Prestamos\"(\"IDContenido\",\"IDPerfil\",\"FechaHoraPrestamo\",\"FechaHoraDevolucion\",\"DiasDePrestamo\",\"Devuelto\") "+
				"VALUES(:idContenido,:idPerfil,:fechaHoraPrestamo,:fechaHoraDevolucion,:diasDePrestamo,:devuelto) RETURNING \"ID\"";

		var i = jdbcTemplate.query(sqlString, new MapSqlParameterSource()
				.addValue("idContenido", prestamo.getIDContenido())
				.addValue("idPerfil", prestamo.getIDPerfil())
				.addValue("fechaHoraPrestamo", prestamo.getFechaHoraPrestamo()!=null? prestamo.getFechaHoraPrestamo().format(DateTimeFormatter.ISO_DATE_TIME):null)
				.addValue("fechaHoraDevolucion", prestamo.getFechaHoraDevolucion()!=null? prestamo.getFechaHoraDevolucion().format(DateTimeFormatter.ISO_DATE_TIME):null)
				.addValue("diasDePrestamo", prestamo.getDiasdePrestamo())
				.addValue("devuelto", prestamo.isDevuelto()),
				Utils.idRowMapper
			);

		if(i.isEmpty()) throw new DatabasePrestamoException("El prestamo no ha sido insertado en la base de datos por alguna razon");

		long idContenido = i.get(0);

		return this.getPrestamoByID(idContenido).orElseThrow(
				() -> new DatabasePrestamoException("El prestamo no ha sido insertado en la base de datos por alguna razon"));
	}
	

	@Override
	public void deletePrestamoByID(Long ID) throws PrestamoNotFoundException {
		final String sqlString = "DELETE FROM \"Prestamos\" WHERE \"ID\" = :id";
		
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
		final String sqlString = "UPDATE \"Prestamos\" SET "
				+ " \"IDContenido\" = :idContenido,"
				+ " \"IDPerfil\" = :idPerfil,"
				+ " \"FechaHoraPrestamo\" = :fechaHoraPrestamo,"
				+ " \"FechaHoraDevolucion\" = :fechaHoraDevolucion,"
				+ " \"DiasDePrestamo\" = :diasDePrestamo,"
				+ " \"Devuelto\" = :devuelto"
				+ " WHERE \"ID\" = :id";
		
		var a = this.getPrestamoByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource()
					.addValue("idContenido", prestamo.getIDContenido())
					.addValue("idPerfil", prestamo.getIDPerfil())
					.addValue("fechaHoraPrestamo", prestamo.getFechaHoraPrestamo()!=null? prestamo.getFechaHoraPrestamo().format(DateTimeFormatter.ISO_DATE_TIME):null)
					.addValue("fechaHoraDevolucion", prestamo.getFechaHoraDevolucion()!=null? prestamo.getFechaHoraDevolucion().format(DateTimeFormatter.ISO_DATE_TIME):null)
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

	private RowMapper<Prestamo> prestamoRowMapper = (rs, rowNum) -> {
		var p = new Prestamo(rs.getLong("ID"), rs.getLong("IDContenido"), rs.getLong("IDPerfil"),
				rs.getString("FechaHoraPrestamo") != null
						? ZonedDateTime.parse(rs.getString("FechaHoraPrestamo"), DateTimeFormatter.ISO_DATE_TIME)
						: null,
				rs.getString("FechaHoraDevolucion") != null ? ZonedDateTime.parse(rs.getString("FechaHoraDevolucion"),
						DateTimeFormatter.ISO_DATE_TIME) : null,
				rs.getInt("DiasDePrestamo"), rs.getBoolean("Devuelto"));
		return p;
	};
	
}
