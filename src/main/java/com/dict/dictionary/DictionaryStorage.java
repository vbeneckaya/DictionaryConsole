package com.dict.dictionary;

import com.dict.dictionary.config.Configuration;
import com.dict.dictionary.reader.Record;
import com.dict.dictionary.reader.Storage;
import com.dict.dictionary.reader.StorageEntity;
import com.dict.dictionary.reader.StorageEntityFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Отвечает за распределение действий между отдельными словарями не зависимо от типа хранилища
 */

public class DictionaryStorage implements Storage {
    private Configuration configuration;
    public List<StorageEntity> validDictionaries;
    private StorageEntityFactory entityFactory;

    public DictionaryStorage(Configuration configuration, StorageEntityFactory entityFactory) {
        this.configuration = configuration;
        this.entityFactory = entityFactory;
        connect();
    }

    @Override
    public boolean isValidDictionaryWord(String dn, String value) {
        return validDictionaries.stream().anyMatch(e -> e.getName().equals(dn) && e.isValidStorageEntityWord(value));
    }

    @Override
    public boolean dictionaryIsPresent(String name) {
        return validDictionaries.stream().anyMatch(d -> d.isValidStorageEntityName(name));
    }

    @Override
    public List<Record> add(String dictionaryName, Record word) {
        getDictionaryByName(dictionaryName).insert(word.getKey(), word.getValue());
        return null;
    }

    @Override
    public List<Record> find(String dictionaryName, String key) {
        return getDictionaryByName(dictionaryName).find(key);
    }

    @Override
    public List<Record> delete(String dictionaryName, String key) {
        getDictionaryByName(dictionaryName).delete(key);
        return null;
    }

    @Override
    public List<Record> all(String dictionaryName) {
        return getDictionaryByName(dictionaryName).readAll();
    }

    private void connect() {
        this.validDictionaries = new ArrayList<>();
        configuration.getParameters().forEach(e -> {
            this.validDictionaries.add(this.entityFactory.createStorageEntity(e, configuration));
        });
    }

    private StorageEntity getDictionaryByName(String name) {
        return this.validDictionaries.stream().filter(e -> e.getName().equals(name)).findFirst().get();
    }
}
