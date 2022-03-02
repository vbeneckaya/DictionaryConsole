package com.dict.dictionary.storage.reader;

public interface Record {
    String getKey();
    void setKey(String name);
    String getValue();
    void setValue(String value);
    String toString();
}
