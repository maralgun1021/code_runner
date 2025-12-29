package com.sprintboot.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprintboot.admin.core.JudgeService;

@RestController
@RequestMapping("/judge")
public class JudgeController {

    private final JudgeService judgeService;

    public JudgeController(JudgeService judgeService) {
        this.judgeService = judgeService;
    }

    @PostMapping("/run")
    public ResponseEntity<String> run(@RequestBody CodeRequest request)
            throws Exception {

        return ResponseEntity.ok(judgeService.run(request.code()));
    }
}
