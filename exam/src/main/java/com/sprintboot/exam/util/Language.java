package com.sprintboot.exam.util;

public enum Language {

    JAVA(
        "java",
        ".java",
        new String[]{"javac", "Main.java"},
        new String[]{"java", "Main"}
    ),

    C(
        "c",
        ".c",
        new String[]{"gcc", "main.c", "-o", "main"},
        new String[]{"./main"}
    ),

    CPP(
        "cpp",
        ".cpp",
        new String[]{"g++", "main.cpp", "-o", "main"},
        new String[]{"./main"}
    ),

    PYTHON(
        "python",
        ".py",
        null,                      // no compile step
        new String[]{"python", "main.py"}
    );

    public final String name;
    public final String fileExt;
    public final String[] compileCmd;
    public final String[] runCmd;

    Language(String name, String fileExt,
             String[] compileCmd, String[] runCmd) {
        this.name = name;
        this.fileExt = fileExt;
        this.compileCmd = compileCmd;
        this.runCmd = runCmd;
    }
}
