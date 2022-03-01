package com.dict.services;

public interface Storage {
    void insert(String key, String value);
    void delete(String key);
    String find(String key);
    String readAll();
    String getName();
    String getStorageFullName();
    boolean isValidDictionaryWord(String data);
    boolean isValidDictionaryName(String data);
}
