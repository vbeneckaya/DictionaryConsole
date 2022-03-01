package com.dict.patterns.structural.adapter;

public interface Dictionary {
    void insert(String key, String value);
    void remove(String key);
    void select(String key);
    void readAllLines();
}
