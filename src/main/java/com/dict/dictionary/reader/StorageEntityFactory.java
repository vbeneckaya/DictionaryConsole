package com.dict.dictionary.reader;

import com.dict.dictionary.config.Configuration;
import com.dict.dictionary.config.DictionaryParameter;

/**
 * Отвечает за гибкость при создании экземляров словарей
 */
public interface StorageEntityFactory {
    StorageEntity createStorageEntity(DictionaryParameter dictionaryParameter, Configuration configuration);
}
