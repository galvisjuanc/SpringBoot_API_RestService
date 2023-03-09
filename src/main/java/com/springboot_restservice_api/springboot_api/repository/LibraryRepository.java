package com.springboot_restservice_api.springboot_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot_restservice_api.springboot_api.entity.Library;

@Repository
public interface LibraryRepository extends JpaRepository <Library, String>, LibraryRepositoryCustom{
    
}
