package es.library.databaseserver.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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

import es.library.databaseserver.api.snippets.PerfilSearchSnippets;
import es.library.databaseserver.api.snippets.PerfilSnippets;
import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.perfil.Roles;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PerfilSearchApiTest {

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
	
	@Order(0)
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
				.andReturn();
		perfil = this.mapper.readValue(result.getResponse().getContentAsString(), Perfil.class);
		perfil.setContrasena("hkjgdsf432243");
	}
	
	@Order(2147000000)
	@Test
	void deletePerfilByIdTest() throws Exception {
		mockMvc.perform(delete("/perfiles/{id}", perfil.getID())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Order(2)
	@Test
	void getPerfilByParamsTest() throws Exception {
		mockMvc.perform(get("/perfiles/search")
				.params(PerfilSearchSnippets.GET_PERFILES_PARAMS_MAP)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
					.andDo(document("get-perfil-by-params", 
							ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
							PerfilSearchSnippets.GET_PERFILES_PARAMS_SNIPPET));
	}
	
	@Order(3)
	@Test
	void getPerfilByUsernameTest() throws Exception {
		mockMvc.perform(get("/perfiles/search/username/{username}", "test@test.com")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("get-perfil-by-username", 
							ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
							PerfilSearchSnippets.USERNAME_PATH_PARAM,
							PerfilSnippets.PERFIL_RESPONSE));
	}
	
	@Order(1)
	@Test
	void getPerfilByIdTest() throws Exception {
		mockMvc.perform(get("/perfiles/search/id/{id}", perfil.getID())
				.params(PerfilSearchSnippets.GET_PERFILES_PARAMS_MAP)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
					.andDo(document("get-perfil-by-id", 
							ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
							PerfilSnippets.ID_PATH_PARAM,
							PerfilSnippets.PERFIL_RESPONSE));
	}
	
}
