package com.dict.console.dictclient;

import com.dict.dictionary.DictionaryStorage;
import com.dict.dictionary.config.Configuration;
import com.dict.dictionary.file.FileStorageEntityFactory;
import com.dict.dictionary.reader.Record;
import com.dict.dictionary.reader.Storage;
import com.dict.dictionary.reader.Reader;
import com.dict.dictionary.reader.FindCommand;
import com.dict.dictionary.reader.DeleteCommand;
import com.dict.dictionary.reader.ReadAllCommand;
import com.dict.dictionary.reader.AddCommand;

import java.util.List;

/**
 * <p> Инициализирует хранилище и выполняет комманды хранилища</p>
 */

public class DictionaryClient {
    private Storage storage;
    private Reader storageReader;
    private Configuration configuration;

    public DictionaryClient() {
        this.configuration = new Configuration("./target/res");
        this.storage = new DictionaryStorage(configuration, new FileStorageEntityFactory());
        this.storageReader = new Reader(
                new AddCommand(storage),
                new ReadAllCommand(storage),
                new DeleteCommand(storage),
                new FindCommand(storage)
        );
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
        return this.storage.dictionaryIsPresent(name);
    }

    public boolean isValidDictionaryWord(String id, String val) {
        return this.storage.isValidDictionaryWord(id, val);
    }

    private String listToString(List<Record> rec) {
        StringBuilder sb = new StringBuilder();
        rec.forEach(e -> sb.append(e.toString()+"\n"));
        return sb.toString();
    }
}
