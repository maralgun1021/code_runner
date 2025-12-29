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

import com.sprintboot.admin.model.AdminExamResult;
import com.sprintboot.admin.repository.AdminExamResultRepo;

@RestController
@RequestMapping("/examResult") 
public class AdminExamResultController {
    
    @Autowired
    private AdminExamResultRepo repo;

    @GetMapping
    public List<AdminExamResult> getAll() {
        return repo.findAll();
    }

        // GET problem by id
    @GetMapping("/{id}")
    public ResponseEntity<AdminExamResult> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE problem
    @PostMapping
    public AdminExamResult create(@RequestBody AdminExamResult exam) {
        return repo.save(exam);
    }

    // UPDATE problem
    @PutMapping("/{id}")
    public ResponseEntity<AdminExamResult> update(
            @PathVariable Long id,
            @RequestBody AdminExamResult updatedExamResult) {

        return repo.findById(id)
                .map(examResult -> {
                    examResult.setTotalResult(updatedExamResult.getTotalResult());

                    repo.save(examResult);
                    return ResponseEntity.ok(examResult);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE problem
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
