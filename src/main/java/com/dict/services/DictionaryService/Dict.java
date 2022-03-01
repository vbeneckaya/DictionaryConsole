package com.dict.services.DictionaryService;

import com.dict.services.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dict implements Storage {
    public Dict(String file, String wordPattern, String rootPath) {
        this.file = file;
        this.wordPattern = wordPattern;
        this.path = Paths.get(rootPath,file);
    }

    public String wordPattern;
    public String file;
    public Path path;

    public void insert(String key, String value) {
        writeToFile(key, value);
    }

    public String readAll() {
        return readFromFile();
    }

    @Override
    public String getName() {
        return file.split("\\.")[0];
    }

    @Override
    public String getStorageFullName() {
        return file;
    }

    public String find(String key) {
        var existLines = readLinesFromFile();
        return existLines.stream().filter(e -> e.matches(getKeyRegexp(key))).findFirst().orElse("");
    }

    public void delete(String key) {
        var existLines = readLinesFromFile();
        var resString = new StringBuilder();
        var newLines = existLines.stream().map(e -> e.matches(getKeyRegexp(key)) ? "" : e + "\n").collect(Collectors.toList());
        for (String line : newLines) {
            resString.append(line);
        }
        if (!resString.toString().equals("")) {
            writeToFile(resString.toString());
        }
    }

    public boolean isValidDictionaryWord(String value){
        return value.matches(wordPattern);
    }

    public boolean isValidDictionaryName(String name){
        return file.matches("^" + name + "(\\..+)?$");
    }


    private void writeToFile(String data) {
        try {
            Files.write(path, data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String key, String value) {
        var data = (key + " " + value + "\n").getBytes();

        try {
            if (Files.exists(path)) {

                if (Files.readAllLines(path).stream().filter(l -> l.matches(getKeyRegexp(key))).findAny().isEmpty()) {
                    Files.write(
                            path,
                            data,
                            StandardOpenOption.APPEND);
                }
            } else {
                Files.write(
                        path,
                        data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readLinesFromFile() {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  new ArrayList<>();
    }

    private String readFromFile() {
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
        if (getClass() != o.getClass())
            return false;

        if (o == this) {
            return true;
        }

        Dict d = (Dict) o;

        return (this.file.equals(d.file) && this.wordPattern.equals(d.wordPattern));
    }
}

