package es.library.databaseserver.contenido.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.dao.ContenidoDAO;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.model.ContenidoModel;

@Service
public class ContenidoService {

	private final ContenidoDAO contenidoRepository;
	
	@Autowired
	public ContenidoService(@Qualifier("contenidoSQLite") ContenidoDAO contenidoRepository) {
		this.contenidoRepository = contenidoRepository;
	}
	
	public List<ContenidoModel> getAllContenidos(){
		return contenidoRepository.getAllContenidos();
	}
	
	public ContenidoModel getContenidoByID(Long ID) throws NoSuchContenidoException {
		return contenidoRepository.getContenidoByID(ID);
	}
	
	public void insertContenido(ContenidoModel contenido){
		contenidoRepository.insertContenido(contenido);
	}
	
	public void deleteContenidoByID(Long ID)  throws  NoSuchContenidoException {
		contenidoRepository.deleteContenidoByID(ID);
	}
	
	public void updateContenidoByID(Long ID, ContenidoModel contenido) throws NoSuchContenidoException {
		contenidoRepository.updateContenidoByID(ID, contenido);
	}
	
}
