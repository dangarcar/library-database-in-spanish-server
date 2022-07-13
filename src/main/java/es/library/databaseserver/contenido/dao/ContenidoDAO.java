package es.library.databaseserver.contenido.dao;

import java.util.List;

import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.model.ContenidoModel;

public interface ContenidoDAO{

	public List<ContenidoModel> getAllContenidos();
	
	public ContenidoModel getContenidoByID(Long ID) throws NoSuchContenidoException;
	
	public void insertContenido(ContenidoModel contenido);
	
	public void deleteContenidoByID(Long ID)  throws NoSuchContenidoException;
	
	public void updateContenidoByID(Long ID, ContenidoModel contenido)  throws NoSuchContenidoException;
}
