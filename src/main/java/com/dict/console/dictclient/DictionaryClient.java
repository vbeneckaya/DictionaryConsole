package com.dict.console.dictclient;

import com.dict.dictionary.reader.Record;
import com.dict.dictionary.reader.Storage;
import com.dict.dictionary.reader.Reader;


import java.util.List;

/**
 * <p>Выполняет комманды хранилища</p>
 */

public class DictionaryClient {
    private Storage storage;
    private Reader storageReader;

    public DictionaryClient(Storage storage, Reader reader) {
        this.storage = storage;
        this.storageReader = reader;
    }

    public DictionaryClientResponse executeActionRequest(DictionaryClientRequest request) {
        var res = new DictionaryClientResponse();
        if (request.valid) {
            if (this.storage.dictionaryIsPresent(request.dn))
                switch (request.command) {
                    case ADD:
                        storageReader.insert(request.dn, request.key, request.value);
                        break;
                    case ALL:
                        res.result = listToString(storageReader.readAll(request.dn));
                        break;
                    case DELETE:
                        storageReader.delete(request.dn, request.key);
                        break;
                    case FIND:
                        var rec = storageReader.find(request.dn, request.key);
                        res.result = rec == null ? "" : rec.toString();
                        break;
                    default:
                        res.success = false;
                        res.message = "No such command";
                }
            else {
                res.success = false;
                res.message = "Dictionary not selected, " + request.dn;
            }
        } else {
            res.success = false;
            res.message = "Request is not valid. " + request.message;
        }
        return res;
    }

    public boolean dictionaryIsPresent(String name) {
        return storage.dictionaryIsPresent(name);
    }

    public boolean isValidDictionaryWord(String id, String val) {
        return storage.isValidDictionaryWord(id, val);
    }

    private String listToString(List<Record> rec) {
        StringBuilder sb = new StringBuilder();
        rec.forEach(e -> sb.append(e.toString()+"\n"));
        return sb.toString();
    }
}
