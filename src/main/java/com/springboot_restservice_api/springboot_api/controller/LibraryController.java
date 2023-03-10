package com.springboot_restservice_api.springboot_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/getBooks/{id}")
    public Library getBookById(@PathVariable(value="id") String id) {
        try{
            Library libraryFound = libraryRepository.findById(id).get();
            return libraryFound;
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getBooks/author")
    public List<Library> getBookByAuthorName(@RequestParam(value="authorname") String authorname) {
        return libraryRepository.findAllByAuthor(authorname);

    }

    @GetMapping("/getBooks")
    public List<Library> getAllBooks(){
        return libraryRepository.findAll();
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Library> updateBookById(@PathVariable(value="id") String id, @RequestBody Library library) {
        
        Library libraryFoundToBeUpdated = libraryRepository.findById(id).get();

        libraryFoundToBeUpdated.setAisle(library.getAisle());
        libraryFoundToBeUpdated.setAuthor(library.getAuthor());
        libraryFoundToBeUpdated.setBook_name(library.getBook_name());

        libraryRepository.save(libraryFoundToBeUpdated);

        return new ResponseEntity<Library>(libraryFoundToBeUpdated,HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBookById(@RequestBody Library library) {

        Library libraryToBeDeleted = libraryRepository.findById(library.getId()).get();
        libraryRepository.delete(libraryToBeDeleted);
        return new ResponseEntity<>("Book was deleted successfully", HttpStatus.CREATED);
    }

}
