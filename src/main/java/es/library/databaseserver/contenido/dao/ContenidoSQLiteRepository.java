package es.library.databaseserver.contenido.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.model.ContenidoModel;
import es.library.databaseserver.contenido.model.Soporte;

@Repository(value = "contenidoSQLite")
public class ContenidoSQLiteRepository implements ContenidoDAO {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<ContenidoModel> getAllContenidos(){
		final String sqlString = "SELECT ID,Titulo,Autor,Descripcion,Año,Idioma,Soporte,DiasDePrestamo,Prestable,Disponible,FechaDisponibilidad,IDLibro,IDAudiovisual FROM Contenidos";
		
		return jdbcTemplate.query(sqlString, new MyRowMapper());
	}

	@Override
	public ContenidoModel getContenidoByID(Long ID) throws NoSuchContenidoException {
		final String sqlString = "SELECT ID,Titulo,Autor,Descripcion,Año,Idioma,Soporte,DiasDePrestamo,Prestable,Disponible,FechaDisponibilidad,IDLibro,IDAudiovisual FROM Contenidos WHERE ID = :id";
		
		var contenidos = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), new MyRowMapper());
		
		if(contenidos.isEmpty()) throw new NoSuchContenidoException("No existe ningun contenido con las condiciones puestas");
		
		return contenidos.get(0);
	}

	@Override
	public void insertContenido(ContenidoModel contenido){
		final String sqlString = "INSERT INTO Contenidos(Titulo,Autor,Descripcion,Año,Idioma,Soporte,DiasDePrestamo,Prestable,Disponible,IDLibro,IDAudiovisual) "+
				"VALUES(:titulo,:autor,:descripcion,:ano,:idioma,:soporte,:diasDePrestamo,:prestable,:disponible,:IdLibro,:IdAudiovisual)";
		 
		jdbcTemplate.update(sqlString, new MapSqlParameterSource()
				.addValue("titulo", contenido.getTitulo())
				.addValue("autor", contenido.getAutor())
				.addValue("descripcion", contenido.getDescripcion())
				.addValue("ano", contenido.getAno())
				.addValue("idioma", contenido.getIdioma())
				.addValue("soporte", contenido.getSoporte())
				.addValue("diasDePrestamo", contenido.getDiasDePrestamo())
				.addValue("prestable", contenido.getPrestable())
				.addValue("disponible", contenido.getDisponible())
				.addValue("IdLibro", contenido.getIDLibro())
				.addValue("IdAudiovisual", contenido.getIDAudiovisual())
			);
	}
	
	@Override
	public void deleteContenidoByID(Long ID) throws NoSuchContenidoException{
		final String sqlString = "DELETE FROM Contenidos WHERE ID = :id";
		
		int i = jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID));
		
		if(i == 0) throw new NoSuchContenidoException("No existe tal contenido para ser borrado");
	}

	@Override
	public void updateContenidoByID(Long ID, ContenidoModel contenido) throws NoSuchContenidoException{
		final String sqlString = "UPDATE Contenidos SET "
				+ "Titulo = :titulo"
				+ ",Autor = :autor"
				+ ",Descripcion = :descripcion"
				+ ",Año = :ano"
				+ ",Idioma = :idioma"
				+ ",Soporte = :soporte"
				+ ",DiasDePrestamo = :diasDePrestamo"
				+ ",Prestable = :prestable"
				+ ",Disponible = :disponible"
				+ ",IDLibro = :IdLibro"
				+ ",IDAudiovisual = :IdAudiovisual "
				+ " WHERE ID = :id";
		
		int i = jdbcTemplate.update(sqlString, new MapSqlParameterSource()
				.addValue("titulo", contenido.getTitulo())
				.addValue("autor", contenido.getAutor())
				.addValue("descripcion", contenido.getDescripcion())
				.addValue("ano", contenido.getAno())
				.addValue("idioma", contenido.getIdioma())
				.addValue("soporte", contenido.getSoporte())
				.addValue("diasDePrestamo", contenido.getDiasDePrestamo())
				.addValue("prestable", contenido.getPrestable())
				.addValue("disponible", contenido.getDisponible())
				.addValue("IdLibro", contenido.getIDLibro())
				.addValue("IdAudiovisual", contenido.getIDAudiovisual())
				.addValue("id", ID)
			);
		
		if(i == 0) throw new NoSuchContenidoException("No se ha actualizado ningún contenido porque posiblemente no existe ese contenido");
	}
    
}

class MyRowMapper implements RowMapper<ContenidoModel>{

	@Override
	public ContenidoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ContenidoModel(
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
				((rs.getString("FechaDisponibilidad") != null)? LocalDate.parse(rs.getString("FechaDisponibilidad"), DateTimeFormatter.ofPattern("yyyy-MM-dd")):null),
				rs.getLong("IDLibro"),
				rs.getLong("IDAudiovisual"));
	}
	
}