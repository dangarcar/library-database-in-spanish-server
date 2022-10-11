package es.library.databaseserver.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;
import java.time.LocalDate;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.library.databaseserver.api.snippets.ContenidoSnippets;
import es.library.databaseserver.api.snippets.PerfilSnippets;
import es.library.databaseserver.api.snippets.PersonalSpaceSnippets;
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
public class PersonalSpaceApiTest {
	
	@Autowired 
	private ObjectMapper mapper;
	
	private MockMvc mockMvc;
	
	private static long idContenido;
	
	private final String username = "test@test.com";
	private final String password = "hkjgdsf432243";
	
	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext,
	  RestDocumentationContextProvider restDocumentation) {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
	      .apply(documentationConfiguration(restDocumentation))
	      .apply(SecurityMockMvcConfigurers.springSecurity())
	      .build();
	}
	
	//LA SECCIÓN DE PRÉSTAMOS
	
	@Order(1)
	@Test
	@WithMockUser(username = username, password = password, authorities = "ROLE_USER")
	void prestarTest() throws Exception {
		mockMvc.perform(post("/user/prestar/{id}", idContenido)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("prestar-user", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						ContenidoSnippets.ID_PATH_PARAM,
						PrestamoSnippets.PRESTAMO_RESPONSE));
	}

	@Order(2)
	@Test
	@WithMockUser(username = username, password = password, authorities =  {"ROLE_USER","ROLE_STAFF","ROLE_ADMIN"})
	void devolverTest() throws Exception {
		MvcResult result = mockMvc.perform(post("/user/devolver/{id}", idContenido)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("devolver-user", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						ContenidoSnippets.ID_PATH_PARAM,
						PrestamoSnippets.PRESTAMO_RESPONSE))
				.andReturn();

		//Y borro el préstamo
		mockMvc.perform(delete("/prestamos/{id}", 
				this.mapper.readValue(result.getResponse().getContentAsString(), Prestamo.class).getID())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	//LA SECCIÓN DE INFORMACIÓN PERSONAL
	
	@Order(3)
	@Test
	@WithMockUser(username = username, password = password, authorities = "ROLE_USER")
	public void getMyInfoTest() throws Exception {
		mockMvc.perform(get("/user")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("get-my-info", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						PerfilSnippets.PERFIL_RESPONSE));
	}
	
	@Order(4)
	@Test
	@WithMockUser(username = username, password = password, authorities = "ROLE_USER")
	void getHistorialDePrestamosTest() throws Exception {
		mockMvc.perform(get("/user/prestamos/historial")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("get-historial-prestamos", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						PersonalSpaceSnippets.LIST_CONTENIDO));
		
	}

	@Order(5)
	@Test
	@WithMockUser(username = username, password = password, authorities = "ROLE_USER")
	void getMyPrestamosTest() throws Exception {
		mockMvc.perform(get("/user/prestamos/todos")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("get-my-prestamos", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						PersonalSpaceSnippets.LIST_PRESTAMOS));
	}
	
	@Order(6)
	@Test
	@WithMockUser(username = username, password = password, authorities = "ROLE_USER")
	void getContenidosEnPrestamoTest() throws Exception {
		mockMvc.perform(get("/user/prestamos")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("get-contenidos-en-prestamos", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						PersonalSpaceSnippets.LIST_CONTENIDO));
	}
	
	@Order(7)
	@Test
	@WithMockUser(username = username, password = password, authorities = "ROLE_USER")
	void updateMyAccountTest() throws Exception {
		Perfil a = new Perfil(null, 
				"Test updated", 
				LocalDate.of(20, 12, 12), 
				"test@test.com", 
				"hkjgdsf432243", 
				Roles.ROLE_USER);
		mockMvc.perform(put("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(a)))
				.andExpect(status().isOk())
				.andDo(document("update-my-perfil", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						PerfilSnippets.PERFIL_REQUEST,
						PerfilSnippets.PERFIL_RESPONSE));	
	}
	
	@Order(8)
	@Test
	@WithMockUser(username = username, password = password, authorities = "ROLE_USER")
	void logoutTest() throws Exception {
		mockMvc.perform(post("/user/logout")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("logout-user", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE));
	}
	
	@Order(9)
	@Test
	@WithMockUser(username = username, password = password, authorities = "ROLE_USER")
	void deletePerfilTest() throws Exception {
		mockMvc.perform(delete("/user/delete")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("delete-perfil-user", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE));
	}
	
	//CREAR Y BORRAR LO NECESARIO PARA EL CONTENIDO
	
	@Test
	@Order(0)
	@WithMockUser(authorities = {"ROLE_USER","ROLE_STAFF","ROLE_ADMIN"})
	void createTestPerfilAndContenido() throws Exception {
		//Creo el perfil
		Perfil a = new Perfil(null, 
				"Test", 
				LocalDate.of(20, 12, 12), 
				"test@test.com", 
				"hkjgdsf432243", 
				Roles.ROLE_USER);
		mockMvc.perform(post("/perfiles")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(a)))
				.andExpect(status().isCreated());

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
				new URL("https://imagessl0.casadellibro.com/a/l/t5/60/9788495971760.jpg"),
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
	@WithMockUser(authorities = {"ROLE_USER","ROLE_STAFF","ROLE_ADMIN"})
	void deleteTestPerfilAndContenido() throws Exception {
		//Borro el contenido
		mockMvc.perform(delete("/contenidos/{id}", idContenido)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
}