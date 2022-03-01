package com.dict.services.DictionaryService;


import com.dict.services.Storage;

public class ReadAllCommand implements Command {
    Storage storage;

    public ReadAllCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(String key, String value) {
        storage.readAllLines();
    }
}
