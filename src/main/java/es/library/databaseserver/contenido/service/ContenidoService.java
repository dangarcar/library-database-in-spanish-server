package es.library.databaseserver.contenido.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.repository.ContenidoRepository;

@Service
public class ContenidoService {

	private final ContenidoRepository contenidoRepository;
	
	@Autowired
	public ContenidoService(@Qualifier("contenidoSQLite") ContenidoRepository contenidoRepository) {
		this.contenidoRepository = contenidoRepository;
	}
	
	public List<Contenido> getAllContenidos() throws SQLException {
		return contenidoRepository.getAllContenidos();
	}
	
	public Contenido getContenidoByID(Long ID) throws SQLException {
		return contenidoRepository.getContenidoByID(ID);
	}
	
	public void insertContenido(Contenido contenido) throws SQLException {
		contenidoRepository.insertContenido(contenido);
	}
	
	public void deleteContenidoByID(Long ID)  throws SQLException {
		contenidoRepository.deleteContenidoByID(ID);
	}
	
	public void updateContenidoByID(Long ID, Contenido contenido)  throws SQLException {
		contenidoRepository.updateContenidoByID(ID, contenido);
	}
	
}
