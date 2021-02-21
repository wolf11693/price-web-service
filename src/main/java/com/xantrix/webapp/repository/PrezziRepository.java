package com.xantrix.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xantrix.webapp.entities.DettListini;

public interface PrezziRepository extends JpaRepository<DettListini, Long> {

	public DettListini findByCodArtAndIdList(String CodArt, String IdList);

	@Modifying
	@Query( value = "DELETE FROM dettlistini WHERE CodArt = :codiceArticolo AND IdList = :idListino", 
		    nativeQuery = true 
	)
	public void DelRowDettList( @Param("codiceArticolo") String codArt, 
								@Param("idListino") 	 String idList );
}
