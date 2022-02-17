package com.dict.console.dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DictService {
    protected static String DictionaryRootPath = "./target/res/";
    //    protected static String DictionaryRootPath = "../../../../../../res/";
    protected static String DictionaryTypesFile = DictionaryRootPath + "DictionaryTypes.txt";
    protected static List<Dict> ValidDictionaries;

    public static void init(String dictionaryTypesFile) {
        setValidDictionaries(dictionaryTypesFile);
    }

    public static void init() {
        setValidDictionaries(DictionaryTypesFile);
    }

    public static void init(List<Dict> validDictionaries) {
        ValidDictionaries = validDictionaries;
    }

    public static Dict getDictionaryByFileName(String fileName) {
        return ValidDictionaries.stream().filter(e -> e.File == fileName).findFirst().get();
    }

    public static void setDictionaryRootPath(String path) {
        DictionaryRootPath = path;
    }

    public static DictActionResponse executeActionRequest(DictActionRequest data) {
        var res = new DictActionResponse();
        if (data.Valid) {
            var dict = ValidDictionaries.stream().filter(e -> e.File == data.Dn).findAny().orElse(null);
            if (dict != null) {
                System.out.println("Dictionary selected");
                switch (data.Command) {
                    case ADD:
                        dict.add(data.Key, data.Value);
                        break;
                    case ALL:
                        res.Result = dict.all();
                        break;
                    case DELETE:
                        dict.delete(data.Key);
                        break;
                    case FIND:
                        res.Result = dict.find(data.Key);
                        break;
                    default:
                        res.Success = false;
                        res.Message = "No such command";
                }
            } else {
                res.Success = false;
                res.Message = "Dictionary not selected, "+ data.Dn;
            }
        } else {
            res.Success = false;
            res.Message = "Request is not valid";
        }
        return res;
    }

    protected static void setValidDictionaries(String dictionaryTypesFile) {
        ValidDictionaries = getValidDictionaries(dictionaryTypesFile);
    }

    public static List<Dict> getValidDictionaries() {
        return ValidDictionaries;
    }

    protected static boolean isValidDictionaryWord(String dn, String value) {
        return ValidDictionaries.stream().filter(e -> e.File == dn && value.matches(e.WordPattern)).findFirst().isPresent();
    }

    protected static String isValidDictionaryName(String name) {
        var t = ValidDictionaries.stream().filter(d -> d.File.matches("^" + name + ".txt$")).findFirst();
        return t.isPresent() ? t.get().File : null;
    }

    private static List<Dict> getValidDictionaries(String dictionaryFile) {
        var path = Paths.get(dictionaryFile);
        var dict = new ArrayList<Dict>();
        try {
            var s = Files.readAllLines(path);
            for (String s2 : s) {
                var s3 = s2.split("; ");
                dict.add(new Dict(s3[0].split("=")[1], s3[1].split("=")[1], DictionaryRootPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Dictionaries: ");
        dict.forEach(d -> System.out.println(d.File));
        System.out.println("");
        return dict;
    }
}
