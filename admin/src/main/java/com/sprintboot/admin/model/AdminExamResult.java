package com.sprintboot.admin.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "admin_exam_result")
public class AdminExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private AdminUser user;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    @JsonManagedReference
    private AdminExam exam;

    private Long totalResult;

    @OneToMany(mappedBy = "result")
    @JsonBackReference
    private Set<AdminProblemResult> problemResults;

}
