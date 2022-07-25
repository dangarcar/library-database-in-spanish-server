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

import es.library.databaseserver.contenido.dao.ContenidoDetallesLibroDAO;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.model.DetallesLibroModel;

@Repository
public class DetallesLibroSQLiteRepo implements ContenidoDetallesLibroDAO{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Long> getAllLibroID() {
		final String sqlString = "SELECT ID FROM Detalles_Libros";
		
		return jdbcTemplate.query(sqlString, new RowMapper<Long>() {
				@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("ID");
			}
			
		});
	}
	
	@Override
	public Optional<DetallesLibroModel> getLibroByID(Long ID) {
		final String sqlString = "SELECT ID,ISBN,Paginas,Editorial FROM Detalles_Libros WHERE ID = :id";
		
		var contenidos = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), new LibroRowMapper());
		
		return Optional.ofNullable(contenidos.get(0));
	}
	
	@Override
	public DetallesLibroModel insertLibro(DetallesLibroModel libro) throws NotInsertedContenidoException {
		final String sqlString = "INSERT INTO Detalles_Libros(ID,ISBN,Paginas,Editorial) "+
				"VALUES(:id,:isbn,:paginas,:editorial)";
		
		var a = this.getLibroByID(libro.getID());
		
		if(a.isEmpty()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource()
					.addValue("id", libro.getID())
					.addValue("isbn", libro.getISBN())
					.addValue("paginas", libro.getPaginas())
					.addValue("editorial", libro.getEditorial())
				);
		}
		
		return this.getLibroByID(libro.getID()).orElseThrow(
				() -> new NotInsertedContenidoException("El contenido no ha sido insertado en la base de datos por alguna razon"));
	}
	
	@Override
	public DetallesLibroModel deleteLibroByID(Long ID) throws NoSuchContenidoException {
		final String sqlString = "DELETE FROM Detalles_Libros WHERE ID = :id";
		
		var a = this.getLibroByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID));
		}
		else {
			throw new NoSuchContenidoException("No existe tal contenido para ser borrado");
		}

		return this.getLibroByID(ID).isEmpty()? a.get():null;
	}
	
	@Override
	public DetallesLibroModel updateLibroByID(Long ID, DetallesLibroModel libro) throws NoSuchContenidoException {
		final String sqlString = "UPDATE Detalles_Libros SET "
				+ "ISBN = :isbn"
				+ ",Paginas = :paginas"
				+ ",Editorial = :editorial"
				+ " WHERE ID = :id";
		
		var a = this.getLibroByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource()
					.addValue("isbn", libro.getISBN())
					.addValue("paginas", libro.getPaginas())
					.addValue("editorial", libro.getEditorial())
					.addValue("id", ID)
				);
		}
		else {
			throw new NoSuchContenidoException("No se ha actualizado ningún contenido porque no existe ese contenido");
		}
		
		return this.getLibroByID(ID).isEmpty()? a.get():null;
	}

	private class LibroRowMapper implements RowMapper<DetallesLibroModel>{

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
	
}