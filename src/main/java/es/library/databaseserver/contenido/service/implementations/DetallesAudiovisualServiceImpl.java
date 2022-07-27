package es.library.databaseserver.contenido.service.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.library.databaseserver.contenido.dao.ContenidoDetallesAudiovisualDAO;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.service.DetallesAudiovisualService;

@Service
public class DetallesAudiovisualServiceImpl implements DetallesAudiovisualService{

	@Autowired
	private ContenidoDetallesAudiovisualDAO cDetallesAudiovisualDAO;
	
	public List<DetallesAudiovisualModel> getAllAudiovisual(){
		List<DetallesAudiovisualModel> lista = new ArrayList<>();
		for(long id:cDetallesAudiovisualDAO.getAllAudiovisualID()) {
			try {
				lista.add(getAudiovisualByID(id));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return lista;
	}
		
	public DetallesAudiovisualModel getAudiovisualByID(long ID) throws ContenidoNotFoundException {
		return cDetallesAudiovisualDAO.getAudiovisualByID(ID).orElseThrow(() -> new ContenidoNotFoundException("El contenido que busca no existe"));
	}
		
	public DetallesAudiovisualModel insertAudiovisual(DetallesAudiovisualModel audiovisual) throws DatabaseContenidoException {
		return cDetallesAudiovisualDAO.insertAudiovisual(audiovisual);
	}
		
	public void deleteAudiovisualByID(long ID)  throws ContenidoNotFoundException {
		cDetallesAudiovisualDAO.deleteAudiovisualByID(ID);
	}
		
	public DetallesAudiovisualModel updateAudiovisualByID(long ID, DetallesAudiovisualModel audiovisual)  throws ContenidoNotFoundException, DatabaseContenidoException{
		if(cDetallesAudiovisualDAO.getAudiovisualByID(ID).isEmpty()) {
			return cDetallesAudiovisualDAO.insertAudiovisual(audiovisual);
		}
		return cDetallesAudiovisualDAO.updateAudiovisualByID(ID, audiovisual);
	}
	
}