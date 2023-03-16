package com.springboot_restservice_api.springboot_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springboot_restservice_api.springboot_api.controller.LibraryController;
import com.springboot_restservice_api.springboot_api.entity.AddResponse;
import com.springboot_restservice_api.springboot_api.entity.Library;
import com.springboot_restservice_api.springboot_api.repository.LibraryRepository;
import com.springboot_restservice_api.springboot_api.service.LibraryService;

@SpringBootTest
class SpringbootApiApplicationTests {

	@Autowired
	LibraryController libraryController;

	@MockBean
	LibraryRepository libraryRepository;

	@MockBean
	LibraryService libraryService;

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
	public void addBookTestExists(){
		
		Library library = buildLibraryForTestPurposes();
		when(libraryService.buildId(library.getIsbn(), library.getAisle())).thenReturn(library.getId());	// Mock Line 42 LibraryController
		when(libraryService.checkBookAlreadyExists(library.getId())).thenReturn(false); 		// Mock Line 45 LibraryController
		ResponseEntity<?> responseEntity = libraryController.addBookImplementation(buildLibraryForTestPurposes());
		System.out.println(responseEntity.getStatusCode());
		assertEquals(responseEntity.getStatusCode(),HttpStatus.CREATED);

		AddResponse addResponse = (AddResponse) responseEntity.getBody();
		assertEquals(library.getId(), addResponse.getId());
		assertEquals("Book was added successfully", addResponse.getMsg());

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
