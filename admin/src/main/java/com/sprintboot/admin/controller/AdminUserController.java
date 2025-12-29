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

import com.sprintboot.admin.model.AdminUser;
import com.sprintboot.admin.repository.AdminUserRepo;

@RestController
@RequestMapping("/users") 
public class AdminUserController {

    @Autowired
    private AdminUserRepo repo;

    @GetMapping
    public List<AdminUser> getAll() {
        return repo.findAll();
    }

    // GET user by id
    @GetMapping("/{id}")
    public ResponseEntity<AdminUser> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE user
    @PostMapping
    public AdminUser create(@RequestBody AdminUser exam) {
        return repo.save(exam);
    }

    // UPDATE user
    @PutMapping("/{id}")
    public ResponseEntity<AdminUser> update(
            @PathVariable Long id,
            @RequestBody AdminUser updatedUser) {

        return repo.findById(id)
                .map(user -> {
                    user.setAge(updatedUser.getAge());
                    user.setEmail(updatedUser.getEmail());
                    user.setName(updatedUser.getName());
                    user.setPhone(updatedUser.getPhone());

                    repo.save(user);
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE user
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
