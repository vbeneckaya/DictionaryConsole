package com.dict.dictionary.file;

import com.dict.dictionary.config.Configuration;
import com.dict.dictionary.config.DictionaryParameter;
import com.dict.dictionary.reader.StorageEntity;
import com.dict.dictionary.reader.Record;
import com.dict.dictionary.reader.Word;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Отвечает за действия одного файлового словаря
 */

public class FileStorageEntity implements StorageEntity {
    private String wordPattern;
    private String file;
    private Path path;
    private String name;

    public FileStorageEntity(DictionaryParameter dictionaryParameter, Configuration configuration) {
        this.wordPattern = dictionaryParameter.wordPattern;
        this.name = dictionaryParameter.dictionaryName;
        this.file = name + ".txt";
        this.path = Paths.get(configuration.getDictionaryRoot(), file);
    }

    public void insert(String key, String value) {
        writeToFile(key, value);
    }

    public List<Record> readAll() {
        return readLinesFromFile().stream().map(l -> mapStringToRecord(l)).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public List<Record> find(String key) {
        var existLines = readLinesFromFile();
        return existLines.stream().filter(e -> e.matches(getKeyRegexp(key))).map(l -> mapStringToRecord(l)).collect(Collectors.toList());
    }

    public void delete(String key) {
        var existLines = readLinesFromFile();
        var resString = new StringBuilder();
        var newLines = existLines.stream().map(e -> e.matches(getKeyRegexp(key)) ? "" : e + "\n").collect(Collectors.toList());
        for (String line : newLines) {
            resString.append(line);
        }

        writeToFile(resString.toString());
    }

    public boolean isValidStorageEntityWord(String value) {
        return value.matches(wordPattern);
    }

    public boolean isValidStorageEntityName(String name) {
        return name.equals(getName());
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
        return new ArrayList<>();
    }

    private String getKeyRegexp(String key) {
        return "^" + key + " .*";
    }

    private Record mapStringToRecord(String line) {
        var splits = line.split(" ");
        return new Word(splits[0], splits[1]);
    }


    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass())
            return false;

        if (o == this) {
            return true;
        }

        FileStorageEntity d = (FileStorageEntity) o;

        return (this.file.equals(d.file) && this.wordPattern.equals(d.wordPattern));
    }
}
