package com.dict.console.dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class Dict {
    public Dict(String file, String wordPattern, String rootPath) {
        this.File = file;
        this.WordPattern = wordPattern;
        this.RootPath = rootPath;
    }

    public String WordPattern;
    public String File;
    public String RootPath;

    public void add(String key, String value) {
        writeToFile(key, value);
    }

    public String all() {
        return readFromFile();
    }

    public String find(String key) {
        var existLines = readLinesFromFile();
        return existLines.stream().filter(e -> e.matches(getKeyRegexp(key))).findFirst().orElse("");
    }

    public void delete(String key) {
        var existLines = readLinesFromFile();
        var resString = "";
        var newLines = existLines.stream().map(e -> e.matches(getKeyRegexp(key)) ? "" : e + "\n").collect(Collectors.toList());
        for (String line : newLines) {
            resString += line;
        }
        if (resString != null) {
            writeToFile(resString);
        }
    }


    private void writeToFile(String data) {
        var path = Paths.get(RootPath, File);

        try {
            Files.write(path, data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String key, String value) {
        var path = Paths.get(RootPath, File);
        var data = (key + " " + value + "\n").getBytes();

        try {
            if (Files.exists(path)) {

                if (Files.readAllLines(path).stream().filter(l -> l.matches(getKeyRegexp(key))).findAny().isEmpty()) {
                    System.out.println("no such key");

                    Files.write(
                            path,
                            data,
                            StandardOpenOption.APPEND);
                }
            } else {
                System.out.println("new dictionary file");
                Files.write(
                        path,
                        data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("write file exception");
        }
    }

    private List<String> readLinesFromFile() {
        var path = Paths.get(RootPath, File);

        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readFromFile() {
        var path = Paths.get(RootPath, File);

        try {
            return Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getKeyRegexp(String key) {
        return "^" + key + " .*";
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        Dict d = (Dict) o;

        return (this.File.equals(d.File) && this.WordPattern.equals(d.WordPattern));
    }
}

