package com.xantrix.webapp.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class RestExceptionHandlerController {

	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandlerController.class);

	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(ValidationException ex) {
		log.info("exceptionHandler - START - exceptionClass={}, exceptionMessage={}", ex.getClass().getSimpleName(), ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCodiceDiStato(HttpStatus.BAD_REQUEST.value());
		
		List<String> messages = new ArrayList<>();
		messages.add(ex.getMessage());
		errorResponse.setMessaggi(messages);

		log.info("exceptionHandler - END");
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NotFoundException.class)
	public final ResponseEntity<ErrorResponse> exceptionNotFoundHandler(NotFoundException ex) {
		log.info("exceptionNotFoundHandler - START - exceptionClass={}, exceptionMessage={}", ex.getClass().getSimpleName(), ex.getMessage());

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCodiceDiStato(HttpStatus.NOT_FOUND.value());
		
		List<String> messages = new ArrayList<>();
		messages.add(ex.getMessage());
		errorResponse.setMessaggi(messages);

		log.info("exceptionNotFoundHandler - END");
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		log.info("exceptionHandler - START - exceptionClass={}, exceptionMessage={}", ex.getClass().getSimpleName(), ex.getMessage());
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCodiceDiStato(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		List<String> messages = new ArrayList<>();
		messages.add("errore generico");
		messages.add(ex.getMessage());
		errorResponse.setMessaggi(messages);

		log.info("exceptionHandler - END");
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
