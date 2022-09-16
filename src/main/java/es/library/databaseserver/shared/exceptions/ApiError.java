package es.library.databaseserver.shared.exceptions;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ApiError {

	private final int statusCode;
	private final ZonedDateTime timestamp;
	private final String message;
	
	public ApiError(int statusCode, ZonedDateTime timestamp, String message) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	@JsonIgnore
	public ZonedDateTime getTimestampAsDateTime() {
		return timestamp;
	}

	public String getTimestamp() {
		return timestamp.format(DateTimeFormatter.ISO_DATE_TIME);
	}
	
	public String getMessage() {
		return message;
	}
	
	@JsonIgnore
	public String getJsonString() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		String errorMessage = mapper.writeValueAsString(this);
		return errorMessage;
	}
	
}
