package com.sprintboot.exam.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenConfig {

    private final JdbcTemplate jdbcTemplate;

    public GenConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getConfigValue(String key) {
        String sql = "SELECT config_value FROM config WHERE config_key = ?";

        List<String> results = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getString("config_value"),
                key);

        return results.isEmpty() ? null : results.get(0);
    }
}
