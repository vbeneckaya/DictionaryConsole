package com.dict.patterns.structural.bridge;

public class ProgramCreator {
    public static void main(String[] args) {
        Program [] programs = null;
        for (Program program : programs) {
            program.developProgram();
        }
    }
}
