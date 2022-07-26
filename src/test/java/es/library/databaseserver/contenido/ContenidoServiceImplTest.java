package es.library.databaseserver.contenido;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.library.databaseserver.contenido.dto.Libros;
import es.library.databaseserver.contenido.service.implementations.ContenidoServiceImpl;

@SpringBootTest
@Disabled
public class ContenidoServiceImplTest {

	@Autowired
	private ContenidoServiceImpl testService;
	
	@Test
	@Disabled
	void getContenidoByIDTest() {
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
				"SM",
				1024l
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
	void insertContenidoTest() {
		long id = 36l;
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
				100,
				"SM",
				1025l
			);
		
		var result = testService.insertContenido(expected);
		
		assertThat(result).isEqualTo(expected);
	}
	
	@Test
	@Disabled
	void deleteContenidoTest() {
		long id = 36L;
		
		testService.deleteContenidoByID(id);
	}
	
	@Test
	void updateContenidoByIdTest() {
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
				100,
				"SM",
				1025l
			);
		
		var result = testService.updateContenidoByID(id, expected);
		
		assertThat(result).usingRecursiveComparison().isEqualTo(expected);
	}
}
