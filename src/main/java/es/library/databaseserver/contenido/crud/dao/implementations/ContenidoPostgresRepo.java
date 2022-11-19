package es.library.databaseserver.contenido.crud.dao.implementations;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Contenido.Soporte;
import es.library.databaseserver.contenido.crud.dao.ContenidoDAO;
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.exceptions.IllegalContenidoException;
import es.library.databaseserver.shared.Utils;

@Repository
public class ContenidoPostgresRepo implements ContenidoDAO {
	
	@Autowired
	@Qualifier("baseJDBC")
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Long> getAllContenidosID(){
		final String sqlString = "SELECT \"ID\" FROM \"Contenidos\"";
		
		return jdbcTemplate.query(sqlString, Utils.idRowMapper);
	}
	
	@Override
	public Optional<Contenido> getContenidoByID(Long ID) {
		final String sqlString = "SELECT \"ID\",\"Titulo\",\"Autor\",\"Descripcion\",\"Year\",\"Idioma\",\"Soporte\",\"DiasDePrestamo\",\"Prestable\",\"Disponible\",\"IDLibro\",\"IDAudiovisual\",\"Imagen\" FROM \"Contenidos\" WHERE \"ID\" = :id";
		
		var contenidos = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), contenidoRowMapper);
		
		if(contenidos.isEmpty()) return Optional.empty();
		
		return Optional.ofNullable(contenidos.get(0));
	}
	
	@Override
	public Contenido insertContenido(Contenido contenido) throws DatabaseContenidoException,ContenidoAlreadyExistsException, IllegalContenidoException {
		final String sqlString = "INSERT INTO \"Contenidos\"(\"Titulo\",\"Autor\",\"Descripcion\",\"Year\",\"Idioma\",\"Soporte\",\"DiasDePrestamo\",\"Prestable\",\"Disponible\",\"IDLibro\",\"IDAudiovisual\",\"Imagen\") "+
				"VALUES(:titulo,:autor,:descripcion,:ano,:idioma,:soporte,:diasDePrestamo,:prestable,:disponible,:IdLibro,:IdAudiovisual,:imagen) RETURNING \"ID\"";
		 
		var i = jdbcTemplate.query(sqlString, new MapSqlParameterSource()
					.addValue("titulo", contenido.getTitulo())
					.addValue("autor", contenido.getAutor())
					.addValue("descripcion", contenido.getDescripcion())
					.addValue("ano", contenido.getAno())
					.addValue("idioma", contenido.getIdioma())
					.addValue("soporte", contenido.getSoporte().toString())
					.addValue("diasDePrestamo", contenido.getDiasDePrestamo())
					.addValue("prestable", contenido.getPrestable())
					.addValue("disponible", contenido.getDisponible())
					.addValue("IdLibro", contenido.getIDLibro())
					.addValue("IdAudiovisual", contenido.getIDAudiovisual())
					.addValue("imagen", contenido.getImagen()==null? null:contenido.getImagen().toString()),
					Utils.idRowMapper
				);
		
		if(i.isEmpty()) throw new DatabaseContenidoException("El contenido no ha sido insertado en la base de datos por alguna razon");
		
		long idContenido = i.get(0);
		
		return this.getContenidoByID(idContenido).orElseThrow(
				() -> new DatabaseContenidoException("El contenido no ha sido insertado en la base de datos por alguna razon"));
	}
	
	@Override
	public void deleteContenidoByID(Long ID) throws ContenidoNotFoundException{
		final String sqlString = "DELETE FROM \"Contenidos\" WHERE \"ID\" = :id";
		
		var a = this.getContenidoByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID));
		}
		else {
			throw new ContenidoNotFoundException("No existe tal contenido para ser borrado");
		}
	}
	
	@Override
	public Contenido updateContenidoByID(Long ID, Contenido contenido) throws ContenidoNotFoundException{		
		final String sqlString = "UPDATE \"Contenidos\" SET "
				+ "\"Titulo\" = :titulo"
				+ ",\"Autor\" = :autor"
				+ ",\"Descripcion\" = :descripcion"
				+ ",\"Year\" = :ano"
				+ ",\"Idioma\" = :idioma"
				+ ",\"Soporte\" = :soporte"
				+ ",\"DiasDePrestamo\" = :diasDePrestamo"
				+ ",\"Prestable\" = :prestable"
				+ ",\"Disponible\" = :disponible"
				+ ",\"IDLibro\" = :IdLibro"
				+ ",\"IDAudiovisual\" = :IdAudiovisual "
				+ ",\"Imagen\" = :imagen"
				+ " WHERE \"ID\" = :id";
		
		var a = this.getContenidoByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource()
					.addValue("titulo", contenido.getTitulo())
					.addValue("autor", contenido.getAutor())
					.addValue("descripcion", contenido.getDescripcion())
					.addValue("ano", contenido.getAno())
					.addValue("idioma", contenido.getIdioma())
					.addValue("soporte", contenido.getSoporte().toString())
					.addValue("diasDePrestamo", contenido.getDiasDePrestamo())
					.addValue("prestable", contenido.getPrestable())
					.addValue("disponible", contenido.getDisponible())
					.addValue("IdLibro", contenido.getIDLibro())
					.addValue("IdAudiovisual", contenido.getIDAudiovisual())
					.addValue("id", ID)
					.addValue("imagen", contenido.getImagen()==null? null:contenido.getImagen().toString())
				);
		}
		else {
			throw new ContenidoNotFoundException("No existe tal contenido para ser actualizado");
		}
		
		return this.getContenidoByID(ID).orElse(null);
	}
	
	private RowMapper<Contenido> contenidoRowMapper = (rs, rowNum) -> {
		Contenido c = null;
		try {
			c = new Contenido(rs.getLong("ID"), rs.getString("Titulo"), rs.getString("Autor"),
					rs.getString("Descripcion"), rs.getInt("Year"), rs.getString("Idioma"),
					Soporte.valueOf(rs.getString("Soporte")), rs.getBoolean("Prestable"), rs.getInt("DiasDePrestamo"),
					rs.getBoolean("Disponible"),rs.getString("Imagen")==null? null:new URL(rs.getString("Imagen")));
		} catch (MalformedURLException e) {
			new RuntimeException(e);
		}
		c.setIDLibro(rs.getLong("IDLibro") == 0L ? null : rs.getLong("IDLibro"));
		c.setIDAudiovisual(rs.getLong("IDAudiovisual") == 0L ? null : rs.getLong("IDAudiovisual"));
		return c;
	};
	
}