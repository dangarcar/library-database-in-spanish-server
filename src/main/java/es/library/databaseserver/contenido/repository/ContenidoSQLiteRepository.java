package es.library.databaseserver.contenido.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;

@Repository(value = "contenidoSQLite")
public class ContenidoSQLiteRepository implements ContenidoRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final List<Contenido> contenidosList = new ArrayList<>();
	
	@Override
	public List<Contenido> getAllContenidos() throws SQLException {
		String sqlString = "SELECT ID,Titulo,Autor,Descripcion,Año,Idioma,Soporte,DiasDePrestamo,Prestable,Disponible,FechadeDisponibilidad FROM Contenidos";
		
		List<Contenido> contenidos = jdbcTemplate.query(sqlString, new RowMapper<Contenido>(){			
			@Override
			public Contenido mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Contenido(
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
						(rs.getString("FechaDeDisponibilidad") != null)? LocalDate.parse(rs.getString("FechaDeDisponibilidad"), DateTimeFormatter.ofPattern("yyyy-MM-dd")):null);
			}
		});
		return contenidos;
	}

	@Override
	public Contenido getContenidoByID(Long ID) throws SQLException {
		return contenidosList.stream().filter(p -> p.getID().equals(ID)).collect(Collectors.toList()).get(0);
	}

	@Override
	public void insertContenido(Contenido contenido) throws SQLException {
		 String sqlString="INSERT INTO Contenidos(Titulo,Autor,Descripcion,Año,Idioma,Soporte,DiasDePrestamo,Prestable,Disponible) VALUES(?,?,?,?,?,?,?,?,?)";
		 Object p[] = {contenido.getTitulo(),
				 contenido.getAutor(),
				 contenido.getDescripcion(),
				 contenido.getAno(),
				 contenido.getIdioma(),
				 contenido.getSoporte(),
				 contenido.getPrestable(),
				 contenido.getDiasDePrestamo(),
				 contenido.getDisponible(),
				 };
		 
		 jdbcTemplate.update(sqlString, p);
	}

	@Override
	public void deleteContenidoByID(Long ID) throws SQLException {
		contenidosList.remove(getContenidoByID(ID));
	}

	@Override
	public void updateContenidoByID(Long ID, Contenido contenido) throws SQLException {
		int index = -1;
		
		for(var c:contenidosList) {
			if(ID.equals(contenido.getID())) {
				index = contenidosList.indexOf(c);
				break;
			}
		}
		
		if(index != -1) contenidosList.set(index, contenido);
	}
    
}