package com.dict.dictionary.reader;

import java.util.List;

public interface Command {
    List<Record> execute(String id, String key , String value);
}
