package com.sprintboot.exam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exam")
public class SubmissionController {

    @GetMapping("/submit")
    public String submit() {
        return "EXAM API WORKING";
    }
}
