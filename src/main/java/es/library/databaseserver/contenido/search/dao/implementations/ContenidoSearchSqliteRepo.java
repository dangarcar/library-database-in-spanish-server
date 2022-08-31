package es.library.databaseserver.contenido.search.dao.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.Contenido.Soporte;
import es.library.databaseserver.contenido.search.dao.ContenidoSearchDAO;
import es.library.databaseserver.shared.Utils;

@Repository
public class ContenidoSearchSqliteRepo implements ContenidoSearchDAO{

	@Autowired
	@Qualifier("baseJDBC")
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Long> getContenidosIDByPrompt(String prompt){
		final String sqlString = "SELECT ID FROM BusquedaContenidos WHERE BusquedaContenidos = :word ORDER BY RANK;";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("word", prompt), Utils.idRowMapper);
	}

	
	@Override
	public List<Long> getContenidosIDByTitulo(String titulo) {
		final String sqlString = "SELECT ID FROM Contenidos WHERE Titulo LIKE :titulo";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("titulo", "%"+titulo+"%"), Utils.idRowMapper);
	}	

	@Override
	public List<Long> getContenidosIDByAutor(String autor) {
		final String sqlString = "SELECT ID FROM Contenidos WHERE Autor LIKE :autor";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("autor", "%"+autor+"%"), Utils.idRowMapper);
	}

	@Override
	public List<Long> getContenidosIDByAno(Integer ano) {
		final String sqlString = "SELECT ID FROM Contenidos WHERE Año = :ano";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("ano", ano), Utils.idRowMapper);
	}
	@Override
	public List<Long> getContenidosIDByAno(Integer min, Integer max) {
		final String sqlString = "SELECT ID FROM Contenidos WHERE Año BETWEEN :min AND :max";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("min", min).addValue("max", max), Utils.idRowMapper);
	}

	@Override
	public List<Long> getContenidosIDByIdioma(String idioma) {
		final String sqlString = "SELECT ID FROM Contenidos WHERE Idioma LIKE :idioma";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("idioma", "%"+idioma+"%"), Utils.idRowMapper);
	}

	@Override
	public List<Long> getContenidosIDBySoporte(Soporte soporte) {
		final String sqlString = "SELECT ID FROM Contenidos WHERE Soporte LIKE :soporte";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("soporte", "%"+soporte+"%"), Utils.idRowMapper);
	}

	//Detalles libro
	@Override
	public List<Long> getContenidosIDByPaginas(Integer paginas) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Libros as DL on C.IDLibro = DL.ID WHERE DL.Paginas = :paginas";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("paginas", paginas), Utils.idRowMapper);
	}
	@Override
	public List<Long> getContenidosIDByPaginas(Integer min, Integer max) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Libros as DL on C.IDLibro = DL.ID WHERE DL.Paginas BETWEEN :min AND :max";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("min", min).addValue("max", max), Utils.idRowMapper);
	}
	
	@Override
	public List<Long> getContenidosIDByEditorial(String editorial) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Libros as DL on C.IDLibro = DL.ID WHERE DL.Editorial LIKE :editorial";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("editorial", "%"+editorial+"%"), Utils.idRowMapper);
	}

	@Override
	public List<Long> getContenidosIDByISBN(String isbn) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Libros as DL on C.IDLibro = DL.ID WHERE DL.ISBN LIKE :isbn";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("isbn", "%"+isbn+"%"), Utils.idRowMapper);
	}

	//Detalles Audiovisual
	@Override
	public List<Long> getContenidosIDByEdadRecomendada(Integer edad) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Audiovisual as DA on C.IDAudiovisual = DA.ID WHERE DA.EdadRecomendada = :edad";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("edad", edad), Utils.idRowMapper);
	}
	@Override
	public List<Long> getContenidosIDByEdadRecomendada(Integer min, Integer max) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Audiovisual as DA on C.IDAudiovisual = DA.ID WHERE DA.EdadRecomendada BETWEEN :min AND :max";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("min", min).addValue("max", max), Utils.idRowMapper);
	}
	
	@Override
	public List<Long> getContenidosIDByDuracion(Double duracion) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Audiovisual as DA on C.IDAudiovisual = DA.ID WHERE DA.Duracion = :duracion";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("duracion", duracion), Utils.idRowMapper);
	}
	@Override
	public List<Long> getContenidosIDByDuracion(Double min, Double max) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Audiovisual as DA on C.IDAudiovisual = DA.ID WHERE DA.Duracion BETWEEN :min AND :max";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("min", min).addValue("max", max), Utils.idRowMapper);
	}
	
	@Override
	public List<Long> getContenidosIDByCalidad(Integer calidad) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Audiovisual as DA on C.IDAudiovisual = DA.ID WHERE DA.Calidad = :calidad";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("calidad", calidad), Utils.idRowMapper);
	}
	@Override
	public List<Long> getContenidosIDByCalidad(Integer min, Integer max) {
		final String sqlString = "SELECT C.ID AS ID FROM Contenidos AS C INNER JOIN Detalles_Audiovisual as DA on C.IDAudiovisual = DA.ID WHERE DA.Calidad BETWEEN :min AND :max";
		
		return jdbcTemplate.query(sqlString, new MapSqlParameterSource().addValue("min", min).addValue("max", max), Utils.idRowMapper);
	}
}
