package com.dict.services.DictionaryService;

import com.dict.app.Reader;
import com.dict.services.StoragePool;
import com.dict.services.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DictPool implements StoragePool {
    public final String defaultDictionaryRootPath = "./target/res/";
    public String dictionaryRootPath;
    public final String dictionaryTypesFileName = "DictionaryTypes.txt";
    public List<Storage> validDictionaries;
    public List<Reader> readers;

    public void init(String rootPath) {
        dictionaryRootPath = rootPath;
        setValidDictionaries(Paths.get(dictionaryRootPath, dictionaryTypesFileName));
    }

    public void init() {
        dictionaryRootPath = defaultDictionaryRootPath;
        setValidDictionaries(Paths.get(dictionaryRootPath, dictionaryTypesFileName));
    }

    public void init(List<Storage> dictionaries) {
        validDictionaries = dictionaries;
    }

    public Storage getDictionaryByFileName(String fileName) {
        return validDictionaries.stream().filter(e -> e.getStorageFullName().equals(fileName)).findFirst().orElse(null);
    }

    public void setValidDictionaries(Path dictionaryTypesFile) {
        validDictionaries = getValidDictionaries(dictionaryTypesFile);
    }

    public List<Storage> getValidDictionaries() {
        return validDictionaries;
    }

    @Override
    public List<Reader> getReaders() {
        return null;
    }

    @Override
    public void setReaders() {
        validDictionaries.forEach(e -> {
            readers.add(new Reader(
                    new InsertCommand(e),
                    new ReadAllCommand(e),
                    new DeleteCommand(e),
                    new FindCommand(e)
            ));
        });
    }

    public boolean isValidDictionaryWord(String dn, String value) {
        return validDictionaries.stream().anyMatch(e -> e.getStorageFullName().equals(dn) && e.isValidDictionaryWord(value));
    }

    public String validDictionaryName(String name) {
        return validDictionaries.stream().filter(d -> d.isValidDictionaryName(name)).findFirst().map(d -> d.getStorageFullName()).orElse(null);
    }

    public ActionResponse executeActionRequest(ActionRequest data) {
        var res = new ActionResponse();
        if (data.valid) {
            var dict = validDictionaries.stream().filter(e -> e.getStorageFullName().equals(data.dn)).findAny().orElse(null);
            if (dict != null) {
                switch (data.command) {
                    case ADD:
                        dict.insert(data.key, data.value);
                        break;
                    case ALL:
                        res.result = dict.readAll();
                        break;
                    case DELETE:
                        dict.delete(data.key);
                        break;
                    case FIND:
                        res.result = dict.find(data.key);
                        break;
                    default:
                        res.success = false;
                        res.message = "No such command";
                }
            } else {
                res.success = false;
                res.message = "Dictionary not selected, " + data.dn;
            }
        } else {
            res.success = false;
            res.message = "Request is not valid";
        }
        return res;
    }

    private List<Storage> getValidDictionaries(Path path) {
        var dict = new ArrayList<Storage>();
        try {
            var s = Files.readAllLines(path);
            for (String s2 : s) {
                var s3 = s2.split("; ");
                dict.add(new Dict(s3[0].split("=")[1], s3[1].split("=")[1], dictionaryRootPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dict;
    }
}
