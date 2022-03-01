package com.dict.services.DictionaryService;

public interface Command {
    void execute(String key , String value);
}
