package com.xantrix.webapp.UnitTest.ControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xantrix.webapp.Application;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestPropertySource(locations = "classpath:application-list1.properties")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrezziControllerTestList1 {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		DefaultMockMvcBuilder mockMVCBuilder = MockMvcBuilders.webAppContextSetup(wac);
		this.mockMvc = mockMVCBuilder.build();
	}

	@Test
	public void A_getListino1CodArtTest() throws Exception {
		MockHttpServletRequestBuilder httpMockRequest = MockMvcRequestBuilders.get("http://localhost:50071/api/prezzi/cerca/codice/002000301")
																			  .contentType(MediaType.APPLICATION_JSON)
																			  .accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform( httpMockRequest )
				.andExpect( status().isOk() )
				.andExpect( content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) )
				.andExpect( jsonPath("$.id").exists() )
				.andExpect( jsonPath("$.codArt").exists() )
				.andExpect( jsonPath("$.codArt").value("002000301") )
				.andExpect( jsonPath("$.idList").exists() )
				.andExpect( jsonPath("$.idList").value("1") )
				.andExpect( jsonPath("$.prezzo").exists() )
				.andExpect( jsonPath("$.prezzo").value("1.07") )
				.andReturn();
	}

	@Test
	public void A_GetPrzCodArtTestListino1_OK() throws Exception {
		MockHttpServletRequestBuilder httpMockRequest = MockMvcRequestBuilders.get("http://localhost:50071/api/prezzi/002000301")
																			  .accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform( httpMockRequest )
				.andExpect( status().isOk())
				.andExpect( content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) )
				.andExpect( jsonPath("$").value("1.07") )
				.andReturn();
	}

	// MODIFICARE LA PROPRIETA' application.listino=3
	@Test
	public void B_GetPrzCodArtTestListino1_KO() throws Exception {
		MockHttpServletRequestBuilder httpMockRequest = MockMvcRequestBuilders.get("http://localhost:50071/api/prezzi/0020003012")
																			  .accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform( httpMockRequest)
				.andExpect( status().isOk())
				.andExpect( content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) )
				.andExpect( jsonPath("$").value("0.0")).andReturn();
	}


	@Test
	public void A_testInserimentoPrezzo() throws Exception {
		String jsonData = new StringBuilder()
									.append("{\n")
										.append("\"codArt\": \"002000301\",").append("\n")
										.append("\"idList\": \"3\",").append("\n")
										.append("\"prezzo\": 1.99").append("\n")
									.append("}\n")
									.toString();
					
		MockHttpServletRequestBuilder httpMockRequest = MockMvcRequestBuilders.post("http://localhost:50071/api/prezzi/inserisci")
																			  .contentType(MediaType.APPLICATION_JSON)
																			  .content(jsonData).accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform( httpMockRequest )
				.andExpect( status().isCreated() )
				.andDo (print() );
	}

	@Test
	public void E_testCancellazionePrezzo() throws Exception {
		MockHttpServletRequestBuilder httpMockRequest = MockMvcRequestBuilders.delete("http://localhost:50071/api/prezzi/elimina/002000301/3")
																			  .accept(MediaType.APPLICATION_JSON);
		
		mockMvc
				.perform( httpMockRequest )
				.andExpect( status().isOk() )
				.andExpect( jsonPath("$.code").value(200) )
				.andExpect( jsonPath("$.message").value("Eliminazione Prezzo Eseguita Con Successo") )
				.andDo( print() );
	}
}