package com.sprintboot.exam.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprintboot.exam.entity.CodeSubmission;
import com.sprintboot.exam.util.GenConfig;
import com.sprintboot.exam.util.Language;

import common.util.FileUtils;

@RestController
@RequestMapping("/api/submit")
public class SubmissionController {

    @Autowired
    private GenConfig genConfig;

    public SubmissionController(GenConfig genConfig) {
        this.genConfig = genConfig;
    }

    @GetMapping("/submit")
    public String submit() {
        String osType = genConfig.getConfigValue("BASE_PATH");
        return osType;
    }

    @PostMapping("/{problemId}")
    public ResponseEntity<String> addSolution(@PathVariable Long problemId,
            @RequestBody CodeSubmission submission) {

        String basePath = genConfig.getConfigValue("BASE_PATH");
        try {
            String path = basePath + "submissions" + File.separator +
                    +problemId + File.separator;

            Language lang = Language.valueOf(submission.getLanguage().toUpperCase());

            String fileName = problemId + lang.fileExt;

            boolean saved = FileUtils.saveFile(path, fileName, submission.getCode());

            if (!saved) {
                return ResponseEntity.status(400)
                        .body("File already exists and could not be overwritten");
            }

            return ResponseEntity.ok("Code saved successfully at: " + basePath + fileName);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving code: " + e.getMessage());
        }

    }

    @PostMapping("/{userId}/{problemId}")
    public ResponseEntity<String> addSolutionByUser(@PathVariable("userId") Long userId,
            @PathVariable("problemId") Long problemId,
            @RequestBody CodeSubmission submission) {

        String basePath = genConfig.getConfigValue("BASE_PATH");
        try {
            String path = basePath + "submissions" + File.separator + userId + File.separator
                    + problemId + File.separator;

            Language lang = Language.valueOf(submission.getLanguage().toUpperCase());

            String fileName = problemId + lang.fileExt;

            boolean saved = FileUtils.saveFile(path, fileName, submission.getCode());

            if (!saved) {
                return ResponseEntity.status(400)
                        .body("File already exists and could not be overwritten");
            }

            return ResponseEntity.ok("Code saved successfully at: " + basePath + fileName);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving code: " + e.getMessage());
        }

    }

}
