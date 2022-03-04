package com.dict.patterns.structural.adapter;

public class DictionaryRunner {
    public static void main(String[] args) {
        Dictionary dictionary = new AdapterJavaToDictionary();

        dictionary.insert("key","value");
        dictionary.readAllLines();
        dictionary.select("key");
        dictionary.remove("key");
    }
}
