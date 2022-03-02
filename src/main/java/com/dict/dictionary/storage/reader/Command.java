package com.dict.dictionary.storage.reader;

import java.util.List;

public interface Command {
    List<Record> execute(String id, String key , String value);
}
