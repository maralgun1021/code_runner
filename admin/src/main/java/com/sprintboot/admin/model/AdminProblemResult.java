package com.sprintboot.admin.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "admin_problem_result")
public class AdminProblemResult {

    @EmbeddedId
    private AdminProblemResultId id;

    @ManyToOne
    @JoinColumn(name = "result")
    @JsonManagedReference
    private AdminExamResult result;

    @ManyToOne
    @JoinColumn(name = "problem")
    @JsonManagedReference
    private AdminProblem problem;

    private Long point;    
}
