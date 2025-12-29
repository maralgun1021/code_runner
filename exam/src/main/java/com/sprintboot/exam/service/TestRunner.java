package com.sprintboot.exam.service;

import java.io.*;
import java.util.*;
import javax.tools.*;
import java.net.*;

public class TestRunner {
    public static void main(String[] args) throws Exception {
        String mainPath = "C:/Users/maral/OneDrive/Documents/visual_program/store/store/exam/src/main/resources/";
        String userFile = mainPath + "submissions/user456_Solution.java";

        String className = "user456_Solution";

        // 1. Compile user solution
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, null, userFile);
        if (result != 0) {
            System.out.println("Compilation failed!");
            return;
        }

        // 2. Load compiled class
        File classesDir = new File(mainPath + "submissions/");
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classesDir.toURI().toURL()});
        Class<?> cls = Class.forName(className, true, classLoader);

        // 3. Create instance
        Object sol = cls.getDeclaredConstructor().newInstance();

        // 4. Read testcases.txt
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(mainPath + "testcases/testcases.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int testNum = 1;
        for (String line : lines) {
            String[] parts = line.split(";");
            String inputStr = parts[0];
            int expected = Integer.parseInt(parts[1]);

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

            // 5. Invoke sum method dynamically
            int output = (int) cls.getMethod("sum", int[].class).invoke(sol, (Object) input);

            if (output == expected) {
                System.out.println("Test " + testNum + " Passed!");
            } else {
                System.out.println("Test " + testNum + " Failed! Expected " + expected + " but got " + output);
            }
            testNum++;
        }
    }
}
