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

import com.sprintboot.admin.model.AdminProblemResult;
import com.sprintboot.admin.repository.AdminProblemResultRepo;

@RestController
@RequestMapping("/problemResult") 
public class AdminProblemResultController {
    @Autowired
    private AdminProblemResultRepo repo;

    @GetMapping
    public List<AdminProblemResult> getAll() {
        return repo.findAll();
    }

    // GET problem by id
    @GetMapping("/{resultId}/{problemId}")
    public ResponseEntity<AdminProblemResult> getById(
        @PathVariable Long resultId,
        @PathVariable Long problemId) {

        return repo.findByIdResultIdAndIdProblemId(resultId, problemId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

    // CREATE problem
    @PostMapping
    public AdminProblemResult create(@RequestBody AdminProblemResult exam) {
        return repo.save(exam);
    }

    // UPDATE problem
    @PutMapping("/{id}")
    public ResponseEntity<AdminProblemResult> update(
        @PathVariable Long resultId,
        @PathVariable Long problemId,
            @RequestBody AdminProblemResult updatedProblem) {

        return repo.findByIdResultIdAndIdProblemId(resultId, problemId)
                .map(problemResult -> {
                    problemResult.setPoint(updatedProblem.getPoint());

                    repo.save(problemResult);
                    return ResponseEntity.ok(problemResult);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE problem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable Long resultId,
        @PathVariable Long problemId
    ) {
        return repo.findByIdResultIdAndIdProblemId(resultId, problemId)
                .map(problemResult -> {
                    repo.delete(problemResult);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }



}
