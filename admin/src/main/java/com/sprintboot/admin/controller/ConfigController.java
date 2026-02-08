package com.sprintboot.admin.controller;

import com.sprintboot.admin.model.Config;
import com.sprintboot.admin.repository.ConfigRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/configs")
public class ConfigController {

    @Autowired
    private ConfigRepository configRepository;

    // Get all configs
    @GetMapping
    public ResponseEntity<List<Config>> getAllConfigs() {
        List<Config> configs = configRepository.findAll();
        return ResponseEntity.ok(configs);
    }

    // Get a single config by ID
    @GetMapping("/{id}")
    public ResponseEntity<Config> getConfigById(@PathVariable Long id) {
        return configRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get a single config by key
    @GetMapping("/key/{configKey}")
    public ResponseEntity<Config> getConfigByKey(@PathVariable String configKey) {
        return configRepository.findByConfigKey(configKey)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new config
    @PostMapping
    public ResponseEntity<Config> createConfig(@RequestBody Config config) {
        if (config.getConfigKey() == null || config.getConfigKey().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // Prevent duplicate keys
        if (configRepository.existsByConfigKey(config.getConfigKey())) {
            return ResponseEntity.status(409).build(); // Conflict
        }

        Config saved = configRepository.save(config);
        return ResponseEntity.ok(saved);
    }

    // Update an existing config
    @PutMapping("/{id}")
    public ResponseEntity<Config> updateConfig(@PathVariable Long id, @RequestBody Config updatedConfig) {
        return configRepository.findById(id)
                .map(config -> {
                    config.setConfigKey(updatedConfig.getConfigKey());
                    config.setConfigValue(updatedConfig.getConfigValue());
                    config.setDescription(updatedConfig.getDescription());
                    Config saved = configRepository.save(config);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a config
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteConfig(@PathVariable Long id) {
        return configRepository.findById(id)
                .map(config -> {
                    configRepository.delete(config);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
