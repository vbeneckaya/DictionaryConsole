package com.dict.patterns.structural.adapter;

public class AdapterJavaToDictionary extends JavaApplication implements Dictionary {
    @Override
    public void insert(String key, String value) {
        saveObject();
    }

    @Override
    public void remove(String key) {
        deleteObject();
    }

    @Override
    public void select(String key) {
        loadObject();
    }

    @Override
    public void readAllLines() {
        loadObject();
    }
}
