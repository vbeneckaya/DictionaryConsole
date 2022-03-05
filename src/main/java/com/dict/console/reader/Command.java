package com.dict.console.reader;


import com.dict.console.dictclient.DictionaryClient;
import com.dict.console.dictclient.DictionaryClientRequest;

/**
 * <p>Абстракция функционала строкового представления комманды пльзователя из консоли</p>
 */
public interface Command {
    DictionaryClientRequest execute(DictionaryClientRequest request, String argString, DictionaryClient dictionaryClient);
    boolean hasMatch(String consoleData);
}
