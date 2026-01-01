package com.sprintboot.exam.service;

import java.io.*;
import java.util.*;

public class CppTestRunner {
    public static void main(String[] args) throws Exception {
        String mainPath = "C:/Users/maral/OneDrive/Documents/visual_program/store/store/exam/src/main/resources/";
        String userFile = mainPath + "submissions/user456_Solution.cpp"; // or .c for C

        String outputBinary = mainPath + "submissions/user456_Solution.exe"; // Windows
        // Linux: just "user456_Solution"

        // 1. Compile user code
        String compileCmd = "g++ \"" + userFile + "\" -o \"" + outputBinary + "\""; 
        Process compileProcess = Runtime.getRuntime().exec(compileCmd);
        int compileResult = compileProcess.waitFor();

        // Print compilation errors if any
        try (BufferedReader br = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.err.println(line);
            }
        }

        if (compileResult != 0) {
            System.out.println("Compilation failed!");
            return;
        }

        // 2. Read testcases.txt
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(mainPath + "testcases/testcases.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                lines.add(line);
            }
        }

        int testNum = 1;
        for (String line : lines) {
            String[] parts = line.split(";");
            String inputStr = parts[0];       // e.g., "1,2,3"
            int expected = Integer.parseInt(parts[1]);

            // Prepare input for the program
            String[] inputs = inputStr.isEmpty() ? new String[0] : inputStr.split(",");

            // 3. Run the compiled binary
            ProcessBuilder pb = new ProcessBuilder(outputBinary);
            Process runProcess = pb.start();

            // Send input via stdin
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                for (String s : inputs) {
                    bw.write(s);
                    bw.newLine();
                }
            }

            // Read output from stdout
            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            String outputLine = reader.readLine(); // assume program prints one integer result
            runProcess.waitFor();

            int output = Integer.parseInt(outputLine.trim());

            if (output == expected) {
                System.out.println("Test " + testNum + " Passed!");
            } else {
                System.out.println("Test " + testNum + " Failed! Expected " + expected + " but got " + output);
            }

            testNum++;
        }
    }
}
