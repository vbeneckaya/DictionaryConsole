package com.dict.console.reader;

import com.dict.console.dictclient.DictionaryClient;
import com.dict.console.dictclient.DictionaryClientCommands;
import com.dict.console.dictclient.DictionaryClientRequest;


public class AllCommand implements Command {
    private String pattern = "^all$";

    public DictionaryClientRequest execute(DictionaryClientRequest request, String argString, DictionaryClient dictionaryClient) {
        request.command = DictionaryClientCommands.ALL;
        return request;
    }

    @Override
    public boolean hasMatch(String consoleData) {
        return consoleData.matches(pattern);
    }
}
