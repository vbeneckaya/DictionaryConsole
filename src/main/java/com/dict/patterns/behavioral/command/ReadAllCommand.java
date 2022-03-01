package com.dict.patterns.behavioral.command;

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
