package com.sprintboot.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // No-args constructor
@AllArgsConstructor // All-args constructor
public class CodeSubmission {
    private String code; // actual code
    private String language; // "java", "cpp", "python"

}
