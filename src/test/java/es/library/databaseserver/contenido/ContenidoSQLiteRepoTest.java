package es.library.databaseserver.contenido;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import es.library.databaseserver.contenido.dao.implementations.ContenidoSQLiteRepo;
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;

@SpringBootTest
@Disabled
public class ContenidoSQLiteRepoTest {

	/*@Autowired
	private ContenidoSQLiteRepo testRepo;
	
	@Test
	void insertContenidoTest() {
		Contenido expected = new Contenido(35L, 
				"El principito", 
//				"g",
				"pepe", 
				"un libro para leer", 
				1860, 
				"Japones", 
				Soporte.E_BOOK, 
				true, 
				7, 
				true, 
				null, 
				1024L, 
				null
			);
		
		assertThatThrownBy(() -> testRepo.insertContenido(expected)).isInstanceOf(ContenidoAlreadyExistsException.class);
		
//		var result = testRepo.insertContenido(expected);
		
//		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	@Disabled
	void getContenidoByIdTest() {
		long id = 13L;
		Contenido expected = new Contenido(id, 
				"La catedral del Mar", 
				"Ildefonso Falcones", 
				"Uan catedral al lado del mar", 
				2004, 
				"Español", 
				Soporte.FISICO, 
				true, 
				21, 
				true, 
				null, 
				1024L, 
				null);
		
		var result = testRepo.getContenidoByID(id);
		
		assertThat(result.get()).isEqualTo(expected);
	}
	
	@Test
	void updateContenidoByIDTest() throws ContenidoNotFoundException {
		long id = 45L;
		Contenido expected = new Contenido(id, 
				"El principito", 
				"g",
				"un libro para leer", 
				1860, 
				"Japones", 
				Soporte.E_BOOK, 
				true, 
				7, 
				true, 
				null, 
				1024L, 
				null
			);
		
		var result = testRepo.updateContenidoByID(id, expected);
		
		assertThat(result).isEqualTo(expected);
		
	}*/
}
