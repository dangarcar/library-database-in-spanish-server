package es.library.databaseserver.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import es.library.databaseserver.api.snippets.ContenidoSearchSnippets;
import es.library.databaseserver.api.snippets.ContenidoSnippets;

//Está terminada

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class ContenidoSearchApiTest {
	
	private MockMvc mockMvc;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext,
	  RestDocumentationContextProvider restDocumentation) {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
	      .apply(documentationConfiguration(restDocumentation))
	      .build();
	}
	
	@Test
	void getContenidosByParamsTest() throws Exception {
		mockMvc.perform(get("/contenidos/search")
				.contentType(MediaType.APPLICATION_JSON)
				.params(ContenidoSearchSnippets.GET_CONTENIDOS_PARAMS_MAP))
				.andExpect(status().isOk())
				.andDo(document("get-contenidos-by-params", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						ContenidoSearchSnippets.GET_CONTENIDOS_PARAMS_SNIPPET));
	}
	
	@Test
	void getUniqueContenidosByParamsTest() throws Exception {
		mockMvc.perform(get("/contenidos/search/unique")
				.contentType(MediaType.APPLICATION_JSON)
				.params(ContenidoSearchSnippets.GET_UNIQUE_CONTENIDOS_PARAMS_MAP))
				.andExpect(status().isOk())
				.andDo(document("get-unique-contenidos-by-params", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						ContenidoSearchSnippets.GET_UNIQUE_CONTENIDOS_PARAMS_SNIPPET,
						ContenidoSearchSnippets.UNIQUE_CONTENIDOS_RESPONSE
						));
	}
	
	@Test
	void getTopPrestamosContenidosTest() throws Exception {
		mockMvc.perform(get("/contenidos/search/topprestamos")
				.param("limit", "5")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("get-contenidos-top-prestamos", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						ContenidoSearchSnippets.TOP_PRESTAMOS_PARAMS
						));
	}
	
	@Test
	void getContenidoByIdTest() throws Exception {
		mockMvc.perform(get("/contenidos/search/id/{id}", -76239876)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andDo(document("get-contenido-by-id", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						ContenidoSnippets.ID_PATH_PARAM));
	}
}
