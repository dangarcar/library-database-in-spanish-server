package es.library.databaseserver.contenido.crud.dao.implementations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.crud.dao.ContenidoDetallesAudiovisualDAO;
import es.library.databaseserver.contenido.crud.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;

@Repository
public class DetallesAudiovisualSQLiteRepo implements ContenidoDetallesAudiovisualDAO{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Long> getAllAudiovisualID() {
		final String sqlString = "SELECT ID FROM Detalles_Audiovisual";
		
		return jdbcTemplate.query(sqlString, new RowMapper<Long>() {
				@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("ID");
			}
			
		});
	}
	
	@Override
	public Optional<DetallesAudiovisualModel> getAudiovisualByID(Long ID) {
		final String sqlString = "SELECT ID,Duracion,IsVideo,EdadRecomendada,Calidad FROM Detalles_Audiovisual WHERE ID = :id";
		
		var contenidos = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), new AudiovisualRowMapper());
		
		if(contenidos.isEmpty()) return Optional.empty();
		
		return Optional.ofNullable(contenidos.get(0));
	}
	
	@Override
	public DetallesAudiovisualModel insertAudiovisual(DetallesAudiovisualModel audiovisual) throws DatabaseContenidoException {
		final String sqlString = "INSERT INTO Detalles_Audiovisual(Duracion,IsVideo,EdadRecomendada,Calidad) "+
				"VALUES(:duracion,:isVideo,:edadRecomendada,:calidad)";
		
		var a = this.getAudiovisualByID(audiovisual.getID());
		
		if(a.isEmpty()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource()
					.addValue("duracion", audiovisual.getDuracion())
					.addValue("isVideo", audiovisual.getIsVideo())
					.addValue("edadRecomendada", audiovisual.getEdadRecomendada()==0? null:audiovisual.getEdadRecomendada())
					.addValue("calidad", audiovisual.getCalidad()==0? null:audiovisual.getCalidad())
				);
		}

		return this.getAudiovisualIfIdIsNull(audiovisual).orElseThrow(
				() -> new DatabaseContenidoException("El contenido no ha sido insertado en la base de datos por alguna razon"));
	}
	
	@Override
	@Deprecated
	public DetallesAudiovisualModel deleteAudiovisualByID(Long ID) throws ContenidoNotFoundException{
		final String sqlString = "DELETE FROM Detalles_Audiovisual WHERE ID = :id";
		
		var a = this.getAudiovisualByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID));
		} 
		else {
			throw new ContenidoNotFoundException("No existe tal contenido para ser borrado");
		}
		
		return this.getAudiovisualByID(ID).isEmpty()? a.get():null;
	}
	
	@Override
	public DetallesAudiovisualModel updateAudiovisualByID(Long ID, DetallesAudiovisualModel audiovisual) throws ContenidoNotFoundException {
		final String sqlString = "UPDATE Detalles_Audiovisual SET "
				+ "Duracion = :duracion"
				+ ",IsVideo = :isVideo"
				+ ",EdadRecomendada = :edadRecomendada"
				+ ",Calidad = :calidad"
				+ " WHERE ID = :id";
		
		var a = this.getAudiovisualByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource()
					.addValue("duracion", audiovisual.getDuracion())
					.addValue("isVideo", audiovisual.getIsVideo())
					.addValue("edadRecomendada", audiovisual.getEdadRecomendada())
					.addValue("calidad", audiovisual.getCalidad())
					.addValue("id", ID)
				);
		}
		else {
			throw new ContenidoNotFoundException("No se ha actualizado ningún contenido porque no existe ese contenido");
		}
		
		return this.getAudiovisualByID(ID).get();
	}

	@Override
	public Optional<DetallesAudiovisualModel> getAudiovisualIfIdIsNull(DetallesAudiovisualModel audiovisualIdNull) {
		String sqlString;
		if(audiovisualIdNull.getIsVideo()) {
			sqlString = "SELECT ID,Duracion,IsVideo,EdadRecomendada,Calidad FROM Detalles_Audiovisual "
					+ "WHERE Duracion = :duracion AND IsVideo = :isVideo AND EdadRecomendada = :edad AND Calidad = :calidad";
		}
		else {
			sqlString = "SELECT ID,Duracion,IsVideo,EdadRecomendada,Calidad FROM Detalles_Audiovisual "
					+ "WHERE Duracion = :duracion AND IsVideo = :isVideo";
		}
		
		var contenidos = jdbcTemplate.query(sqlString, new MapSqlParameterSource()
				.addValue("duracion", audiovisualIdNull.getDuracion())
				.addValue("isVideo", audiovisualIdNull.getIsVideo())
				.addValue("edad", audiovisualIdNull.getEdadRecomendada(),Types.NULL)
				.addValue("calidad", audiovisualIdNull.getCalidad(),Types.NULL)
			,new AudiovisualRowMapper());
		
		if(contenidos.isEmpty()) return Optional.empty();
		
		return Optional.ofNullable(contenidos.get(0));
	}

	@Override
	public void deleteAudiovisualByIDIfIsNotPointed(Long ID, boolean ifOne) throws ContenidoNotFoundException {
		final String sqlString = "DELETE FROM Detalles_Audiovisual WHERE ID = :id AND (SELECT count(*) FROM Contenidos WHERE IDAudiovisual = :id) <= :num;";
		
		var a = this.getAudiovisualByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID).addValue("num", ifOne? 1:0));
		} 
		else {
			throw new ContenidoNotFoundException("No existe tal contenido para ser borrado");
		}
	}
	
	private class AudiovisualRowMapper implements RowMapper<DetallesAudiovisualModel>{

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
}