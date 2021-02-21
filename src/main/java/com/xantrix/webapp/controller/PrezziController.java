package com.xantrix.webapp.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xantrix.webapp.config.ListinoConfig;
import com.xantrix.webapp.entities.DettListini;
import com.xantrix.webapp.exception.NotFoundException;
import com.xantrix.webapp.exception.ValidationException;
import com.xantrix.webapp.service.PrezziService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/api/prezzi")
public class PrezziController {

	private static final Logger log = LoggerFactory.getLogger(PrezziController.class);

	@Autowired
	private PrezziService prezziService;
	 
	@Autowired
	private ListinoConfig listinoConfig;
	 
	@Autowired
	private ResourceBundleMessageSource errorMessageValidation;

	@ApiOperation( value = "Ricerca il prezzo di un articolo in base al listino inserito nelle proprietà", 
		      	   notes = "Metodo richiamato da servizio esterno. Restituisce 0 in caso di prezzo non trovato",
		      	   response = Double.class
	)
	@ApiResponses( value = { @ApiResponse(  code = 200, 
						   				    message = "Chiamata Ok" )
				   }
	)
	@GetMapping( path = "/{codart}")
	public double getPriceCodArt(@ApiParam("Codice Articolo") @PathVariable("codart") String codiceArticolo) {
		log.info("==> GET /api/prezzi/{}", codiceArticolo);
		log.info("getPriceCodArt - START - codiceArticolo={}", codiceArticolo);
		
		Double returnValue = null;
		
		String idListino = this.listinoConfig.getId();
		log.info("Listino di Riferimento={}", idListino);
		
		DettListini dettaglioListino =  prezziService.SelPrezzo(codiceArticolo, idListino);
		
		if (dettaglioListino != null) {
			log.info("Prezzo Articolo: " + dettaglioListino.getPrezzo());
			returnValue = dettaglioListino.getPrezzo();
		}
		else {
			log.info("Prezzo Articolo Assente!!");
			returnValue = 0d;
		}

		log.info("<== [{}]", returnValue);
		log.info("getPriceCodArt - END");
		return returnValue;		 
	}
	
	@GetMapping( path = "/cerca/codice/{codArt}" )	 
	public ResponseEntity<?> getListCodArt(@ApiParam("Codice Articolo") @PathVariable("codArt") String codiceArticolo)  throws NotFoundException {
		log.info("==> GET /api/cerca/codice/{}", codiceArticolo);
		log.info("getListCodArt - START - codiceArticolo={}", codiceArticolo);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String idListino = this.listinoConfig.getId();
		
		log.info("Listino di Riferimento: " + idListino);
		
		DettListini dettListino =  prezziService.SelPrezzo(codiceArticolo, idListino);
		
		if (dettListino == null) {
			String ErrMsg = String.format("Il prezzo del listino %s del codice %s non è stato trovato!", idListino, codiceArticolo);
			log.info(ErrMsg);
			throw new NotFoundException(ErrMsg);
		}
		
		log.info("getListCodArt - END");
		return new ResponseEntity<>(dettListino,  headers, HttpStatus.OK);  
	}
	
	@PostMapping( value = "/inserisci", 
				  produces = "application/json"
	)
	public ResponseEntity<?> createPrice(
				@ApiParam("Codice Articolo") @Valid @RequestBody DettListini dettListino,
				BindingResult bindingResult,
				UriComponentsBuilder uriComponentsBuilder ) throws ValidationException { 
		log.info("==> POST /api/prezzi/inserisici");
		log.info("createPrice - START - dettListini={}", dettListino);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		if(bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			Locale locale = LocaleContextHolder.getLocale();
			String errorMsg = errorMessageValidation.getMessage(fieldError, locale);
			log.info("error validation occured in field={}, errorMessage={}", fieldError, errorMsg);
			throw new ValidationException(errorMsg);
		}

		this.prezziService.InsPrezzo(dettListino);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		responseNode.put("code", HttpStatus.OK.value());
		responseNode.put("message", "Inserimento Prezzo " + dettListino.getPrezzo() + " Eseguito Con Successo");

		log.info("createPrice - END");
		log.info("<== [{}]", responseNode.get("message"));
		return new ResponseEntity<>(responseNode, headers, HttpStatus.CREATED);
	}
	
	@DeleteMapping( value = "/elimina/{codart}/{idlist}", 
					produces = "application/json"
	)
	public ResponseEntity<?> deletePrice(
				@PathVariable("codart") String codiceArticolo, 
				@PathVariable("idlist") String idListino) throws NotFoundException, Exception {
		log.info("==> DELETE /api/prezzi/elimina/{}/{}", codiceArticolo, idListino);
		log.info("getListCodArt - START - codiceArticolo={}, idListino={}", codiceArticolo, idListino);
		
		HttpHeaders headers = new HttpHeaders();
		
		 DettListini dettagliListino = this.prezziService.SelPrezzo(codiceArticolo, idListino);
		 if(dettagliListino == null) {
			String errorMsg = String.format("Prezzo %s non presente in anagrafica! " , dettagliListino);
			log.info(errorMsg);
			throw new NotFoundException(errorMsg);
		}
		 
		this.prezziService.DelPrezzo(codiceArticolo, idListino);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		responseNode.put("code", HttpStatus.OK.value());
		responseNode.put("message", "Eliminazione Prezzo Eseguita Con Successo");
		
		log.info("getListCodArt - END");
		log.info("<== [{}]");
		return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);  
	}
}