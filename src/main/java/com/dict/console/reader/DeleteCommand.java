package com.dict.console.reader;

import com.dict.console.dictclient.DictionaryClient;
import com.dict.console.dictclient.DictionaryClientCommands;
import com.dict.console.dictclient.DictionaryClientRequest;


public class DeleteCommand implements Command {
    private String pattern = "^delete$";

    public DictionaryClientRequest execute(DictionaryClientRequest request, String argString, DictionaryClient dictionaryClient) {
        request.command = DictionaryClientCommands.DELETE;
        return request;
    }

    @Override
    public boolean hasMatch(String consoleData) {
        return consoleData.matches(pattern);
    }
}
