package es.library.databaseserver.contenido.search.dao.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.crud.dao.rowmappers.IdRowMapper;
import es.library.databaseserver.contenido.search.dao.ContenidoSearchDAO;

@Repository
public class ContenidoSearchSqliteRepo implements ContenidoSearchDAO{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Long> getContenidosIDByPrompt(String prompt){
		final String sqlString = "SELECT ID FROM BusquedaContenidos WHERE BusquedaContenidos = :word ORDER BY RANK;";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("word", prompt), new IdRowMapper());
	}

	
	@Override
	public List<Long> getContenidosIDByTitulo(String titulo) {
		final String sqlString = "SELECT ID FROM Contenidos WHERE Titulo LIKE :titulo";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("titulo", "%"+titulo+"%"), new IdRowMapper());
	}	

	@Override
	public List<Long> getContenidosIDByAutor(String autor) {
		final String sqlString = "SELECT ID FROM Contenidos WHERE Autor LIKE :autor";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("autor", "%"+autor+"%"), new IdRowMapper());
	}

	@Override
	public List<Long> getContenidosIDByAno(Integer ano) {
		final String sqlString = "SELECT ID FROM Contenidos WHERE Año = :ano";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("ano", ano), new IdRowMapper());
	}

	@Override
	public List<Long> getContenidosIDByIdioma(String idioma) {
		final String sqlString = "SELECT ID FROM Contenidos WHERE Idioma LIKE :idioma";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("idioma", "%"+idioma+"%"), new IdRowMapper());
	}

	@Override
	public List<Long> getContenidosIDBySoporte(Soporte soporte) {
		final String sqlString = "SELECT ID FROM Contenidos WHERE Soporte LIKE :soporte";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("soporte", "%"+soporte+"%"), new IdRowMapper());
	}

	//Detalles libro
	@Override
	public List<Long> getContenidosIDByPaginas(Integer paginas) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Libros as DL on C.IDLibro = DL.ID WHERE DL.Paginas = :paginas";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("paginas", paginas), new IdRowMapper());
	}

	@Override
	public List<Long> getContenidosIDByEditorial(String editorial) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Libros as DL on C.IDLibro = DL.ID WHERE DL.Editorial LIKE :editorial";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("editorial", "%"+editorial+"%"), new IdRowMapper());
	}

	@Override
	public List<Long> getContenidosIDByISBN(String isbn) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Libros as DL on C.IDLibro = DL.ID WHERE DL.ISBN LIKE :isbn";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("isbn", "%"+isbn+"%"), new IdRowMapper());
	}

	//Detalles Audiovisual
	@Override
	public List<Long> getContenidosIDByEdadRecomendada(Integer edad) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Audiovisual as DA on C.IDAudiovisual = DA.ID WHERE DA.EdadRecomendada = :edad";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("edad", edad), new IdRowMapper());
	}

	@Override
	public List<Long> getContenidosIDByDuracion(Double duracion) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Audiovisual as DA on C.IDAudiovisual = DA.ID WHERE DA.Duracion = :duracion";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("duracion", duracion), new IdRowMapper());
	}

	@Override
	public List<Long> getContenidosIDByCalidad(Integer calidad) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Audiovisual as DA on C.IDAudiovisual = DA.ID WHERE DA.Calidad = :calidad";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("calidad", calidad), new IdRowMapper());
	}
}
