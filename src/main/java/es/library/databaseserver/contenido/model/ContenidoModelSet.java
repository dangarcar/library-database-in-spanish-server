package es.library.databaseserver.contenido.model;

import java.util.Objects;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;

public class ContenidoModelSet {

	private final Contenido contenido;
	private final DetallesAudiovisualModel audiovisual;
	private final DetallesLibroModel libro;
	private int type = -1;
	
	public ContenidoModelSet(Contenido contenido, DetallesAudiovisualModel audiovisual, DetallesLibroModel libro) {				
		this.contenido = contenido;
		this.audiovisual = audiovisual;
		this.libro = libro;
		
		if(contenido == null) 
			throw new IllegalArgumentException("El contenido pasado por parametro es nulo");
	}
	
	public Contenido getContenido() {return contenido;}	
	public DetallesAudiovisualModel getAudiovisual() {return audiovisual;}	
	public DetallesLibroModel getLibro() {return libro;}
	public int getType() throws NotValidTypeContenidoException, NotValidSoporteException {
		if(type == -1) {
			type = checkType();
		}
		return type;
	}
	
	int checkType() throws NotValidTypeContenidoException, NotValidSoporteException {
		int i = -1;
		
		//SI ES AUDIOVISUAL
		if(contenido.getIDAudiovisual() != null && audiovisual != null && contenido.getSoporte().isAudio()) {
			
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
			
			if(contenido.getSoporte().isAudio() || contenido.getSoporte().isMultimedia())
				throw new NotValidSoporteException("El contenido de tipo libro debe estar en plataformas compatibles");
			
			i = LIBRO;	
		}
		else if(!contenido.getSoporte().isAudio()) {
			throw new NotValidSoporteException("El contenido de tipo libro debe estar en plataformas compatibles");
		}
		
		if(i == -1) throw new NotValidTypeContenidoException("El contenido parece no tener tipo alguno");
		
		return i;
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(audiovisual, contenido, libro);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContenidoModelSet other = (ContenidoModelSet) obj;
		return Objects.equals(audiovisual, other.audiovisual) && Objects.equals(contenido, other.contenido)
				&& Objects.equals(libro, other.libro);
	}



	public static final int LIBRO = 0;
	public static final int AUDIO = 1;
	public static final int VIDEO = 2;
}
