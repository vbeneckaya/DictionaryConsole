package com.dict.dictionary.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Хранит информацию о настройках всех словарей</p>
 */

public class Configuration {
    public List<DictionaryParameter> parameters;
    private String connectionData;
    private String configFileName;
    private String connectionStringFileName;

    public String getStorageType() {
        return storageType;
    }

    private String storageType;

    public List<DictionaryParameter> getParameters() {
        return parameters;
    }

    public String getDictionaryRoot() {
        return connectionData;
    }

    public Configuration(String configDir) {
        this.configFileName = "DictionaryTypes.txt";
        this.connectionStringFileName = "ConnectionString.txt";
        init(configDir);
    }

    private void init(String configDir){
        parameters = new ArrayList<>();
        try {
            var s = Files.readAllLines(Paths.get(configDir, configFileName));
            for (String s2 : s) {
                var s3 = s2.split("; ");
                parameters.add(new DictionaryParameter(s3[0].split("=")[1], s3[1].split("=")[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            var lines = Files.readAllLines(Paths.get(configDir, connectionStringFileName));
            for (String line : lines) {
                var ps = new ParametersString(line);
                this.storageType = ps.getValueByKey("storage");
                this.connectionData = ps.getValueByKey("connect");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
