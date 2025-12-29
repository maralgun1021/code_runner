package com.sprintboot.admin.controller;

import org.springframework.web.bind.annotation.*;

import com.sprintboot.admin.model.Post;
import com.sprintboot.admin.model.User;
import com.sprintboot.admin.repository.PostRepository;
import com.sprintboot.admin.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users12") 
public class UserController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PostRepository postRepo;


    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    // ---------------- Create ----------------
    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = repo.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // ---------------- Read all ----------------
    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    // ---------------- Read single user ----------------
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = repo.findById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // ---------------- Update ----------------
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return repo.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setAge(updatedUser.getAge());
                    return ResponseEntity.ok(repo.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ---------------- Delete ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return repo.findById(id)
                .map(user -> {
                    repo.delete(user);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/posts")
    public Post addPostToUser(@PathVariable Long userId, @RequestBody Post post) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.setUser(user);
        return postRepo.save(post);
    }


    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePostFromUser(@PathVariable Long id) {
        return postRepo.findById(id).map(
            post -> {
                postRepo.delete(post);
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        ).orElse(ResponseEntity.notFound().build());


    }

}
