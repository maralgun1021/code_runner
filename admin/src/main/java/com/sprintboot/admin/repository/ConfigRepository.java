package com.sprintboot.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprintboot.admin.model.Config;

import java.util.Optional;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {

    Optional<Config> findByConfigKey(String configKey);

    boolean existsByConfigKey(String configKey);
}
