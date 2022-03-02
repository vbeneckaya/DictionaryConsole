package com.dict.dictionary.storage.reader;

import java.util.List;

public class AddCommand implements Command{
    Storage storage;

    public AddCommand(Storage storage) {
        this.storage = storage;
    }

    @Override
    public List<Record> execute(String id, String key, String value) {

        return storage.add(id, new Word(key, value));
    }
}
