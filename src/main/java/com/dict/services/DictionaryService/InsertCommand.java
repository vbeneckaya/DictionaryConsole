package com.dict.services.DictionaryService;


import com.dict.services.Storage;

public class InsertCommand implements Command {
    Storage storage;

    public InsertCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(String key, String value) {
        storage.insert(key, value);
    }
}
