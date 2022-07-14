package es.library.databaseserver.contenido.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.exceptions.NoSuchContenidoException;

@Repository("contenidoFake")
public abstract class ContenidoFakeRepository implements ContenidoDAO {
	
	private final List<Contenido> contenidosList = new ArrayList<>();
	
	@Override
	public List<Contenido> getAllContenidos() {
		return contenidosList;
	}

	@Override
	public Contenido getContenidoByID(Long ID) throws NoSuchContenidoException {
		return contenidosList.stream().filter(p -> p.getID().equals(ID)).collect(Collectors.toList()).get(0);
	}

	@Override
	public void insertContenido(Contenido contenido) {
		contenidosList.add(contenido);
	}

	@Override
	public void deleteContenidoByID(Long ID) throws NoSuchContenidoException {
		contenidosList.remove(getContenidoByID(ID));
	}

	@Override
	public void updateContenidoByID(Long ID, Contenido contenido) {
		int index = -1;
		
		for(var c:contenidosList) {
			if(ID.equals(contenido.getID())) {
				index = contenidosList.indexOf(c);
				break;
			}
		}
		
		if(index != -1) contenidosList.set(index, contenido);
	}
    
}