package com.springboot_restservice_api.springboot_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot_restservice_api.springboot_api.entity.AddResponse;
import com.springboot_restservice_api.springboot_api.entity.Library;
import com.springboot_restservice_api.springboot_api.repository.LibraryRepository;
import com.springboot_restservice_api.springboot_api.service.LibraryService;

@RestController
public class LibraryController {

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    LibraryService libraryService;

    @PostMapping("/addBook")
    public ResponseEntity<AddResponse> addBookImplementation(@RequestBody Library library){
        
        AddResponse addResponse = new AddResponse();
        HttpHeaders httpHeaders = new HttpHeaders();
        String idBook = libraryService.buildId(library.getIsbn(),library.getAisle());
        libraryService.checkBookAlreadyExists(idBook);

        if (!libraryService.checkBookAlreadyExists(idBook)){    
            library.setId(idBook);
            libraryRepository.save(library);
    
            addResponse.setMsg("Book was added successfully");
            addResponse.setId(idBook);
    
            httpHeaders.add("unique", idBook);
            
            return new ResponseEntity<AddResponse>(addResponse, httpHeaders, HttpStatus.CREATED);
        }
        else {
            addResponse.setMsg("Book already exists");
            addResponse.setId(idBook);
            return new ResponseEntity<AddResponse>(addResponse, HttpStatus.ACCEPTED);
        }
    }
    
}
