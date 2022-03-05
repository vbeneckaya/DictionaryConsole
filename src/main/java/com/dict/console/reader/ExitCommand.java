package com.dict.console.reader;

import com.dict.console.dictclient.DictionaryClient;
import com.dict.console.dictclient.DictionaryClientCommands;
import com.dict.console.dictclient.DictionaryClientRequest;


public class ExitCommand implements Command {
    private String pattern = "^exit";

    public DictionaryClientRequest execute(DictionaryClientRequest request, String argString, DictionaryClient dictionaryClient) {
        return null;
    }

    @Override
    public boolean hasMatch(String consoleData) {
        return consoleData.matches(pattern);
    }
}
