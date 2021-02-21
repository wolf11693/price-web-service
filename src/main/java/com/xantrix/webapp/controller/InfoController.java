package com.xantrix.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xantrix.webapp.config.ListinoConfig;

@RestController
@RequestMapping("api/listino")
public class InfoController {

	private static final Logger log = LoggerFactory.getLogger(InfoController.class);

	@Autowired
	private ListinoConfig appConfig;
	
	@GetMapping( path = "/info", 
				 produces = "application/json"
	)
	public ResponseEntity<?> getInfoListino(){
		log.info("==> GET /api/info/listino");
		log.info("getInfoListino - START");
		
		Map<String, String> infoMap = new HashMap<String, String>();
		infoMap.put("idListino", this.appConfig.getId());
		
		log.info("<== [{}]", infoMap);
		log.info("getInfoListino - END");
		return new ResponseEntity<>(infoMap, HttpStatus.OK);
	}
}
