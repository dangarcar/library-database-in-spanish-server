package es.library.databaseserver.contenido.service.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.dao.ContenidoDetallesLibroDAO;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.model.DetallesLibroModel;
import es.library.databaseserver.contenido.service.DetallesLibroService;

@Service
public class DetallesLibroServiceImpl implements DetallesLibroService {
	
	@Autowired
	private ContenidoDetallesLibroDAO cDetallesLibroDAO;
	
	public List<DetallesLibroModel> getAllLibro(){
		List<DetallesLibroModel> lista = new ArrayList<>();
		for(long id:cDetallesLibroDAO.getAllLibroID()) {
			try {
				lista.add(getLibroByID(id));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return lista;
	}
		
	public DetallesLibroModel getLibroByID(long ID) throws NoSuchContenidoException {
		return cDetallesLibroDAO.getLibroByID(ID).orElseThrow(() -> new NoSuchContenidoException("El contenido que busca no existe"));
	}
		
	public DetallesLibroModel insertLibro(DetallesLibroModel libro) throws NotInsertedContenidoException{
		return cDetallesLibroDAO.insertLibro(libro);
	}
			
	public DetallesLibroModel deleteLibroByID(long ID) throws NoSuchContenidoException{
		return cDetallesLibroDAO.deleteLibroByID(ID);
	}
			
	public DetallesLibroModel updateLibroByID(long ID, DetallesLibroModel libro)  throws NoSuchContenidoException, NotInsertedContenidoException{
		if(cDetallesLibroDAO.getLibroByID(ID).isEmpty()) {
			return cDetallesLibroDAO.insertLibro(libro);
		}
		return cDetallesLibroDAO.updateLibroByID(ID, libro);
	}
	
}