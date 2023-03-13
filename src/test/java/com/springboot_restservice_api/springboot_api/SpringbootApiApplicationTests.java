package com.springboot_restservice_api.springboot_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot_restservice_api.springboot_api.service.LibraryService;

@SpringBootTest
class SpringbootApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void checkBuildIDLogic(){
		LibraryService libraryService = new LibraryService();
		String id = libraryService.buildId("ZMAN", 2005);

		assertEquals(id, "OLDZMAN2005");
	}

}
