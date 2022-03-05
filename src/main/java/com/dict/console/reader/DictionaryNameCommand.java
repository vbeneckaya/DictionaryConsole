package com.dict.console.reader;

import com.dict.console.ConsoleSyntax;
import com.dict.console.Messages;
import com.dict.console.dictclient.DictionaryClient;
import com.dict.console.dictclient.DictionaryClientRequest;


public class DictionaryNameCommand implements Command {
    private String pattern = ConsoleSyntax.createParameterPattern("dn");

    public DictionaryClientRequest execute(DictionaryClientRequest request, String argString, DictionaryClient dictionaryClient) {
        var argValues = argString.split(ConsoleSyntax.ParamSeparator);
        if (argValues.length != 2) {
            request.message = Messages.DnValueError;
            request.valid = false;
        } else {
            var val = argString.split(ConsoleSyntax.ParamSeparator)[1];

            if (dictionaryClient.dictionaryIsPresent(val)) {
                request.dn = val;
            } else {
                request.message = Messages.NoSuchDictionary;
                request.valid = false;
            }
        }
        return request;
    }

    @Override
    public boolean hasMatch(String consoleData) {
        return consoleData.matches(pattern);
    }
}
