package com.dict.dictionary.storage;

import com.dict.dictionary.storage.reader.Record;
import com.dict.dictionary.storage.reader.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DictionaryStorage implements Storage {
    public final String defaultDictionaryRootPath = "./target/res/";
    public String dictionaryRootPath;
    public final String dictionaryTypesFileName = "DictionaryTypes.txt";
    public List<DictionaryStorageEntity> validDictionaries;

    public DictionaryStorage() {
        connect();
    }

    @Override
    public boolean isValidDictionaryWord(String dn, String value) {
        return validDictionaries.stream().anyMatch(e -> e.getName().equals(dn) && e.isValidStorageEntityWord(value));
    }

    @Override
    public boolean dictionaryIsPresent(String name) {
        return validDictionaries.stream().anyMatch(d -> d.isValidStorageEntityName(name));
    }

    @Override
    public List<Record> add(String dictionaryName, Record word) {
        getDictionaryByName(dictionaryName).insert(word.getKey(), word.getValue());
        return null;
    }

    @Override
    public List<Record> find(String dictionaryName, String key) {
        return getDictionaryByName(dictionaryName).find(key);
    }

    @Override
    public List<Record> delete(String dictionaryName, String key) {
        getDictionaryByName(dictionaryName).delete(key);
        return null;
    }

    @Override
    public List<Record> all(String dictionaryName) {
        return getDictionaryByName(dictionaryName).readAll();
    }

    private void connect() {
        dictionaryRootPath = defaultDictionaryRootPath;
        setValidDictionaries(Paths.get(dictionaryRootPath, dictionaryTypesFileName));
    }

    private List<DictionaryStorageEntity> getValidDictionaries(Path path) {
        var dict = new ArrayList<DictionaryStorageEntity>();
        try {
            var s = Files.readAllLines(path);
            for (String s2 : s) {
                var s3 = s2.split("; ");
                dict.add(new DictionaryStorageEntity(s3[0].split("=")[1], s3[1].split("=")[1], dictionaryRootPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dict;
    }

    private void setValidDictionaries(Path dictionaryTypesFile) {
        validDictionaries = getValidDictionaries(dictionaryTypesFile);
    }

    private DictionaryStorageEntity getDictionaryByName(String name) {
        return this.validDictionaries.stream().filter(e -> e.getName().equals(name)).findFirst().get();
    }
}
