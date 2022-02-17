package com.dict.console.dictionary;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class DictService {
    protected static String DictionaryRootPath = "../../../../../../res/";
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

    protected static void setValidDictionaries(String dictionaryTypesFile) {
        ValidDictionaries = getValidDictionaries(dictionaryTypesFile);
    }

    public static List<Dict> getValidDictionaries() {
        return ValidDictionaries;
    }

    protected static boolean isValidDictionaryWord(String dn, String value) {
        return ValidDictionaries.stream().filter(e -> e.File == dn && value.matches(e.WordPattern)).findFirst().isPresent();
    }


    private static List<Dict> getValidDictionaries(String dictionaryFile) {
        var path = Paths.get(dictionaryFile);
        var dict = new ArrayList<Dict>();
        try {
            var s = Files.readAllLines(path);
            for (String s2 : s) {
                var s3 = s2.split("; ");
                dict.add(new Dict(s3[0].split("=")[1], s3[1].split("=")[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dict;
    }
}
