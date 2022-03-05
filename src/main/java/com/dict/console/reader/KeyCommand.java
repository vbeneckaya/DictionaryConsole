package com.dict.console.reader;

import com.dict.console.ConsoleSyntax;
import com.dict.console.Messages;
import com.dict.console.dictclient.DictionaryClient;
import com.dict.console.dictclient.DictionaryClientRequest;

public class KeyCommand implements Command {
    private String pattern = ConsoleSyntax.createParameterPattern("key");

    public DictionaryClientRequest execute(DictionaryClientRequest request, String argString, DictionaryClient dictionaryClient) {
        var argValues = argString.split(ConsoleSyntax.ParamSeparator);
        if (argValues.length != 2) {
            request.valid = false;
            request.message = Messages.KeyValueError;
        } else {
            request.key = argString.split(ConsoleSyntax.ParamSeparator)[1];
        }
        return request;
    }

    @Override
    public boolean hasMatch(String consoleData) {
        return consoleData.matches(pattern);
    }
}
