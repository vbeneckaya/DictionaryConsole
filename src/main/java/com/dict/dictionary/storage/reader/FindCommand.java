package com.dict.dictionary.storage.reader;

import java.util.List;

public class FindCommand implements Command{
    Storage storage;

    public FindCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public List<Record> execute(String id, String key, String value) {
        return storage.find(id, key);
    }
}
