package com.dict.patterns.behavioral.command;

public interface Storage {
    void insert(String key, String value);
    void delete(String key);
    void select(String key);
    void readAllLines();
}
