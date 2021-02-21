package com.xantrix.webapp.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.xantrix.webapp.entities.DettListini;
import com.xantrix.webapp.repository.PrezziRepository;

@Service
@Transactional
public class PrezziServiceImpl implements PrezziService {
	
	private static Logger log = LoggerFactory.getLogger(PrezziServiceImpl.class);
	
	@Autowired
	PrezziRepository prezziRepository;

	@Override
	@Cacheable( value = "chacheprezzo", 
				key   = "#codArt", 
				sync  = true
	)
	public DettListini SelPrezzo(String codArt, String idListino) {
		log.info("SelPrezzo - START - codArt={}, idListino={}", codArt, idListino);
		
		DettListini dettListino = prezziRepository.findByCodArtAndIdList(codArt, idListino);
	
		log.info("SelPrezzo - END");
		return dettListino;
	}

	@Override
	@Caching( evict = { @CacheEvict( cacheNames = "chacheprezzo", 
									 key = "#dettListino.codArt" ) 
			  }
	)
	public DettListini InsPrezzo(DettListini dettListino) {
		log.info("InsPrezzo - START - dettListino={}", dettListino);
		
		DettListini dettListinoSaved = prezziRepository.save(dettListino);
		
		log.info("InsPrezzo - END - objSaved={}", dettListinoSaved);
		return dettListinoSaved;
	}

	@Override
	@Caching( evict = { @CacheEvict( cacheNames = "chacheprezzo",
									 key = "#codArt" )
			  }
	)
	public void DelPrezzo(String codArt, String idList) {
		log.info("DelPrezzo - START - CodArt={}, idList={}, ", codArt, idList);
		
		this.prezziRepository.DelRowDettList(codArt, idList);
		
		log.info("DelPrezzo - END");
	}
}
