package com.sprintboot.admin.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sprintboot.exam.entity.TestResult;
import com.sprintboot.exam.service.JavaTestRunner;
import com.sprintboot.exam.util.GenConfig;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/testcases")
public class TestModuleController {

    @Autowired
    private GenConfig genConfig;

    @GetMapping("/test")
    public String test() {
        try {
            String basePath = genConfig.getConfigValue("BASE_PATH");
            String runningCode = "submissions/user456_Solution.cpp";
            String runningTestcase = "testcases/testcases.txt";

            List<TestResult> results = JavaTestRunner.runJavaTests(basePath, runningCode, runningTestcase);
            return String.valueOf(results.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @PostMapping("text/{problemId}")
    public ResponseEntity<String> insertTestcaseByText(@PathVariable Long problemId, @RequestBody String testcase) {

        if (testcase == null || testcase.isBlank()) {
            return ResponseEntity.badRequest().body("Testcase cannot be empty");
        }

        try {
            String basePath = genConfig.getConfigValue("BASE_PATH") + File.separator + "testcases";
            Path problemDir = Paths.get(basePath, problemId.toString());
            Files.createDirectories(problemDir);

            Path testcaseFile = problemDir.resolve("testcase.txt");

            Files.writeString(
                    testcaseFile,
                    testcase,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            return ResponseEntity.ok("Testcase saved at: " + testcaseFile);

        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to save testcase: " + e.getMessage());
        }
    }

    @PostMapping("file/{problemId}")
    public ResponseEntity<String> insertTestcaseByFile(
            @PathVariable Long problemId,
            @RequestParam MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty or missing");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".txt")) {
            return ResponseEntity.badRequest().body("Only .txt files are allowed");
        }

        try {
            String basePath = genConfig.getConfigValue("BASE_PATH") + File.separator + "testcases";
            Path problemDir = Paths.get(basePath, problemId.toString());
            Files.createDirectories(problemDir);

            Path testcaseFile = problemDir.resolve("testcase.txt");

            file.transferTo(testcaseFile.toFile());

            return ResponseEntity.ok("Testcase saved at: " + testcaseFile);

        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to save testcase: " + e.getMessage());
        }
    }

    @PutMapping("text/{problemId}")
    public ResponseEntity<String> updateTestcaseByText(@PathVariable Long problemId, @RequestBody String testcase) {

        if (testcase == null || testcase.isBlank()) {
            return ResponseEntity.badRequest().body("Testcase cannot be empty");
        }

        try {
            String basePath = genConfig.getConfigValue("BASE_PATH") + File.separator + "testcases";
            Path problemDir = Paths.get(basePath, problemId.toString());
            Files.createDirectories(problemDir);

            Path testcaseFile = problemDir.resolve("testcase.txt");

            if (Files.exists(testcaseFile)) {
                String timestamp = java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

                Path backupFile = problemDir.resolve(
                        "testcase_" + timestamp + ".txt");

                Files.move(testcaseFile, backupFile);
            }

            Files.writeString(
                    testcaseFile,
                    testcase,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            return ResponseEntity.ok("Testcase saved at: " + testcaseFile);

        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to save testcase: " + e.getMessage());
        }
    }

    @PutMapping("file/{problemId}")
    public ResponseEntity<String> updateTestcaseByFile(
            @PathVariable Long problemId,
            @RequestParam MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty or missing");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".txt")) {
            return ResponseEntity.badRequest().body("Only .txt files are allowed");
        }

        try {
            String basePath = genConfig.getConfigValue("BASE_PATH") + File.separator + "testcases";
            Path problemDir = Paths.get(basePath, problemId.toString());
            Files.createDirectories(problemDir);

            Path testcaseFile = problemDir.resolve("testcase.txt");

            if (Files.exists(testcaseFile)) {
                String timestamp = java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

                Path backupFile = problemDir.resolve(
                        "testcase_" + timestamp + ".txt");

                Files.move(testcaseFile, backupFile);
            }

            file.transferTo(testcaseFile.toFile());

            return ResponseEntity.ok("Testcase saved at: " + testcaseFile);

        } catch (IOException e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to save testcase: " + e.getMessage());
        }
    }

}
