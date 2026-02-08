package com.sprintboot.exam.service;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.tools.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.sprintboot.exam.entity.TestResult;
import com.sprintboot.exam.util.GenConfig;

public class JavaTestRunner {

    public static void main(String[] args) {
        String basePath = "C:/Users/maral/OneDrive/Documents/visual_program/problem_solving/";

        try {
            List<TestResult> results = runJavaTests(
                    basePath,
                    "submissions/user456_Solution.java",
                    "testcases/testcases.txt");

            results.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Compile and run Java code against test cases
     */
    public static List<TestResult> runJavaTests(
            String basePath,
            String sourceFileRelativePath,
            String testcaseRelativePath) throws Exception {

        List<TestResult> results = new ArrayList<>();

        String userFile = basePath + sourceFileRelativePath;
        File sourceFile = new File(userFile);

        String className = sourceFile.getName().replace(".java", "");
        File classesDir = sourceFile.getParentFile();

        // 1. Compile
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            results.add(TestResult.builder()
                    .testNumber(0)
                    .passed(false)
                    .message("Java Compiler not found (JDK required)")
                    .build());
            return results;
        }

        int compileResult = compiler.run(null, null, null, sourceFile.getPath());
        if (compileResult != 0) {
            results.add(TestResult.builder()
                    .testNumber(0)
                    .passed(false)
                    .message("Compilation failed")
                    .build());
            return results;
        }

        // 2. Load class
        URLClassLoader classLoader = URLClassLoader.newInstance(
                new URL[] { classesDir.toURI().toURL() });
        Class<?> cls = Class.forName(className, true, classLoader);
        Object sol = cls.getDeclaredConstructor().newInstance();

        // 3. Read testcases
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader(basePath + testcaseRelativePath))) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#"))
                    continue;
                lines.add(line);
            }
        }

        // 4. Run tests
        int testNum = 1;
        for (String line : lines) {
            String[] parts = line.split(";");
            String inputStr = parts[0];
            String expectedStr = parts[1].trim();

            Object expected;
            Object actual = null;

            // For now, try Integer. Later you can extend to String, List, etc
            try {
                expected = Integer.parseInt(expectedStr);
            } catch (NumberFormatException e) {
                expected = expectedStr; // fallback as String
            }

            // Parse input as int[] (assuming sum(int[] arr) method)
            int[] input;
            if (inputStr.isEmpty()) {
                input = new int[0];
            } else {
                String[] nums = inputStr.split(",");
                input = new int[nums.length];
                for (int i = 0; i < nums.length; i++) {
                    input[i] = Integer.parseInt(nums[i].trim());
                }
            }

            boolean passed = false;
            String message;

            try {
                // invoke sum(int[] arr)
                actual = cls.getMethod("sum", int[].class)
                        .invoke(sol, (Object) input);

                if (expected instanceof Integer && actual instanceof Integer) {
                    passed = expected.equals(actual);
                } else {
                    // fallback string comparison
                    passed = expected.toString().equals(actual.toString());
                }

                message = passed ? "PASSED"
                        : "FAILED (expected " + expected + ", got " + actual + ")";

            } catch (Exception e) {
                message = "Runtime error: " + e.getMessage();
            }

            results.add(TestResult.builder()
                    .testNumber(testNum)
                    .passed(passed)
                    .expected(expected)
                    .actual(actual)
                    .message(message)
                    .build());

            testNum++;
        }

        return results;
    }
}
