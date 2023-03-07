package com.springboot_restservice_api.springboot_api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springboot_restservice_api.springboot_api.entity.Library;
import com.springboot_restservice_api.springboot_api.repository.LibraryRepository;

@SpringBootApplication
public class SpringbootApiApplication implements CommandLineRunner{

@Autowired
LibraryRepository libraryRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApiApplication.class, args);
	}

	@Override
	public void run(String[] args){
		Library library = libraryRepository.findById("Weezer2005").get();
		System.out.println("Name of the Author --> " + library.getAuthor());
		
		Library library2 = new Library();
		library2.setBookName("Programming with Java and Springboot");
		library2.setId("SwitchOled2020");
		library2.setIsbn("SwitchOled");
		library2.setAisle(2020);
		library2.setAuthor("Juan Galvis");

		libraryRepository.save(library2);

		List<Library> allLibraryRecords = libraryRepository.findAll();

		for (Library item : allLibraryRecords) {
			System.out.println(item.getAuthor());
		}

		libraryRepository.delete(library2);
	}

}
