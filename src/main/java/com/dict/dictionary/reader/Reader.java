package com.dict.dictionary.reader;


import java.util.List;

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

    public  void insert(String id, String key, String value) {
        insert.execute(id, key, value);
    }

    public List<Record> readAll(String id) {
        return readAll.execute(id, null, null);
    }

    public void delete(String id, String key) {
        delete.execute(id, key, null);
    }

    public Record find(String id, String key) {
        return find.execute(id,key, null).stream().findFirst().orElse(null);
    }
}
