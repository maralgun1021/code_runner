package com.sprintboot.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // No-args constructor
@AllArgsConstructor // All-args constructor
@Builder
public class TestResult {
    private int testNumber;
    private boolean passed;
    private Object expected; // can be Integer, String, List<Integer>, etc
    private Object actual;
    private String message;

}
