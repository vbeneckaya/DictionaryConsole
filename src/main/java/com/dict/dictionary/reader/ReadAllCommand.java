package com.dict.dictionary.reader;

import java.util.List;

public class ReadAllCommand implements Command{
    Storage storage;

    public ReadAllCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public List<Record> execute(String id, String key, String value) {
        return storage.all(id);
    }
}
