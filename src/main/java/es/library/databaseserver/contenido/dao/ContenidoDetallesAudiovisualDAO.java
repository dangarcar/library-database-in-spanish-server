package es.library.databaseserver.contenido.dao;

import java.util.List;
import java.util.Optional;

import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;
import es.library.databaseserver.contenido.exceptions.NotInsertedContenidoException;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;

public interface ContenidoDetallesAudiovisualDAO {
	
	public List<Long> getAllAudiovisualID();
	
	public Optional<DetallesAudiovisualModel> getAudiovisualByID(Long ID);
	
	public DetallesAudiovisualModel insertAudiovisual(DetallesAudiovisualModel audiovisual) throws NotInsertedContenidoException;
	
	public DetallesAudiovisualModel deleteAudiovisualByID(Long ID)  throws NoSuchContenidoException;
	
	public DetallesAudiovisualModel updateAudiovisualByID(Long ID, DetallesAudiovisualModel audiovisual)  throws NoSuchContenidoException;
	
}
