package com.dict.services.DictionaryService;


import com.dict.services.Storage;

public class FindCommand implements Command {
    Storage storage;

    public FindCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(String key, String value) {
        storage.select(key);
    }
}
