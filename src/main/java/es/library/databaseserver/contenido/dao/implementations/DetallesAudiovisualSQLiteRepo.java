package es.library.databaseserver.contenido.dao.implementations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.dao.ContenidoDetallesAudiovisualDAO;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;

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
		
		return Optional.ofNullable(contenidos.get(0));
	}
	
	@Override
	public DetallesAudiovisualModel insertAudiovisual(DetallesAudiovisualModel audiovisual) throws NotInsertedContenidoException {
		final String sqlString = "INSERT INTO Detalles_Audiovisual(ID,Duracion,IsVideo,EdadRecomendada,Calidad) "+
				"VALUES(:id,:duracion,:isVideo,:edadRecomendada,:calidad)";
		
		var a = this.getAudiovisualByID(audiovisual.getID());
		
		if(a.isEmpty()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource()
					.addValue("id", audiovisual.getID())
					.addValue("duracion", audiovisual.getDuracion())
					.addValue("isVideo", audiovisual.getIsVideo())
					.addValue("edadRecomendada", audiovisual.getEdadRecomendada())
					.addValue("calidad", audiovisual.getCalidad())
				);
		}

		return this.getAudiovisualByID(audiovisual.getID()).orElseThrow(
				() -> new NotInsertedContenidoException("El contenido no ha sido insertado en la base de datos por alguna razon"));
	}
	
	@Override
	public DetallesAudiovisualModel deleteAudiovisualByID(Long ID) throws NoSuchContenidoException{
		final String sqlString = "DELETE FROM Detalles_Audiovisual WHERE ID = :id";
		
		var a = this.getAudiovisualByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID));
		} 
		else {
			throw new NoSuchContenidoException("No existe tal contenido para ser borrado");
		}
		
		return this.getAudiovisualByID(ID).isEmpty()? a.get():null;
	}
	
	@Override
	public DetallesAudiovisualModel updateAudiovisualByID(Long ID, DetallesAudiovisualModel audiovisual) throws NoSuchContenidoException {
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
			throw new NoSuchContenidoException("No se ha actualizado ningún contenido porque no existe ese contenido");
		}
		
		return this.getAudiovisualByID(ID).isEmpty()? a.get():null;
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