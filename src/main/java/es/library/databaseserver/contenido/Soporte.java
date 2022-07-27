package es.library.databaseserver.contenido;

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
	private boolean audio;
	
	Soporte(boolean audio,boolean multimedia){
		this.multimedia = multimedia;
		this.audio = audio;
	}
	
	public boolean isMultimedia() {
		return multimedia;
	}
	
	public boolean isAudio() {
		return audio;
	}
}
