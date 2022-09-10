package es.library.databaseserver.api;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;

public interface ApiTestUtils {

	OperationRequestPreprocessor HTTP_REQUEST = preprocessRequest(removeHeaders("Host","Content-Length"), prettyPrint());
	
}
