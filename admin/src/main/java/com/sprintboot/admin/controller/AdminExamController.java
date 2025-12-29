package com.sprintboot.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprintboot.admin.model.AdminExam;
import com.sprintboot.admin.repository.AdminExamRepo;


@RestController
@RequestMapping("/exams") 
public class AdminExamController {

    @Autowired
    private AdminExamRepo repo;

    @GetMapping
    public List<AdminExam> getAll() {
        return repo.findAll();
    }

    // GET exam by id
    @GetMapping("/{id}")
    public ResponseEntity<AdminExam> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE exam
    @PostMapping
    public AdminExam create(@RequestBody AdminExam exam) {
        return repo.save(exam);
    }

    // UPDATE exam
    @PutMapping("/{id}")
    public ResponseEntity<AdminExam> update(
            @PathVariable Long id,
            @RequestBody AdminExam updatedExam) {

        return repo.findById(id)
                .map(exam -> {
                    exam.setTotal(updatedExam.getTotal());

                    repo.save(exam);
                    return ResponseEntity.ok(exam);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE exam
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return repo.findById(id)
                .map(exam -> {
                    repo.delete(exam);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

