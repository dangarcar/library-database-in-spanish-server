package es.library.databaseserver.prestamos.search.dao.implementations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.crud.dao.rowmappers.IdRowMapper;
import es.library.databaseserver.prestamos.search.dao.PrestamoSearchDAO;

@Repository
public class PrestamoSearchSQLiteRepo implements PrestamoSearchDAO{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Long> getPrestamosByIdContenido(Long idContenido) {
		final String sqlString = "SELECT ID FROM Prestamos WHERE IDContenido = :idContenido";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("idContenido", idContenido), new IdRowMapper());
	}

	@Override
	public List<Long> getPrestamosIdPerfil(Long idPerfil) {
		final String sqlString = "SELECT ID FROM Prestamos WHERE IDPerfil = :idPerfil";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("idPerfil", idPerfil), new IdRowMapper());
	}

	@Override
	public List<Long> getPrestamosByDiasDePrestamo(int dias) {
		final String sqlString = "SELECT ID FROM Prestamos WHERE DiasDePrestamo = :dias";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("dias", dias), new IdRowMapper());
	}

	@Override
	public List<Long> getPrestamosByFechaPrestamo(String prestamo) {
		final String sqlString = "SELECT ID FROM Prestamos WHERE FechaHoraPrestamo LIKE :prestamo";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("prestamo", "%"+prestamo+"%"), new IdRowMapper());
	}

	@Override
	public List<Long> getPrestamosByFechaDevolucion(String devolucion) {
		final String sqlString = "SELECT ID FROM Prestamos WHERE FechaHoraDevolucion LIKE :devolucion";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("devolucion", "%"+devolucion+"%"), new IdRowMapper());
	}

	@Override
	public List<Long> getPrestamosBetweenTwoPrestamoDates(LocalDateTime from, LocalDateTime to) {
		final String sqlString = "SELECT ID FROM Prestamos WHERE FechaHoraPrestamo BETWEEN :from AND :to";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource()
				.addValue("from", from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				.addValue("to", to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)), new IdRowMapper());
	}

	@Override
	public List<Long> getPrestamosBetweenTwoDevolucionDates(LocalDateTime from, LocalDateTime to) {
		final String sqlString = "SELECT ID FROM Prestamos WHERE FechaHoraDevolucion BETWEEN :from AND :to";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource()
				.addValue("from", from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
				.addValue("to", to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)), new IdRowMapper());
	}
	
}
