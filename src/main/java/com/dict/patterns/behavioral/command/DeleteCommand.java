package com.dict.patterns.behavioral.command;

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
