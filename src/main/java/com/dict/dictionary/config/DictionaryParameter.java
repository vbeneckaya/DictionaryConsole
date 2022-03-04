package com.dict.dictionary.config;

/**
 * <p>Хранит информацию о метаданных одного словаря</p>
 */

public class DictionaryParameter {
    public String dictionaryName;
    public String wordPattern;

    public DictionaryParameter(String dictionaryName, String wordPattern) {
        this.dictionaryName = dictionaryName;
        this.wordPattern = wordPattern;
    }
}
