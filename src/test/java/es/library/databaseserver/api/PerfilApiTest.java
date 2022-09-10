package es.library.databaseserver.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.library.databaseserver.api.snippets.PerfilSnippets;
import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.Roles;

//Está terminada

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PerfilApiTest {

	private MockMvc mockMvc;
	
	private static Perfil perfil;
	
	@Autowired
	private ObjectMapper mapper;
	
	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext,
	  RestDocumentationContextProvider restDocumentation) {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
	      .apply(documentationConfiguration(restDocumentation))
	      .build();
	}
	
	@Order(1)
	@Test
	void insertPerfilTest() throws Exception {
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
				.andDo(document("insert-perfil", 
						ApiTestUtils.HTTP_REQUEST,
						PerfilSnippets.PERFIL_REQUEST,
						PerfilSnippets.PERFIL_RESPONSE))
				.andReturn();
		perfil = this.mapper.readValue(result.getResponse().getContentAsString(), Perfil.class);
		perfil.setContrasena("hkjgdsf432243");
	}
	
	@Order(2)
	@Test
	void updatePerfilByIdTest() throws Exception {
		Perfil a = new Perfil(null, 
				"Test", 
				LocalDate.of(400, 12, 12), 
				"test@test.com", 
				"hkjgdsf432243", 
				Roles.ROLE_STAFF);
		MvcResult result = mockMvc.perform(put("/perfiles/{id}", perfil.getID())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(a)))
				.andDo(document("update-perfil-perfiles",
							ApiTestUtils.HTTP_REQUEST,
							PerfilSnippets.PERFIL_REQUEST,
							PerfilSnippets.PERFIL_RESPONSE,
							PerfilSnippets.ID_PATH_PARAM))
				.andReturn();
		perfil = this.mapper.readValue(result.getResponse().getContentAsString(), Perfil.class);
		perfil.setContrasena("hkjgdsf432243");
	}
	
	@Order(3)
	@Test
	void setRoleTest() throws Exception {
		mockMvc.perform(put("/perfiles/roles/{id}/{role}", perfil.getID(), "user")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("set-role", 
						ApiTestUtils.HTTP_REQUEST,
						PerfilSnippets.ROLE_PATH_PARAM));
	}
	
	@Order(4)
	@Test
	void deletePerfilByIdTest() throws Exception {
		mockMvc.perform(delete("/perfiles/{id}", perfil.getID())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("delete-perfil-perfiles", 
						ApiTestUtils.HTTP_REQUEST,
						PerfilSnippets.ID_PATH_PARAM));
	}
	
}