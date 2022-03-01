package com.dict.app;


import com.dict.services.DictionaryService.Command;

public class Reader {
    private Command insert;
    private Command readAll;
    private Command delete;
    private Command find;

    public Reader(Command insert, Command readAll, Command delete, Command find) {
        this.insert = insert;
        this.readAll = readAll;
        this.delete = delete;
        this.find = find;
    }

    public void insert(String key, String value) {
        insert.execute(key, value);
    }

    public void readAll() {
        readAll.execute(null, null);
    }

    public void delete(String key) {
        delete.execute(key, null);
    }

    public void find(String key) {
        find.execute(key, null);
    }
}
