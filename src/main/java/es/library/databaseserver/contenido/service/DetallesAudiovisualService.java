package es.library.databaseserver.contenido.service;

import java.util.List;

import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;

public interface DetallesAudiovisualService {

	public List<DetallesAudiovisualModel> getAllAudiovisual();
		
	public DetallesAudiovisualModel getAudiovisualByID(long ID) throws ContenidoNotFoundException;
		
	public DetallesAudiovisualModel insertAudiovisual(DetallesAudiovisualModel audiovisual) throws DatabaseContenidoException;
		
	public void deleteAudiovisualByID(long ID)  throws ContenidoNotFoundException;
		
	public DetallesAudiovisualModel updateAudiovisualByID(long ID, DetallesAudiovisualModel audiovisual)  throws ContenidoNotFoundException, DatabaseContenidoException;
	
}
