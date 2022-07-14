package es.library.databaseserver.contenido.model;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;

public class ContenidoModelSet {

	private final Contenido contenido;
	private final DetallesAudiovisualModel audiovisual;
	private final DetallesLibroModel libro;
	private final int type;
	
	public ContenidoModelSet(Contenido contenido, DetallesAudiovisualModel audiovisual, DetallesLibroModel libro) throws NotValidTypeContenidoException, NotValidSoporteException {		
		if((libro == null && audiovisual == null) || contenido == null) 
			throw new NotValidTypeContenidoException("Los contenidos deben ser de tipo libro o audiovisual");
		
		this.contenido = contenido;
		this.audiovisual = audiovisual;
		this.libro = libro;
		
		this.type = checkType();
	}
	
	public Contenido getContenido() {return contenido;}	
	public DetallesAudiovisualModel getAudiovisual() {return audiovisual;}	
	public DetallesLibroModel getLibro() {return libro;}
	public int getType() {return type;}
	
	int checkType() throws NotValidTypeContenidoException, NotValidSoporteException {
		int i = -1;
		
		//SI ES AUDIOVISUAL
		if(contenido.getIDAudiovisual() != null && audiovisual != null && contenido.getSoporte().isAudiovisual()) {
			
			if(audiovisual.getID().longValue() != contenido.getIDAudiovisual().longValue())
				throw new NotValidTypeContenidoException("El contenido parece no corresponderse con nada en la base de datos");
				
			i = AUDIO;
			
			//SI ES UN VIDEO
			if(audiovisual.getIsVideo()) {
				if(!contenido.getSoporte().isMultimedia())
					throw new NotValidSoporteException("El contenido de video debe estar en plataformas compatibles");
				
				i = VIDEO;
			}
				
		}
		//SI ES UN LIBRO
		else if(contenido.getIDLibro() != null && libro != null) {
			if(libro.getID().longValue() != contenido.getIDLibro().longValue())
				throw new NotValidTypeContenidoException("El contenido parece no corresponderse con nada en la base de datos");
			
			if(contenido.getSoporte().isAudiovisual() || contenido.getSoporte().isMultimedia())
				throw new NotValidSoporteException("El contenido de tipo libro debe estar en plataformas compatibles");
			
			i = LIBRO;	
		}
		else if(!contenido.getSoporte().isAudiovisual()) {
			throw new NotValidSoporteException("El contenido de tipo libro debe estar en plataformas compatibles");
		}
		
		if(i == -1) throw new NotValidTypeContenidoException("El contenido parece no tener tipo alguno");
		
		return i;
	}
	
	public static final int LIBRO = 0;
	public static final int AUDIO = 1;
	public static final int VIDEO = 2;
}
