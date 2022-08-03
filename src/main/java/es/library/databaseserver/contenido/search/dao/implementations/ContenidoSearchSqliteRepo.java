package es.library.databaseserver.contenido.search.dao.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.crud.dao.rowmappers.IdRowMapper;
import es.library.databaseserver.contenido.search.dao.ContenidoSearchDAO;

@Repository
public class ContenidoSearchSqliteRepo implements ContenidoSearchDAO{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	//TODO 
	@Override
	public List<Contenido> getUniqueContenidos(){
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Long> getContenidosIDByEditorial(String editorial) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Long> getContenidosIDByISBN(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}


	//Detalles Audiovisual
	@Override
	public List<Long> getContenidosIDByEdadRecomendada(Integer edad) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Long> getContenidosIDByDuracion(Double durecion) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Long> getContenidosIDByCalidad(Integer calidad) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
