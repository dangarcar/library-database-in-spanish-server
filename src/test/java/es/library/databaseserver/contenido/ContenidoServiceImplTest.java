package es.library.databaseserver.contenido;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.library.databaseserver.contenido.dto.Audio;
import es.library.databaseserver.contenido.dto.Libros;
import es.library.databaseserver.contenido.dto.Videos;
import es.library.databaseserver.contenido.exceptions.ContenidoAlreadyExistsException;
import es.library.databaseserver.contenido.exceptions.ContenidoNotFoundException;
import es.library.databaseserver.contenido.exceptions.DatabaseContenidoException;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.service.ContenidoService;
import es.library.databaseserver.contenido.service.implementations.ContenidoServiceImpl;

@SpringBootTest
public class ContenidoServiceImplTest {

	@Autowired
	private ContenidoService testService;
	
	@Test
	@Disabled
	void getContenidoByIDTest() throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException {
		long id = 35l;
		
		Contenido expected = new Libros(id, 
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
				"678362-5-2387429874",
				79,
				"SM"
			);
		
		var result = testService.getContenidoByID(id);
		
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	@Disabled
	void getAllContenidosTest() {
		var result = testService.getAllContenidos();
		
		for(var a:result) {
			System.out.println(a);
		}
		
		assertThat(result.get(0)).isInstanceOf(Contenido.class);
	}
	
	@Test
	@Disabled
	void insertContenidoTest() throws DatabaseContenidoException, NotValidTypeContenidoException, NotValidSoporteException, ContenidoAlreadyExistsException {
		long id = 37l;
		Contenido expected = new Audio(id, 
				"El principito", 
				"g",
				"un libro para leer", 
				1860, 
				"Japones", 
				Soporte.CD, 
				true, 
				7, 
				true, 
				null, 
				100.67
//				16,
//				1080
			);
		
		var result = testService.insertContenido(expected);
		
		expected.setIDAudiovisual(result.getIDAudiovisual());
		
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	@Disabled
	void deleteContenidoTest() throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException {
		long id = 37L;
		
		testService.deleteContenidoByID(id);
	}
	
	@Test
//	@Disabled
	void updateContenidoByIdTest() throws ContenidoNotFoundException, NotValidTypeContenidoException, NotValidSoporteException, DatabaseContenidoException, ContenidoAlreadyExistsException {
		long id = 37l;
		Contenido expected = new Audio(id, 
				"El principito", 
				"g",
				"un libro para leer", 
				1860, 
				"Japones", 
				Soporte.VINILO, 
				true, 
				7, 
				true, 
				null, 
				45.327743
//				16,
//				1080
			);
		
		var result = testService.updateContenidoByID(id, expected);
		
		expected.setIDAudiovisual(result.getIDAudiovisual());
		
		assertThat(result).usingRecursiveComparison().isEqualTo(expected);
	}
}
