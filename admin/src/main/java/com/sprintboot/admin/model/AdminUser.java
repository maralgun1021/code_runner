package com.sprintboot.admin.model;

import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "admin_user")
public class AdminUser {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private String name;
    private String phone;
    private Long age;
    private String email;
    private Long role;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<AdminExamResult> results;

    
}
