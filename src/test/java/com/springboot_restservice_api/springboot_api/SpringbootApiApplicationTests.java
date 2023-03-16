package com.springboot_restservice_api.springboot_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.springboot_restservice_api.springboot_api.controller.LibraryController;
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
	public void addBookTest(){
		ResponseEntity responseEntity = libraryController.addBookImplementation(buildLibraryForTestPurposes());
		System.out.println(responseEntity.getStatusCode());

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
