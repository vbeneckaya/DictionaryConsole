package com.dict.services.DictionaryService;

import com.dict.services.Storage;

public class DeleteCommand implements Command {
    Storage storage;

    public DeleteCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void execute(String key, String value) {
        storage.delete(key);
    }
}
