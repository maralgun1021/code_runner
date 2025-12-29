package com.sprintboot.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sprintboot.admin.model.AdminExam;

@RepositoryRestResource
public interface AdminExamRepo extends JpaRepository<AdminExam, Long>{
    
}
