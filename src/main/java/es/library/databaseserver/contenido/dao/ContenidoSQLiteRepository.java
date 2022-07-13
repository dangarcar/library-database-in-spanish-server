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

import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.exceptions.ContenidoNotInsertedException;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.model.ContenidoModel;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.model.DetallesLibroModel;

@Repository(value = "contenidoSQLite")
public class ContenidoSQLiteRepository implements ContenidoDAO {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	//CONTENIDO BASICO
	
	@Override
	public List<ContenidoModel> getAllContenidos(){
		final String sqlString = "SELECT ID,Titulo,Autor,Descripcion,Año,Idioma,Soporte,DiasDePrestamo,Prestable,Disponible,FechaDisponibilidad,IDLibro,IDAudiovisual FROM Contenidos";
		
		return jdbcTemplate.query(sqlString, new ContenidoRowMapper());
	}
	@Override
	public ContenidoModel getContenidoByID(Long ID) throws NoSuchContenidoException {
		final String sqlString = "SELECT ID,Titulo,Autor,Descripcion,Año,Idioma,Soporte,DiasDePrestamo,Prestable,Disponible,FechaDisponibilidad,IDLibro,IDAudiovisual FROM Contenidos WHERE ID = :id";
		
		var contenidos = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), new ContenidoRowMapper());
		
		if(contenidos.isEmpty()) throw new NoSuchContenidoException("No existe ningun contenido con las condiciones puestas");
		
		return contenidos.get(0);
	}
	@Override
	public void insertContenido(ContenidoModel contenido) throws ContenidoNotInsertedException{
		final String sqlString = "INSERT INTO Contenidos(Titulo,Autor,Descripcion,Año,Idioma,Soporte,DiasDePrestamo,Prestable,Disponible,IDLibro,IDAudiovisual) "+
				"VALUES(:titulo,:autor,:descripcion,:ano,:idioma,:soporte,:diasDePrestamo,:prestable,:disponible,:IdLibro,:IdAudiovisual)";
		 
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
			);
		
		if(i==0) throw new ContenidoNotInsertedException("No se ha podido añadir el contenido a la base de datos");
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

	
	//DETALLES AUDIOVISUAL
	
	@Override
	public List<DetallesAudiovisualModel> getAllAudiovisual() {
		final String sqlString = "SELECT ID,Duracion,IsVideo,EdadRecomendada,Calidad FROM Detalles_Audiovisual";
		
		return jdbcTemplate.query(sqlString, new AudiovisualRowMapper());
	}

	@Override
	public DetallesAudiovisualModel getAudiovisualByID(Long ID) throws NoSuchContenidoException {
		final String sqlString = "SELECT ID,Duracion,IsVideo,EdadRecomendada,Calidad FROM Detalles_Audiovisual WHERE ID = :id";
		
		var contenidos = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), new AudiovisualRowMapper());
		
		if(contenidos.isEmpty()) throw new NoSuchContenidoException("No existe ningun contenido con las condiciones puestas");
		
		return contenidos.get(0);
	}

	@Override
	public void insertAudiovisual(DetallesAudiovisualModel audiovisual) throws ContenidoNotInsertedException {
		final String sqlString = "INSERT INTO Detalles_Audiovisual(ID,Duracion,IsVideo,EdadRecomendada,Calidad) "+
				"VALUES(:id,:duracion,:isVideo,:edadRecomendada,:calidad)";
		
		int i = jdbcTemplate.update(sqlString, new MapSqlParameterSource()
				.addValue("id", audiovisual.getID())
				.addValue("duracion", audiovisual.getDuracion())
				.addValue("isVideo", audiovisual.getIsVideo())
				.addValue("edadRecomendada", audiovisual.getEdadRecomendada())
				.addValue("calidad", audiovisual.getCalidad())
			);
		
		if(i==0) throw new ContenidoNotInsertedException("No se ha podido añadir el contenido a la base de datos");
	}

	@Override
	public void deleteAudiovisualByID(Long ID) throws NoSuchContenidoException {
		final String sqlString = "DELETE FROM Detalles_Audiovisual WHERE ID = :id";
		
		int i = jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID));
		
		if(i == 0) throw new NoSuchContenidoException("No existe tal contenido para ser borrado");
	}

	@Override
	public void updateAudiovisualByID(Long ID, DetallesAudiovisualModel audiovisual) throws NoSuchContenidoException {
		final String sqlString = "UPDATE Detalles_Audiovisual SET "
				+ "Duracion = :duracion"
				+ ",IsVideo = :isVideo"
				+ ",EdadRecomendada = :edadRecomendada"
				+ ",Calidad = :calidad"
				+ " WHERE ID = :id";
		
		int i = jdbcTemplate.update(sqlString, new MapSqlParameterSource()
				.addValue("duracion", audiovisual.getDuracion())
				.addValue("isVideo", audiovisual.getIsVideo())
				.addValue("edadRecomendada", audiovisual.getEdadRecomendada())
				.addValue("calidad", audiovisual.getCalidad())
				.addValue("id", ID)
			);
		
		if(i == 0) throw new NoSuchContenidoException("No se ha actualizado ningún contenido porque posiblemente no existe ese contenido");
	}
	
	//DETALLES LIBRO
	

	@Override
	public List<DetallesLibroModel> getAllLibro() {
		final String sqlString = "SELECT ID,ISBN,Paginas,Editorial FROM Detalles_Libros";
		
		return jdbcTemplate.query(sqlString, new LibroRowMapper());
	}
	@Override
	public DetallesLibroModel getLibroByID(Long ID) throws NoSuchContenidoException {
		final String sqlString = "SELECT ID,ISBN,Paginas,Editorial FROM Detalles_Libros WHERE ID = :id";
		
		var contenidos = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), new LibroRowMapper());
		
		if(contenidos.isEmpty()) throw new NoSuchContenidoException("No existe ningun contenido con las condiciones puestas");
		
		return contenidos.get(0);
	}

	@Override
	public void insertLibro(DetallesLibroModel libro) throws ContenidoNotInsertedException {
		final String sqlString = "INSERT INTO Detalles_Libros(ID,ISBN,Paginas,Editorial) "+
				"VALUES(:id,:isbn,:paginas,:editorial)";
		
		int i = jdbcTemplate.update(sqlString, new MapSqlParameterSource()
				.addValue("id", libro.getID())
				.addValue("isbn", libro.getISBN())
				.addValue("paginas", libro.getPaginas())
				.addValue("editorial", libro.getEditorial())
			);
		
		if(i==0) throw new ContenidoNotInsertedException("No se ha podido añadir el contenido a la base de datos");
	}

	@Override
	public void deleteLibroByID(Long ID) throws NoSuchContenidoException {
		final String sqlString = "DELETE FROM Detalles_Libros WHERE ID = :id";
		
		int i = jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID));
		
		if(i == 0) throw new NoSuchContenidoException("No existe tal contenido para ser borrado");
	}

	@Override
	public void updateLibroByID(Long ID, DetallesLibroModel libro) throws NoSuchContenidoException {
		final String sqlString = "UPDATE Detalles_Libros SET "
				+ "ISBN = :isbn"
				+ ",Paginas = :paginas"
				+ ",Editorial = :editorial"
				+ " WHERE ID = :id";
		
		int i = jdbcTemplate.update(sqlString, new MapSqlParameterSource()
				.addValue("isbn", libro.getISBN())
				.addValue("paginas", libro.getPaginas())
				.addValue("editorial", libro.getEditorial())
				.addValue("id", ID)
			);
		
		if(i == 0) throw new NoSuchContenidoException("No se ha actualizado ningún contenido porque posiblemente no existe ese contenido");
	}
    
}

class ContenidoRowMapper implements RowMapper<ContenidoModel>{

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
				rs.getLong("IDAudiovisual")
			);
	}
	
}

class AudiovisualRowMapper implements RowMapper<DetallesAudiovisualModel>{

	@Override
	public DetallesAudiovisualModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new DetallesAudiovisualModel(
				rs.getLong("ID"), 
				rs.getDouble("Duracion"),
				rs.getBoolean("IsVideo"),
				rs.getInt("EdadRecomendada"),
				rs.getInt("Calidad")
			);
	}
	
}

class LibroRowMapper implements RowMapper<DetallesLibroModel>{

	@Override
	public DetallesLibroModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new DetallesLibroModel(
				rs.getLong("ID"), 
				rs.getString("ISBN"),
				rs.getInt("Paginas"),
				rs.getString("Editorial")
			);
	}
	
}