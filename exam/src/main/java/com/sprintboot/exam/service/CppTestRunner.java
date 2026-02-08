package com.sprintboot.exam.service;

import java.io.*;
import java.util.*;

public class CppTestRunner {

    public static void main(String[] args) {
        String basePath = "C:/Users/maral/OneDrive/Documents/visual_program/problem_solving/";

        try {
            List<String> results = runCppTests(
                    basePath,
                    "submissions/user456_Solution.cpp",
                    "testcases/testcases.txt");

            for (String r : results) {
                System.out.println(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Compile and run C/C++ code against test cases
     */
    public static List<String> runCppTests(
            String basePath,
            String sourceFileRelativePath,
            String testcaseRelativePath) throws Exception {

        List<String> results = new ArrayList<>();

        String userFile = basePath + sourceFileRelativePath;
        String outputBinary = userFile.replace(".cpp", ".exe"); // Windows

        // 1. Compile
        String compileCmd = "g++ \"" + userFile + "\" -o \"" + outputBinary + "\"";
        Process compileProcess = Runtime.getRuntime().exec(compileCmd);
        int compileResult = compileProcess.waitFor();

        // Capture compilation errors
        StringBuilder compileErrors = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(compileProcess.getErrorStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                compileErrors.append(line).append("\n");
            }
        }

        if (compileResult != 0) {
            results.add("Compilation failed:");
            results.add(compileErrors.toString());
            return results;
        }

        // 2. Read testcases
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

        // 3. Run tests
        int testNum = 1;
        for (String line : lines) {
            String[] parts = line.split(";");
            String inputStr = parts[0];
            int expected = Integer.parseInt(parts[1]);

            String[] inputs = inputStr.isEmpty()
                    ? new String[0]
                    : inputStr.split(",");

            ProcessBuilder pb = new ProcessBuilder(outputBinary);
            Process runProcess = pb.start();

            // 🔴 FIX: SEND n FIRST, THEN ELEMENTS
            try (BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(runProcess.getOutputStream()))) {

                // send array size
                bw.write(String.valueOf(inputs.length));
                bw.newLine();

                // send array elements
                for (String s : inputs) {
                    bw.write(s.trim());
                    bw.newLine();
                }
            }

            // Read output
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(runProcess.getInputStream()));
            String outputLine = reader.readLine();
            runProcess.waitFor();

            int output = Integer.parseInt(outputLine.trim());

            if (output == expected) {
                results.add("Test " + testNum + " PASSED");
            } else {
                results.add("Test " + testNum + " FAILED (expected "
                        + expected + ", got " + output + ")");
            }

            testNum++;
        }

        return results;
    }
}
