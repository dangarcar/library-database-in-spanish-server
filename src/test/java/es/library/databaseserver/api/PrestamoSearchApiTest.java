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

import es.library.databaseserver.api.snippets.PrestamoSearchSnippets;
import es.library.databaseserver.api.snippets.PrestamoSnippets;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class PrestamoSearchApiTest {

	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext,
	  RestDocumentationContextProvider restDocumentation) {
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
	      .apply(documentationConfiguration(restDocumentation))
	      .build();
	}
	
	@Test
	void getPrestamosByParamsTest() throws Exception {
		mockMvc.perform(get("/prestamos/search")
				.params(PrestamoSearchSnippets.GET_PRESTAMOS_PARAMS_MAP)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(document("get-prestamos-by-params", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						PrestamoSearchSnippets.GET_PRESTAMOS_PARAMS_SNIPPET));
	}
	
	@Test
	void getPrestamoByIdTest() throws Exception {
		mockMvc.perform(get("/prestamos/search/id/{id}", -2873907485239740003L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andDo(document("get-prestamo-by-id", 
						ApiTestUtils.HTTP_REQUEST,ApiTestUtils.HTTP_RESPONSE,
						PrestamoSnippets.ID_PATH_PARAM));
	}
	
}
