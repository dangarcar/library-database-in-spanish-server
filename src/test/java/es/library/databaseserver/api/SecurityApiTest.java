package es.library.databaseserver.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.library.databaseserver.api.snippets.PerfilSnippets;
import es.library.databaseserver.api.snippets.SecuritySnippets;
import es.library.databaseserver.perfil.Perfil;
import es.library.databaseserver.security.model.JWTTokenPair;
import es.library.databaseserver.security.model.LoginCredentials;

//Está terminada

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecurityApiTest {
	
	private MockMvc mockMvc;
	
	private static JWTTokenPair jwtTokenPair;
	
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
	void signUpTest() throws Exception {
		Perfil perfil = new Perfil(null, 
				"Test", 
				LocalDate.of(20, 12, 12), 
				"test@test.com", 
				"hkjgdsf432243", 
				null);
		
		MvcResult result = mockMvc.perform(post("/auth/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(perfil)))
				.andExpect(status().isCreated())
				.andDo(document("registrarse", 
						ApiTestUtils.HTTP_REQUEST, 
						PerfilSnippets.PERFIL_REQUEST, 
						SecuritySnippets.JWT_PAIR_RESPONSE))
				.andReturn();
		
		jwtTokenPair = mapper.readValue(result.getResponse().getContentAsString(), JWTTokenPair.class);
	}
	
	@Order(2)
	@Test
	@WithMockUser(roles = "ADMIN")
	void logoutTest() throws Exception {
		String user = "test@test.com";
		mockMvc.perform(put("/auth/logout/{username}",user)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("logout", 
						ApiTestUtils.HTTP_REQUEST,
						SecuritySnippets.USERNAME_PATH_PARAM));
	}
	
	@Order(3)
	@Test
	@WithMockUser(roles = "ADMIN")
	void loginTest() throws Exception {
		LoginCredentials credentials = new LoginCredentials(
				"test@test.com", "hkjgdsf432243");
		
		MvcResult result = mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(credentials)))
				.andExpect(status().isCreated())
				.andDo(document("login", 
						ApiTestUtils.HTTP_REQUEST,
						SecuritySnippets.LOGIN_CREDENTIALS_REQUEST,
						SecuritySnippets.JWT_PAIR_RESPONSE
						))
				.andReturn();
		jwtTokenPair = mapper.readValue(result.getResponse().getContentAsString(), JWTTokenPair.class);
	}
	
	@Order(4)
	@Test
	@WithMockUser(roles = "ADMIN")
	void refreshTokenTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/auth/token/refresh/{token}",
				jwtTokenPair.getRefreshToken())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("refresh-token", 
						ApiTestUtils.HTTP_REQUEST,
						SecuritySnippets.TOKEN_PATH_PARAM,
						SecuritySnippets.JWT_PAIR_RESPONSE
						))
				.andReturn();
		jwtTokenPair = mapper.readValue(result.getResponse().getContentAsString(), JWTTokenPair.class);
	}
	
	@Test
	@Order(5)
	@WithMockUser(roles = "ADMIN")
	void deletePerfilTest() throws Exception {
		String user = "test@test.com";
		mockMvc.perform(delete("/auth/delete/{username}",user)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("delete-perfil-logout",
						ApiTestUtils.HTTP_REQUEST,
						SecuritySnippets.USERNAME_PATH_PARAM));
	}
	
}