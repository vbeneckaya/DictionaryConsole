package com.dict.dictionary.file;

import com.dict.dictionary.config.Configuration;
import com.dict.dictionary.config.DictionaryParameter;
import com.dict.dictionary.reader.StorageEntity;
import com.dict.dictionary.reader.StorageEntityFactory;


/**
 * Отвечает за создание экземпляров в файловом хранилище
 */
public class FileStorageEntityFactory implements StorageEntityFactory {
    @Override
    public StorageEntity createStorageEntity(DictionaryParameter dictionaryParameter, Configuration configuration) {
        return new FileStorageEntity(dictionaryParameter, configuration);
    }
}
