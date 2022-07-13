package es.library.databaseserver.contenido.model;

/**
 * Enum que define los soportes en los que pueden estar los contenidos de la biblioteca
 * @author Daniel Garc�a
 *
 */
public enum Soporte {
	FISICO(false,false),
	E_BOOK(false,false),
	CD(true,true),
	DVD(true,true),
	BLURAY(true,true),
	VINILO(true,false),
	CASETE(true,false);
	
	private boolean multimedia;
	private boolean audiovisual;
	
	Soporte(boolean multimedia, boolean audiovisual){
		this.multimedia = multimedia;
		this.audiovisual = audiovisual;
	}
	
	boolean isMultimedia() {
		return multimedia;
	}
	
	boolean isAudiovisual() {
		return audiovisual;
	}
}
