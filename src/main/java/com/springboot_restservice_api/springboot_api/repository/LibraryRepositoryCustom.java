package com.springboot_restservice_api.springboot_api.repository;

import java.util.List;

import com.springboot_restservice_api.springboot_api.entity.Library;

public interface LibraryRepositoryCustom {
    List<Library> findAllByAuthor (String authorname);
}
