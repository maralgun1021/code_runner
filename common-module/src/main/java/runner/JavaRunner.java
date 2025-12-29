package runner;

import java.io.*;
import java.util.*;

public class JavaRunner {

    private static final String JAVA_HOME =
        "C:\\Program Files\\Java\\jdk-21\\bin\\";

    public static void main(String[] args) throws Exception {

        // Absolute path to programs directory
        File programDir = new File(
                "C:\\Users\\maral\\OneDrive\\Documents\\visual_program\\store\\store\\common-module\\src\\main\\java\\programs"
        );

        if (!programDir.exists()) {
            throw new RuntimeException("Programs directory not found!");
        }

        // 1️⃣ Compile
        ProcessBuilder compile = new ProcessBuilder(
                JAVA_HOME + "javac.exe",
                "HelloProgram.java"
        );
        compile.directory(programDir);
        compile.redirectErrorStream(true);

        Process compileProcess = compile.start();
        printOutput(compileProcess);

        if (compileProcess.waitFor() != 0) {
            System.out.println("❌ Compilation failed");
            return;
        }

        // 2️⃣ Run
        ProcessBuilder run = new ProcessBuilder(
                JAVA_HOME + "java.exe",
                "HelloProgram",
                "FromRunner"
        );
        run.directory(programDir);
        run.redirectErrorStream(true);

        Process runProcess = run.start();
        printOutput(runProcess);
        runProcess.waitFor();
    }


    private static void printOutput(Process process) throws IOException {
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(process.getInputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
