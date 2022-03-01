package com.dict.patterns.behavioral.command;

public class StorageRunner {
    public static void main(String[] args) {
        Storage storage = new Dictionary();

        Developer developer  = new Developer(
                new InsertCommand(storage),
                new ReadAllCommand(storage),
                new DeleteCommand(storage),
                new FindCommand(storage)
        );

        developer.insert("key", "value");
        developer.readAll();
        developer.find("key");
        developer.delete("key");
    }
}
