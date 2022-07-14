package es.library.databaseserver;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.dto.Libros;
import es.library.databaseserver.contenido.dto.Videos;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.model.ContenidoModelSet;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.model.DetallesLibroModel;

import static es.library.databaseserver.contenido.service.ContenidoModelSetDTOMapper.ContenidoModelSetToContenido;;

@SpringBootTest
class DatabaseServerApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void checkContenidoDTOMapper(){
		Contenido c = null;
		try {
		ContenidoModelSet cSet = new ContenidoModelSet(
					new Contenido(123L, "El principito", "Antoine", "Un gran libro", 1940, "Español", Soporte.CD, true, 21, true, null, 10L, 10L), 
					new DetallesAudiovisualModel(10L, 200.0, false, 16, 1080),
//					null,
//					new DetallesLibroModel(10L, "65456456-645645-878", 239, "Planeta")
					null
					);
		
		c = ContenidoModelSetToContenido(cSet);
		
		System.out.println(c.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(c instanceof Videos);
	}
}
