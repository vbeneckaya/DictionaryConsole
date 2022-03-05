package com.dict.dictionary.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Парсит строку в набор параметров
 */

public class ParametersString {
    private List<String[]> splittedData;
    private String paramSeparator = ";";
    private String valueSeparator = "=";

    public ParametersString(String params) {
        this.splittedData = splitConnectionString(params);
    }

    public String getValueByKey(String key) {
        var res = splittedData.stream().filter(e -> e[0].equals(key)).findFirst();
        return res.isPresent() ? res.get()[1] : "";
    }

    private List<String[]> splitConnectionString(String params) {
        return Arrays.stream(params.replace(" ", "").split(paramSeparator)).map(l -> l.split(valueSeparator)).collect(Collectors.toList());
    }
}
