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

import com.sprintboot.admin.model.AdminProblem;
import com.sprintboot.admin.repository.AdminProblemRepo;

@RestController
@RequestMapping("/problems") 
public class AdminProblemController {
    
    @Autowired
    private AdminProblemRepo repo;

    @GetMapping
    public List<AdminProblem> getAll() {
        return repo.findAll();
    }

    // GET problem by id
    @GetMapping("/{id}")
    public ResponseEntity<AdminProblem> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE problem
    @PostMapping
    public AdminProblem create(@RequestBody AdminProblem exam) {
        return repo.save(exam);
    }

    // UPDATE problem
    @PutMapping("/{id}")
    public ResponseEntity<AdminProblem> update(
            @PathVariable Long id,
            @RequestBody AdminProblem updatedProblem) {

        return repo.findById(id)
                .map(problem -> {
                    problem.setPoint(updatedProblem.getPoint());
                    problem.setName(updatedProblem.getName());
                    problem.setTestCase(updatedProblem.getTestCase());

                    repo.save(problem);
                    return ResponseEntity.ok(problem);
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
