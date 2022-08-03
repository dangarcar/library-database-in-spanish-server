package es.library.databaseserver.contenido.crud.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;

public class ContenidoRowMapper implements RowMapper<Contenido>{

	@Override
	public Contenido mapRow(ResultSet rs, int rowNum) throws SQLException {
		var c = new Contenido(
				rs.getLong("ID"), 
				rs.getString("Titulo"), 
				rs.getString("Autor"), 
				rs.getString("Descripcion"), 
				rs.getInt("Año"), 
				rs.getString("Idioma"), 
				Soporte.valueOf(rs.getString("Soporte")), 
				rs.getBoolean("Prestable"), 
				rs.getInt("DiasDePrestamo"), 
				rs.getBoolean("Disponible"), 
				((rs.getString("FechaDisponibilidad") != null)? LocalDate.parse(rs.getString("FechaDisponibilidad"), DateTimeFormatter.ofPattern("yyyy-MM-dd")):null)
			);
		c.setIDLibro(rs.getLong("IDLibro")==0L? null:rs.getLong("IDLibro"));
		c.setIDAudiovisual(rs.getLong("IDAudiovisual")==0L? null:rs.getLong("IDAudiovisual"));
		return c;
	}
	
}