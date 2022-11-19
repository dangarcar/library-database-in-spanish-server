package es.library.databaseserver.contenido.crud.dao.implementations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.crud.dao.ContenidoDetallesLibroDAO;
import es.library.databaseserver.contenido.crud.model.DetallesLibroModel;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;

@Repository
public class DetallesLibroPostgresRepo implements ContenidoDetallesLibroDAO{

	@Autowired
	@Qualifier("baseJDBC")
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Long> getAllLibroID() {
		final String sqlString = "SELECT \"ID\" FROM \"Detalles_Libros\"";
		
		return jdbcTemplate.query(sqlString, new RowMapper<Long>() {
				@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("ID");
			}
			
		});
	}
	
	@Override
	public Optional<DetallesLibroModel> getLibroByID(Long ID) {
		final String sqlString = "SELECT \"ID\",\"ISBN\",\"Paginas\",\"Editorial\" FROM \"Detalles_Libros\" WHERE \"ID\" = :id";
		
		var contenidos = jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("id", ID), libroRowMapper);
		
		if(contenidos.isEmpty()) return Optional.empty();
		
		return Optional.ofNullable(contenidos.get(0));
	}
	
	@Override
	public DetallesLibroModel insertLibro(DetallesLibroModel libro) throws DatabaseContenidoException {
		final String sqlString = "INSERT INTO \"Detalles_Libros\"(\"ISBN\",\"Paginas\",\"Editorial\") "+
				"VALUES(:isbn,:paginas,:editorial)";
		
		var a = this.getLibroByID(libro.getID());
		
		if(a.isEmpty()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource()
					.addValue("isbn", libro.getISBN())
					.addValue("paginas", libro.getPaginas())
					.addValue("editorial", libro.getEditorial())
				);
		}
		
		return this.getLibroIfIdIsNull(libro).orElseThrow(
				() -> new DatabaseContenidoException("El contenido no ha sido insertado en la base de datos por alguna razon"));
	}
	
	/**
	 * Se prefiere usar <link>es.library.databaseserver.contenido.dao.ContenidoDetallesLibroDAO::deleteLibroByIDIfIsNotPointed </link>
	 */
	@Override
	@Deprecated
	public DetallesLibroModel deleteLibroByID(Long ID) throws ContenidoNotFoundException {
		final String sqlString = "DELETE FROM \"Detalles_Libros\" WHERE \"ID\" = :id";
		
		var a = this.getLibroByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID));
		}
		else {
			throw new ContenidoNotFoundException("No existe tal contenido para ser borrado");
		}

		return this.getLibroByID(ID).isEmpty()? a.get():null;
	}
	
	@Override
	public DetallesLibroModel updateLibroByID(Long ID, DetallesLibroModel libro) throws ContenidoNotFoundException {
		final String sqlString = "UPDATE \"Detalles_Libros\" SET "
				+ "\"ISBN\" = :isbn"
				+ ",\"Paginas\" = :paginas"
				+ ",\"Editorial\" = :editorial"
				+ " WHERE \"ID\" = :id";
		
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
			throw new ContenidoNotFoundException("No se ha actualizado ningún contenido porque no existe ese contenido");
		}
		
		return this.getLibroByID(ID).get();
	}

	@Override
	public Optional<DetallesLibroModel> getLibroIfIdIsNull(DetallesLibroModel libroIdNull) {
		final String sqlString = "SELECT \"ID\",\"ISBN\",\"Paginas\",\"Editorial\" FROM \"Detalles_Libros\" WHERE \"ISBN\" = :isbn AND \"Paginas\" = :paginas AND \"Editorial\" = :edit;";
		
		var contenidos = jdbcTemplate.query(sqlString, new MapSqlParameterSource()
					.addValue("isbn", libroIdNull.getISBN())
					.addValue("paginas", libroIdNull.getPaginas())
					.addValue("edit", libroIdNull.getEditorial())
			,libroRowMapper);
		
		if(contenidos.isEmpty()) return Optional.empty();
		
		return Optional.ofNullable(contenidos.get(0));
	}

	@Override
	public void deleteLibroByIDIfIsNotPointed(Long ID, boolean ifOne) throws ContenidoNotFoundException {
		final String sqlString = "DELETE FROM \"Detalles_Libros\" WHERE \"ID\" = :id AND (SELECT count(*) FROM \"Contenidos\" WHERE \"IDLibro\" = :id) <= :num;";
		
		var a = this.getLibroByID(ID);
		
		if(a.isPresent()) {
			jdbcTemplate.update(sqlString, new MapSqlParameterSource().addValue("id", ID).addValue("num", ifOne? 1:0));
		}
		else {
			throw new ContenidoNotFoundException("No existe tal contenido para ser borrado");
		}
	}
	
	private RowMapper<DetallesLibroModel> libroRowMapper = (rs, rowNum) -> {
		return new DetallesLibroModel(rs.getLong("ID"), rs.getString("ISBN"), rs.getInt("Paginas"),
				rs.getString("Editorial"));
	};
}