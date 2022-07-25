package es.library.databaseserver.contenido.dao.implementations;

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

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.dao.ContenidoDAO;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;

@Repository
public class ContenidoSQLiteRepo implements ContenidoDAO {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Long> getAllContenidosID(){
		final String sqlString = "SELECT ID FROM Contenidos";
		
		return jdbcTemplate.query(sqlString, new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("ID");
			}
			
		});
	}
	
	@Override
	public Optional<Contenido> getContenidoByID(Long ID) throws NoSuchContenidoException {
		final String sqlString = "SELECT ID,Titulo,Autor,Descripcion,Año,Idioma,Soporte,DiasDePrestamo,Prestable,Disponible,FechaDisponibilidad,IDLibro,IDAudiovisual FROM Contenidos WHERE ID = :id";
		
		var contenidos = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), new ContenidoRowMapper());
		
		return Optional.ofNullable(contenidos.get(0));
	}
	
	@Override
	public Contenido insertContenido(Contenido contenido) throws NotInsertedContenidoException,ContenidoAlreadyExistsException {
		final String sqlString = "INSERT INTO Contenidos(Titulo,Autor,Descripcion,Año,Idioma,Soporte,DiasDePrestamo,Prestable,Disponible,IDLibro,IDAudiovisual) "+
				"VALUES(:titulo,:autor,:descripcion,:ano,:idioma,:soporte,:diasDePrestamo,:prestable,:disponible,:IdLibro,:IdAudiovisual)";
		 
		var a = this.getContenidoByID(contenido.getID());
		
		if(a.isEmpty()) {
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
		else {
			throw new ContenidoAlreadyExistsException("El contenido no puede ser insertado en la base de datos porque ya existe otro con su misma ID");
		}
		
		return this.getContenidoByID(contenido.getID()).orElseThrow(
				() -> new NotInsertedContenidoException("El contenido no ha sido insertado en la base de datos por alguna razon"));
	}
	
	@Override
	public Contenido deleteContenidoByID(Long ID) throws NoSuchContenidoException{
		final String sqlString = "DELETE FROM Contenidos WHERE ID = :id";
		
		var a = this.getContenidoByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID));
		}
		else {
			throw new NoSuchContenidoException("No existe tal contenido para ser borrado");
		}

		return this.getContenidoByID(ID).isEmpty()? a.get():null;
	}
	
	@Override
	public Contenido updateContenidoByID(Long ID, Contenido contenido) throws NoSuchContenidoException{
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
		
		var a = this.getContenidoByID(ID);
		
		if(a.isPresent()) {
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
					.addValue("id", ID)
				);
		}
		else {
			throw new NoSuchContenidoException("No existe tal contenido para ser actualizado");
		}
		
		return this.getContenidoByID(ID).isEmpty()? a.get():null;
	}
    
	private class ContenidoRowMapper implements RowMapper<Contenido>{

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
					((rs.getString("FechaDisponibilidad") != null)? LocalDate.parse(rs.getString("FechaDisponibilidad"), DateTimeFormatter.ofPattern("yyyy-MM-dd")):null),
					rs.getLong("IDLibro"),
					rs.getLong("IDAudiovisual")
				);
		}
		
	}
	
}