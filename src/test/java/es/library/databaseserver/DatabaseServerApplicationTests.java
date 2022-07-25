package es.library.databaseserver;

import static es.library.databaseserver.contenido.model.ContenidoModelSetDTOMapper.ContenidoModelSetToContenido;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.Soporte;
import es.library.databaseserver.contenido.dto.Libros;
import es.library.databaseserver.contenido.dto.Videos;
import es.library.databaseserver.contenido.exceptions.NotValidSoporteException;
import es.library.databaseserver.contenido.exceptions.NotValidTypeContenidoException;
import es.library.databaseserver.contenido.model.ContenidoModelSet;
import es.library.databaseserver.contenido.model.ContenidoModelSetDTOMapper;
import es.library.databaseserver.contenido.model.DetallesAudiovisualModel;
import es.library.databaseserver.contenido.model.DetallesLibroModel;;

@SpringBootTest
class DatabaseServerApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void checkContenidoDTOMapper(){
		ContenidoModelSet cSet = null;
		ContenidoModelSet lSet = null;
		ContenidoModelSet vSet = null;
		/*Contenido c = null;
		*/try {
		cSet = new ContenidoModelSet(
					new Contenido(123L, "El principito", "Antoine", "Un gran libro", 1940, "Español", Soporte.DVD, true, 21, true, null, null, 10L), 
					new DetallesAudiovisualModel(10L, 200.0, true, 16, 1080),
//					null,
//					new DetallesLibroModel(10L, "65456456-645645-878", 239, "Planeta")
					null
					);
		
		/*c = ContenidoModelSetToContenido(cSet);
		
		System.out.println(c.toString());*/
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		/*assertTrue(c instanceof Videos);*/
		
		Contenido l = new Libros(123L, "El principito", "Antoine", "Un gran libro", 1940, "Español", Soporte.FISICO, true, 21, true, null, "65456456-645645-878",239,"Planeta", 10L);
		Contenido v = new Videos(123L, "El principito", "Antoine", "Un gran libro", 1940, "Español", Soporte.DVD   , true, 21, true, null, 200.0, 10L, 16, 1080);
		
		
		try {
//			lSet = ContenidoModelSetDTOMapper.ContenidoToContenidoModelSet(l);
			vSet = ContenidoModelSetDTOMapper.ContenidoToContenidoModelSet(v);
		} catch (NotValidTypeContenidoException | NotValidSoporteException e) {
			e.printStackTrace();
		}
		
//		assertEquals(cSet, lSet);
		assertEquals(cSet, vSet);
	}
}
