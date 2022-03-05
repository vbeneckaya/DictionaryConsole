package com.dict.app;

import com.dict.console.dictclient.DictionaryClient;
import com.dict.console.reader.ConsoleReader;
import com.dict.dictionary.config.Configuration;
import com.dict.dictionary.reader.Storage;


public class Context {
    public Configuration configuration;
    public Storage storage;
    public ConsoleReader consoleReader;
    public DictionaryClient dictionaryClient;
}
