package com.dict.dictionary.reader;

import java.util.List;

public interface StorageEntity {
    void insert(String key, String value);
    List<Record> readAll();
    String getName();
    List<Record> find(String key);
    void delete(String key);
    boolean isValidStorageEntityWord(String value);
    boolean isValidStorageEntityName(String name);
}
