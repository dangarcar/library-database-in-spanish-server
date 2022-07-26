package es.library.databaseserver.contenido.service;

import java.util.List;

import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;

public interface DetallesAudiovisualService {

	public List<DetallesAudiovisualModel> getAllAudiovisual();
		
	public DetallesAudiovisualModel getAudiovisualByID(long ID) throws NoSuchContenidoException;
		
	public DetallesAudiovisualModel insertAudiovisual(DetallesAudiovisualModel audiovisual) throws NotInsertedContenidoException;
		
	public void deleteAudiovisualByID(long ID)  throws NoSuchContenidoException;
		
	public DetallesAudiovisualModel updateAudiovisualByID(long ID, DetallesAudiovisualModel audiovisual)  throws NoSuchContenidoException, NotInsertedContenidoException;
	
}
