package com.dict.services.DictionaryService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class DictService {
    protected final static String defaultDictionaryRootPath = "./target/res/";
    protected static String dictionaryRootPath;
    protected final static String dictionaryTypesFileName = "DictionaryTypes.txt";
    protected static List<Dict> validDictionaries;

    public static void init(String rootPath) {
        dictionaryRootPath = rootPath;
        setValidDictionaries(Paths.get(dictionaryRootPath, dictionaryTypesFileName));
    }

    public static void init() {
        dictionaryRootPath = defaultDictionaryRootPath;
        setValidDictionaries(Paths.get(dictionaryRootPath, dictionaryTypesFileName));
    }

    public static void init(List<Dict> dictionaries) {
        validDictionaries = dictionaries;
    }

    public static Dict getDictionaryByFileName(String fileName) {
        return validDictionaries.stream().filter(e -> e.file.equals(fileName)).findFirst().orElse(null);
    }

    public static DictServiceActionResponse executeActionRequest(DictServiceActionRequest data) {
        var res = new DictServiceActionResponse();
        if (data.valid) {
            var dict = validDictionaries.stream().filter(e -> e.file.equals(data.dn)).findAny().orElse(null);
            if (dict != null) {
                switch (data.command) {
                    case ADD:
                        dict.add(data.key, data.value);
                        break;
                    case ALL:
                        res.result = dict.all();
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
                res.message = "Dictionary not selected, "+ data.dn;
            }
        } else {
            res.success = false;
            res.message = "Request is not valid";
        }
        return res;
    }

    protected static void setValidDictionaries(Path dictionaryTypesFile) {
        validDictionaries = getValidDictionaries(dictionaryTypesFile);
    }

    public static List<Dict> getValidDictionaries() {
        return validDictionaries;
    }

    protected static boolean isValidDictionaryWord(String dn, String value) {
        return validDictionaries.stream().anyMatch(e -> e.file.equals(dn) && e.isValidDictionaryWord(value));
    }

    protected static String validDictionaryName(String name) {
        return validDictionaries.stream().filter(d -> d.isValidDictionaryName(name)).findFirst().map(d -> d.file).orElse(null);
    }

    private static @NotNull List<Dict> getValidDictionaries(Path path) {
        var dict = new ArrayList<Dict>();
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
