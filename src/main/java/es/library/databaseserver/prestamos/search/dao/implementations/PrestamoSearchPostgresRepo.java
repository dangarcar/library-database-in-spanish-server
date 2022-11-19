package es.library.databaseserver.prestamos.search.dao.implementations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.prestamos.search.dao.PrestamoSearchDAO;
import es.library.databaseserver.shared.Utils;

@Repository
public class PrestamoSearchPostgresRepo implements PrestamoSearchDAO{

	@Autowired
	@Qualifier("baseJDBC")
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Long> getPrestamosByIdContenido(Long idContenido) {
		final String sqlString = "SELECT \"ID\" FROM \"Prestamos\" WHERE \"IDContenido\" = :idContenido";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("idContenido", idContenido), Utils.idRowMapper);
	}

	@Override
	public List<Long> getPrestamosIdPerfil(Long idPerfil) {
		final String sqlString = "SELECT \"ID\" FROM \"Prestamos\" WHERE \"IDPerfil\" = :idPerfil";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("idPerfil", idPerfil), Utils.idRowMapper);
	}

	@Override
	public List<Long> getPrestamosByDiasDePrestamo(int dias) {
		final String sqlString = "SELECT \"ID\" FROM \"Prestamos\" WHERE \"DiasDePrestamo\" = :dias";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("dias", dias), Utils.idRowMapper);
	}
	@Override
	public List<Long> getPrestamosByDiasDePrestamo(int min, int max) {
		final String sqlString = "SELECT \"ID\" FROM \"Prestamos\" WHERE \"DiasDePrestamo\" BETWEEN :min AND :max";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("min", min).addValue("max", max), Utils.idRowMapper);
	}

	@Override
	public List<Long> getPrestamosByFechaPrestamo(String prestamo) {
		final String sqlString = "SELECT \"ID\" FROM \"Prestamos\" WHERE \"FechaHoraPrestamo\" LIKE :prestamo";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("prestamo", "%"+prestamo+"%"), Utils.idRowMapper);
	}

	@Override
	public List<Long> getPrestamosByFechaDevolucion(String devolucion) {
		final String sqlString = "SELECT \"ID\" FROM \"Prestamos\" WHERE \"FechaHoraDevolucion\" LIKE :devolucion";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("devolucion", "%"+devolucion+"%"), Utils.idRowMapper);
	}

	@Override
	public List<Long> getPrestamosBetweenTwoPrestamoDates(LocalDateTime from, LocalDateTime to) {
		final String sqlString = "SELECT \"ID\" FROM \"Prestamos\" WHERE \"FechaHoraPrestamo\" BETWEEN :from AND :to";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource()
				.addValue("from", from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				.addValue("to", to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)), Utils.idRowMapper);
	}

	@Override
	public List<Long> getPrestamosBetweenTwoDevolucionDates(LocalDateTime from, LocalDateTime to) {
		final String sqlString = "SELECT \"ID\" FROM \"Prestamos\" WHERE \"FechaHoraDevolucion\" BETWEEN :from AND :to";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource()
				.addValue("from", from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				.addValue("to", to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)), Utils.idRowMapper);
	}
	
	public List<Long> getContenidosMasPrestados(int nContenidos) {
		final String sqlString = "SELECT \"IDContenido\" AS \"ID\" FROM \"Prestamos\" GROUP BY \"IDContenido\" ORDER BY COUNT(*) DESC LIMIT :n;";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("n", nContenidos), Utils.idRowMapper);
	}
	
}
