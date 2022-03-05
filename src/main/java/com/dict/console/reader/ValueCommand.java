package com.dict.console.reader;

import com.dict.console.ConsoleSyntax;
import com.dict.console.Messages;
import com.dict.console.dictclient.DictionaryClient;
import com.dict.console.dictclient.DictionaryClientRequest;


public class ValueCommand implements Command {
    private String pattern = ConsoleSyntax.createParameterPattern("value");

    public DictionaryClientRequest execute(DictionaryClientRequest request, String argString, DictionaryClient dictionaryClient) {
        var argValues = argString.split(ConsoleSyntax.ParamSeparator);
        if (argValues.length != 2) {
            request.message = Messages.ValueValueError;
            request.valid = false;
        } else {
            var val = argString.split(ConsoleSyntax.ParamSeparator)[1];
            if (dictionaryClient.isValidDictionaryWord(request.dn, val)) {
                request.value = val;
            } else {
                request.valid = false;
                request.message = Messages.NotValidValue;
            }
        }
        return request;
    }

    @Override
    public boolean hasMatch(String consoleData) {
        return consoleData.matches(pattern);
    }
}
