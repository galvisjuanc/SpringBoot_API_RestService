package com.springboot_restservice_api.springboot_api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot_restservice_api.springboot_api.entity.Library;
import com.springboot_restservice_api.springboot_api.repository.LibraryRepository;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    public String buildId(String isbn, int aisle) {
        if(isbn.startsWith("Z"))
            return "OLD"+isbn+aisle;
        else
            return isbn+aisle;
    }

    public boolean checkBookAlreadyExists(String id) {

        Optional<Library> libraryExists = libraryRepository.findById(id);
        return libraryExists.isPresent() ? true : false;
    }
}
