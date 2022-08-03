package es.library.databaseserver.contenido.crud.dao.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.crud.dao.ContenidoDAO;
import es.library.databaseserver.contenido.crud.dao.rowmappers.ContenidoRowMapper;
import es.library.databaseserver.contenido.crud.dao.rowmappers.IdRowMapper;
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.exceptions.IllegalContenidoException;

@Repository
public class ContenidoSQLiteRepo implements ContenidoDAO {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Long> getAllContenidosID(){
		final String sqlString = "SELECT ID FROM Contenidos";
		
		return jdbcTemplate.query(sqlString, new IdRowMapper());
	}
	
	@Override
	public Optional<Contenido> getContenidoByID(Long ID) {
		final String sqlString = "SELECT ID,Titulo,Autor,Descripcion,Año,Idioma,Soporte,DiasDePrestamo,Prestable,Disponible,FechaDisponibilidad,IDLibro,IDAudiovisual FROM Contenidos WHERE ID = :id";
		
		var contenidos = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), new ContenidoRowMapper());
		
		if(contenidos.isEmpty()) return Optional.empty();
		
		return Optional.ofNullable(contenidos.get(0));
	}
	
	private long getIdLastInserted() {
		final String sqlString = "SELECT seq AS ID FROM sqlite_sequence WHERE name = 'Contenidos';";
		
		var id = jdbcTemplate.query(sqlString, new IdRowMapper());
		
		if(id.isEmpty()) return -1L;
		
		return id.get(0);
	}
	
	@Override
	public Contenido insertContenido(Contenido contenido) throws DatabaseContenidoException,ContenidoAlreadyExistsException, IllegalContenidoException {
		final String sqlString = "INSERT INTO Contenidos(Titulo,Autor,Descripcion,Año,Idioma,Soporte,DiasDePrestamo,Prestable,Disponible,IDLibro,IDAudiovisual) "+
				"VALUES(:titulo,:autor,:descripcion,:ano,:idioma,:soporte,:diasDePrestamo,:prestable,:disponible,:IdLibro,:IdAudiovisual)";
		 
		final int i = jdbcTemplate.update(sqlString, new MapSqlParameterSource()
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
		
		if(i == 0) throw new DatabaseContenidoException("El contenido no ha sido insertado en la base de datos por alguna razon");
		
		long idContenido = getIdLastInserted();
		
		return this.getContenidoByID(idContenido).orElseThrow(
				() -> new DatabaseContenidoException("El contenido no ha sido insertado en la base de datos por alguna razon"));
	}
	
	@Override
	public void deleteContenidoByID(Long ID) throws ContenidoNotFoundException{
		final String sqlString = "DELETE FROM Contenidos WHERE ID = :id";
		
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
		//Miro si el contenido es correcto para guardarlo en la base de datos
		contenido.checkIsCorrect();
		
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
				+ ",FechaDisponibilidad = :fecha"
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
					.addValue("fecha", contenido.getFechaDisponibilidad())
					.addValue("IdLibro", contenido.getIDLibro())
					.addValue("IdAudiovisual", contenido.getIDAudiovisual())
					.addValue("id", ID)
				);
		}
		else {
			throw new ContenidoNotFoundException("No existe tal contenido para ser actualizado");
		}
		
		return this.getContenidoByID(ID).orElse(null);
	}
	
}