package com.springboot_restservice_api.springboot_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="info_books")
public class Library {

    @Column(name="book_name")
    private String book_name;
    
    @Id
    @Column(name="id")
    private String id;

    @Column(name="isbn")
    private String isbn;

    @Column(name="aisle")
    private int aisle;

    @Column(name="author")
    private String author;

    public String getBook_name(){return book_name;}

    public String getId() {return id;}

    public String getIsbn() {return isbn;}

    public int getAisle() {return aisle;}

    public String getAuthor() {return author;}

    public void setBook_name(String book_name) {this.book_name = book_name;}

    public void setId(String id) {this.id = id;}

    public void setIsbn(String isbn) {this.isbn = isbn;}

    public void setAisle(int aisle) {this.aisle = aisle;}

    public void setAuthor(String author) {this.author = author;}

}
