package es.library.databaseserver.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

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

import es.library.databaseserver.api.snippets.PrestamoSnippets;
import es.library.databaseserver.contenido.Contenido;
import es.library.databaseserver.contenido.types.Audio;
import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.Roles;
import es.library.databaseserver.prestamos.Prestamo;

//Está terminada

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrestamoApiTest {

	private MockMvc mockMvc;
	
	private static Prestamo prestamo;
	
	private static Long idPerfil, idContenido;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	@Order(0)
	void createTestPerfilAndContenido() throws Exception {
		//Creo el perfil
		Perfil a = new Perfil(null, 
				"Test", 
				LocalDate.of(20, 12, 12), 
				"test@test.com", 
				"hkjgdsf432243", 
				Roles.ROLE_STAFF);
		MvcResult result = mockMvc.perform(post("/perfiles")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(a)))
				.andExpect(status().isCreated())
				.andReturn();
		idPerfil = this.mapper.readValue(result.getResponse().getContentAsString(), Perfil.class).getID();

		//Creo el contenido
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
				100.67
				);

		MvcResult result2 = mockMvc.perform(post("/contenidos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(contenido)))
				.andExpect(status().isCreated())
				.andReturn();
		idContenido = this.mapper.readValue(result2.getResponse().getContentAsString(), Audio.class).getID();
	}
	
	@Test
	@Order(2147483647)
	void deleteTestPerfilAndContenido() throws Exception {
		//Borro el perfil
		mockMvc.perform(delete("/perfiles/{id}", idPerfil)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		//Borro el contenido
		mockMvc.perform(delete("/contenidos/{id}", idContenido)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
	  RestDocumentationContextProvider restDocumentation) {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
	      .apply(documentationConfiguration(restDocumentation))
	      .build();
	}
	
	@Order(1)
	@Test
	void insertPrestamoTest() throws Exception {
		Prestamo p = new Prestamo(null, 
				100l, 
				100l, 
				LocalDateTime.now().minus(Period.ofDays(30)), 
				null, 
				400, 
				false);
		
		MvcResult result = mockMvc.perform(post("/prestamos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(p)))
				.andExpect(status().isCreated())
				.andDo(document("insert-prestamo", 
						ApiTestUtils.HTTP_REQUEST,
						PrestamoSnippets.PRESTAMO_REQUEST,
						PrestamoSnippets.PRESTAMO_RESPONSE))
				.andReturn();
		prestamo = this.mapper.readValue(result.getResponse().getContentAsString(), Prestamo.class);
		
	}
	
	@Order(2)
	@Test
	void updatePrestamoByIdTest() throws Exception {
		Prestamo p = new Prestamo(null, 
				100l, 
				100l, 
				LocalDateTime.now().minus(Period.ofDays(30)), 
				LocalDateTime.now(), 
				400, 
				true);
		
		MvcResult result = mockMvc.perform(put("/prestamos/{id}", prestamo.getID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(p)))
				.andExpect(status().isOk())
				.andDo(document("update-prestamo", 
						ApiTestUtils.HTTP_REQUEST,
						PrestamoSnippets.ID_PATH_PARAM,
						PrestamoSnippets.PRESTAMO_REQUEST,
						PrestamoSnippets.PRESTAMO_RESPONSE))
				.andReturn();
		prestamo = this.mapper.readValue(result.getResponse().getContentAsString(), Prestamo.class);
	}
	
	@Order(3)
	@Test
	void deletePrestamotest() throws Exception {
		mockMvc.perform(delete("/prestamos/{id}", prestamo.getID())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("delete-prestamo", 
						ApiTestUtils.HTTP_REQUEST,
						PrestamoSnippets.ID_PATH_PARAM));
	}
	
	@Order(4)
	@Test
	void prestarTransaccionesTest() throws Exception {
		MvcResult result = mockMvc.perform(post("/prestar")
				.param("perfil", idPerfil.toString())
				.param("contenido", idContenido.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("prestar-transacciones", 
						ApiTestUtils.HTTP_REQUEST,
						PrestamoSnippets.PRESTAR_DEVOLVER_PARAMS,
						PrestamoSnippets.PRESTAMO_RESPONSE))
				.andReturn();
		prestamo = this.mapper.readValue(result.getResponse().getContentAsString(), Prestamo.class);
	}
	
	@Order(5)
	@Test
	void devolverTransaccionesTest() throws Exception {
		//Devuelvo el contenido
		mockMvc.perform(post("/devolver")
				.param("perfil", idPerfil.toString())
				.param("contenido", idContenido.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("devolver-transacciones", 
						ApiTestUtils.HTTP_REQUEST,
						PrestamoSnippets.PRESTAR_DEVOLVER_PARAMS,
						PrestamoSnippets.PRESTAMO_RESPONSE));
		
		//Y borro el préstamo
		mockMvc.perform(delete("/prestamos/{id}", prestamo.getID())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
}