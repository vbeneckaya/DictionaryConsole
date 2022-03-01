package com.dict.services;

import com.dict.app.Reader;
import com.dict.services.DictionaryService.ActionRequest;
import com.dict.services.DictionaryService.ActionResponse;

import java.nio.file.Path;
import java.util.List;

public interface StoragePool {
    void init();
    void setValidDictionaries(Path dictionaryTypesFile);
    List<Storage> getValidDictionaries();
    List<Reader> getReaders();
    void setReaders();
    boolean isValidDictionaryWord(String dn, String value);
    String validDictionaryName(String name);
    ActionResponse executeActionRequest(ActionRequest request);
}
