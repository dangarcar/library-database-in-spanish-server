package es.library.databaseserver.contenido.repository;

import java.sql.SQLException;
import java.util.List;

import es.library.databaseserver.contenido.Contenido;

public interface ContenidoRepository{

	public List<Contenido> getAllContenidos() throws SQLException;
	
	public Contenido getContenidoByID(Long ID) throws SQLException;
	
	public void insertContenido(Contenido contenido) throws SQLException;
	
	public void deleteContenidoByID(Long ID)  throws SQLException;
	
	public void updateContenidoByID(Long ID, Contenido contenido)  throws SQLException;
}
