package com.sprintboot.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sprintboot.admin.model.UserProfile;
import com.sprintboot.admin.repository.UserProfileRepository;
import com.sprintboot.admin.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class UserProfileController {

    @Autowired
    private UserProfileRepository repo;

    @Autowired
    private UserRepository userRepository;

    // CREATE profile
    @PostMapping("/{userId}")
    public ResponseEntity<UserProfile> addProfileToUser(@PathVariable Long userId,
        @RequestBody UserProfile profileData) {

        return userRepository.findById(userId)
            .map(user -> {
                UserProfile profile = new UserProfile();
                profile.setAddress(profileData.getAddress());

                UserProfile savedProfile = repo.save(profile);

                // link to user
                user.setProfile(savedProfile);
                userRepository.save(user);

                return ResponseEntity.ok(savedProfile);
            })
            .orElse(ResponseEntity.notFound().build());

    }

    // GET all profiles
    @GetMapping
    public List<UserProfile> getAll() {
        return repo.findAll();
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> get(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> update(
            @PathVariable Long id,
            @RequestBody UserProfile profileData) {

        return repo.findById(id).map(profile -> {
            profile.setAddress(profileData.getAddress());
            return ResponseEntity.ok(repo.save(profile));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
