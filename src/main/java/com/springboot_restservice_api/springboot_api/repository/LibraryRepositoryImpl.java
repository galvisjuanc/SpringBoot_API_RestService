package com.springboot_restservice_api.springboot_api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.springboot_restservice_api.springboot_api.entity.Library;

public class LibraryRepositoryImpl implements LibraryRepositoryCustom {

    @Autowired
    @Lazy
    LibraryRepository libraryRepository;

    @Override
    public List<Library> findAllByAuthor(String authorName) {
        
        List<Library> books = libraryRepository.findAll();
        List<Library> booksWithAuthor = new ArrayList<Library>();

        for(int i = 0; i < books.size(); i++) {
            if (books.get(i).getAuthor().equalsIgnoreCase(authorName)){
                System.out.println(books.get(i).getAuthor().equalsIgnoreCase(authorName));
                booksWithAuthor.add(books.get(i));
            }
        }

        /* for(Library item: books) {
         *      if(item.getAuthor().equalsIgnoreCase(authorName)) {
         *          booksWithAuthor.add(item);
         *      }
         * } */
        return booksWithAuthor;

    }
    
}
