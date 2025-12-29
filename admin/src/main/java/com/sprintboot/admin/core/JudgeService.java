package com.sprintboot.admin.core;

import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service
public class JudgeService {

    public String run(String userCode) throws Exception {

        String fullSource = buildSource(userCode);

        Path workDir = Files.createTempDirectory("java-judge");
        Path sourceFile = workDir.resolve("Main.java");

        Files.writeString(sourceFile, fullSource);

        // Compile
        ProcessBuilder compile = new ProcessBuilder(
                "javac", "Main.java"
        );
        compile.directory(workDir.toFile());
        compile.redirectErrorStream(true);

        Process c = compile.start();
        String compileOutput = read(c);
        if (c.waitFor() != 0) {
            return "COMPILATION ERROR\n" + compileOutput;
        }

        // Run
        ProcessBuilder run = new ProcessBuilder(
                "java", "Main"
        );
        run.directory(workDir.toFile());
        run.redirectErrorStream(true);

        Process r = run.start();
        String output = read(r);
        r.waitFor();

        return output;
    }

    private String buildSource(String userCode) {
        return """
        public class Main {
            static class Solution {
        """ + userCode + """
            }

            public static void main(String[] args) {
                char[][] board = {
                    {'5','3','.','.','7','.','.','.','.'},
                    {'6','.','.','1','9','5','.','.','.'},
                    {'.','9','8','.','.','.','.','6','.'},
                    {'8','.','.','.','6','.','.','.','3'},
                    {'4','.','.','8','.','3','.','.','1'},
                    {'7','.','.','.','2','.','.','.','6'},
                    {'.','6','.','.','.','.','2','8','.'},
                    {'.','.','.','4','1','9','.','.','5'},
                    {'.','.','.','.','8','.','.','7','9'}
                };

                new Solution().solveSudoku(board);

                for (char[] row : board) {
                    for (char c : row) System.out.print(c);
                    System.out.println();
                }
            }
        }
        """;
    }

    private String read(Process p) throws Exception {
        return new String(p.getInputStream().readAllBytes());
    }
}
