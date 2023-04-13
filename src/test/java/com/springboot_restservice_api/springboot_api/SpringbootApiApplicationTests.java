package com.springboot_restservice_api.springboot_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
// import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot_restservice_api.springboot_api.controller.LibraryController;
import com.springboot_restservice_api.springboot_api.entity.AddResponse;
import com.springboot_restservice_api.springboot_api.entity.Library;
import com.springboot_restservice_api.springboot_api.repository.LibraryRepository;
import com.springboot_restservice_api.springboot_api.service.LibraryService;

@SpringBootTest
@AutoConfigureMockMvc
class SpringbootApiApplicationTests {

	@Autowired
	LibraryController libraryController;

	@MockBean
	LibraryRepository libraryRepository;

	@MockBean
	LibraryService libraryService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	public void checkBuildIDLogic(){
		LibraryService libraryService = new LibraryService();
		String id = libraryService.buildId("ZMAN", 2005);

		assertEquals(id, "OLDZMAN2005");
	}

	@Test
	public void addBookTestExists() throws Exception{
		
		Library library = buildLibraryForTestPurposes();
		when(libraryService.buildId(library.getIsbn(), library.getAisle())).thenReturn(library.getId());	// Mock Line 42 LibraryController
		when(libraryService.checkBookAlreadyExists(library.getId())).thenReturn(false); 		// Mock Line 45 LibraryController
		when(libraryRepository.save(any())).thenReturn(library);
		ResponseEntity<?> responseEntity = libraryController.addBookImplementation(buildLibraryForTestPurposes());
		System.out.println(responseEntity.getStatusCode());
		assertEquals(responseEntity.getStatusCode(),HttpStatus.CREATED);

		AddResponse addResponse = (AddResponse) responseEntity.getBody();
		try {
			assertEquals(library.getId(), addResponse.getId());
			assertEquals("Book was added successfully", addResponse.getMsg());
		} catch (Exception e) {
			throw new Exception("The response was null");
		}
	}

	@Test
	public void addBookTestDoesNotExists(){
		
		Library library = buildLibraryForTestPurposes();
		when(libraryService.buildId(library.getIsbn(), library.getAisle())).thenReturn(library.getId());	// Mock Line 42 LibraryController
		when(libraryService.checkBookAlreadyExists(library.getId())).thenReturn(true); 		// Mock Line 45 LibraryController
		ResponseEntity<?> responseEntity = libraryController.addBookImplementation(buildLibraryForTestPurposes());
		System.out.println(responseEntity.getStatusCode());
		assertEquals(responseEntity.getStatusCode(),HttpStatus.ACCEPTED);

		AddResponse addResponse = (AddResponse) responseEntity.getBody();
		assertEquals(library.getId(), addResponse.getId());
		assertEquals("Book already exists", addResponse.getMsg());

	}

	@Test
	public void addBookExistingControllerTest() throws Exception{
				
		Library library = buildLibraryForTestPurposes();
		ObjectMapper map = new ObjectMapper();
		String jsonString = map.writeValueAsString(library);
//		var TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
//		var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
//		var csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
		

		when(libraryService.buildId(library.getIsbn(), library.getAisle())).thenReturn(library.getId());	// Mock Line 42 LibraryController
		when(libraryService.checkBookAlreadyExists(library.getId())).thenReturn(true); 		// Mock Line 45 LibraryController
			
		this.mockMvc.perform(post("/addBook")
//		.sessionAttr(TOKEN_ATTR_NAME, csrfToken)
//		.param(csrfToken.getParameterName(), csrfToken.getToken())
		.contentType(MediaType.APPLICATION_JSON)
		.content(jsonString))
		.andDo(print())
		.andExpect(jsonPath("$.id").value(library.getId()))
		.andExpect(status().isAccepted());

	}

	@Test
	public void addBookNotExistingControllerTest() throws Exception{
				
		Library library = buildLibraryForTestPurposes();
		ObjectMapper map = new ObjectMapper();
		String jsonString = map.writeValueAsString(library);
		

		when(libraryService.buildId(library.getIsbn(), library.getAisle())).thenReturn(library.getId());	// Mock Line 42 LibraryController
		when(libraryService.checkBookAlreadyExists(library.getId())).thenReturn(false); 		// Mock Line 45 LibraryController
		when(libraryRepository.save(any())).thenReturn(library);
		
		this.mockMvc.perform(post("/addBook").contentType(MediaType.APPLICATION_JSON)
		.content(jsonString))
		.andDo(print())
		.andExpect(jsonPath("$.id").value(library.getId()))
		.andExpect(status().isCreated());

	}

	public Library buildLibraryForTestPurposes(){
		Library library = new Library();
		library.setAisle(1992);
		library.setAuthor("Juan Camilo Galvis Cuéllar");
		library.setBook_name("La historia del bebé más hermoso");
		library.setIsbn("Samus");
		library.setId("Samus1992");
		return library;
	}

}
