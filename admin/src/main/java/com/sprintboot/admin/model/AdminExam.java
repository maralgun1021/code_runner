package com.sprintboot.admin.model;

import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Data
@Entity
@Table(name = "admin_exam")
public class AdminExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long total;

    @ManyToMany
    @JoinTable(
        name = "admin_exam_problem",  // junction table
        joinColumns = @JoinColumn(name = "exam_id"),
        inverseJoinColumns = @JoinColumn(name = "problem_id")
    )

    private Set<AdminProblem> problems;

    @OneToMany(mappedBy = "exam")
    @JsonManagedReference
    private List<AdminExamResult> results;




}
