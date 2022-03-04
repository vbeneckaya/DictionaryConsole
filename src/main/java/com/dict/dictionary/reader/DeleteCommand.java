package com.dict.dictionary.reader;

import java.util.List;

public class DeleteCommand implements Command{
    Storage storage;

    public DeleteCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public List<Record> execute(String id, String key, String value) {
        return storage.delete(id, key);
    }
}
