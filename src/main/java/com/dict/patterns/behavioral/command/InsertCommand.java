package com.dict.patterns.behavioral.command;

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
