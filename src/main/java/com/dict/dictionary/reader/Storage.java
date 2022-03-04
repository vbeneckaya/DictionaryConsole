package com.dict.dictionary.reader;

import java.util.List;

public interface Storage {
    boolean isValidDictionaryWord(String dn, String value);
    boolean dictionaryIsPresent(String dn);
    List<Record> add(String dictionaryName, Record word);
    List<Record> find(String dictionaryName, String key);
    List<Record> delete(String dictionaryName, String key);
    List<Record> all(String dictionaryName);
}
