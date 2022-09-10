package es.library.databaseserver.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.library.databaseserver.api.snippets.ContenidoSnippets;
import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.types.Audio;
import es.library.databaseserver.contenido.types.Libro;
import es.library.databaseserver.contenido.types.Video;

//Está terminada

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContenidoApiTest {

	@Autowired
	private ObjectMapper mapper;
	
	private MockMvc mockMvc;
	
	private static Contenido contenido;
	
	private static long audioId;
	
	private static long videoId;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
	  RestDocumentationContextProvider restDocumentation) {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
	      .apply(documentationConfiguration(restDocumentation))
	      .build();
	}
	
	@Order(1)
	@Test
	void addAudioTest() throws Exception {
		Contenido contenido = new Audio(null, 
				"El principito", 
				"g", 
				"un libro para leer",
				1860, 
				"Japones",
				Contenido.Soporte.CD, 
				true, 
				7, 
				true, 
				null, 
				100.67);

		MvcResult result = mockMvc
				.perform(post("/contenidos")
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(contenido)))
				.andExpect(status().isCreated())
				.andDo(document("insert-audio", 
						ApiTestUtils.HTTP_REQUEST,
						ContenidoSnippets.AUDIO_REQUEST, 
						ContenidoSnippets.AUDIO_RESPONSE))
				.andReturn();
		audioId = this.mapper.readValue(result.getResponse().getContentAsString(), Audio.class).getID();
	}

	@Order(1)
	@Test
	void addVideoTest() throws Exception {
		Contenido contenido = new Video(null, 
				"El principito", 
				"g", 
				"un libro para leer", 
				1860, 
				"Japones",
				Contenido.Soporte.BLURAY, 
				true, 
				7, 
				true, 
				null,
				100.67, 
				16, 
				1080);

		MvcResult result = mockMvc
				.perform(post("/contenidos")
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(contenido)))
				.andExpect(status().isCreated())
				.andDo(document("insert-video", 
						ApiTestUtils.HTTP_REQUEST,
						ContenidoSnippets.VIDEO_REQUEST, 
						ContenidoSnippets.VIDEO_RESPONSE))
				.andReturn();
		videoId = this.mapper.readValue(result.getResponse().getContentAsString(), Video.class).getID();
	}
	
	@Order(1)
	@Test
	void addLibroTest() throws Exception {
		Contenido c = new Libro(null, 
				"Test", 
				"Test Autor",
				"Test Descripción", 
				0001, 
				"Test Idioma", 
				Contenido.Soporte.FISICO, 
				true, 
				21, 
				true, 
				null,
				"test ISBN",
				1,
				"test editorial"
			);
		
		MvcResult result = mockMvc.perform(post("/contenidos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(c)))
				.andExpect(status().isCreated())
				.andDo(document("insert-libro", 
						ApiTestUtils.HTTP_REQUEST,
						ContenidoSnippets.LIBRO_REQUEST, 
						ContenidoSnippets.LIBRO_RESPONSE))
				.andReturn();
		
		contenido = this.mapper.readValue(result.getResponse().getContentAsString(), Libro.class); 
	}
	
	@Order(2)
	@Test
	void updateContenidoByIdTest() throws Exception {
		Contenido c = new Libro(null, 
				"Updated", 
				"Test Autor",
				"Test Descripción", 
				0001, 
				"Test Idioma", 
				Contenido.Soporte.FISICO, 
				true, 
				21, 
				true, 
				null,
				"test ISBN",
				1,
				"test editorial"
			);
		
		MvcResult result = mockMvc.perform(put("/contenidos/{id}", contenido.getID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(c)))
				.andExpect(status().isOk())
				.andDo(document("update-contenido", 
						ApiTestUtils.HTTP_REQUEST,
						ContenidoSnippets.ID_PATH_PARAM,
						ContenidoSnippets.LIBRO_REQUEST,
						ContenidoSnippets.LIBRO_RESPONSE))
				.andReturn();
		contenido = this.mapper.readValue(result.getResponse().getContentAsString(), Contenido.class); 
	}
	
	@Order(3)
	@Test
	void deleteContenidoByIdTest() throws Exception {
		mockMvc.perform(delete("/contenidos/{id}", contenido.getID())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("delete-contenido", 
						ApiTestUtils.HTTP_REQUEST,
						ContenidoSnippets.ID_PATH_PARAM));
		
		mockMvc.perform(delete("/contenidos/{id}", audioId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		mockMvc.perform(delete("/contenidos/{id}", videoId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
}