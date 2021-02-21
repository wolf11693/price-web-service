
package com.xantrix.webapp.UnitTest.RepositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.xantrix.webapp.Application;
import com.xantrix.webapp.entities.DettListini;
import com.xantrix.webapp.repository.PrezziRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@TestPropertySource(locations = "classpath:application-list1.properties")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrezziRepositoryTest {
	@Autowired
	private PrezziRepository prezziRepository;

	@Test
	public void testFindByCodArtAndIdListino1_OK() {
		final String CodArt = "002000301";
		final String IdList = "1";

        final DettListini dettListino = this.prezziRepository.findByCodArtAndIdList(CodArt, IdList);
		assertThat( dettListino ).extracting(DettListini::getPrezzo)
							     .containsOnly(1.07, 1.07);
	}

	@Test
	public void testFindByCodArtAndIdListino2_OK() {
		final String CodArt = "002000301";
		final String IdList = "2";

        final DettListini dettListino = this.prezziRepository.findByCodArtAndIdList(CodArt, IdList);
		
        assertThat( dettListino ).extracting(DettListini::getPrezzo)
							     .containsOnly(0.87, 0.87);
	}

	@Test
	public void testFindByCodArtAndIdListino3_KO() {
		final String CodArt = "002000301";
		final String IdList = "3";	// idListino = 3 not exists into db

		final DettListini dettListino = this.prezziRepository.findByCodArtAndIdList(CodArt, IdList);
		assertThat( dettListino ).isNull();
	}
}