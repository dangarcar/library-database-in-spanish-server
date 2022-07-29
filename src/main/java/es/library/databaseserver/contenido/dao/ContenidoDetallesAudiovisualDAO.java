package es.library.databaseserver.contenido.dao;

import java.util.List;
import java.util.Optional;

import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;

public interface ContenidoDetallesAudiovisualDAO {
	
	public List<Long> getAllAudiovisualID();
	
	public Optional<DetallesAudiovisualModel> getAudiovisualByID(Long ID);
	
	public Optional<DetallesAudiovisualModel> getAudiovisualIfIdIsNull(DetallesAudiovisualModel audiovisualIdNull);
	
	public DetallesAudiovisualModel insertAudiovisual(DetallesAudiovisualModel audiovisual) throws DatabaseContenidoException;
	
	public DetallesAudiovisualModel deleteAudiovisualByID(Long ID) throws ContenidoNotFoundException;
	
	public DetallesAudiovisualModel updateAudiovisualByID(Long ID, DetallesAudiovisualModel audiovisual) throws ContenidoNotFoundException;

	public void deleteAudiovisualByIDIfIsNotPointed(Long ID, boolean ifOne) throws ContenidoNotFoundException;
	
}
