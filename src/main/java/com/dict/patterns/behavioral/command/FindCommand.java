package com.dict.patterns.behavioral.command;

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
