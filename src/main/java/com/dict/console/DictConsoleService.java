package com.dict.console;
import com.dict.console.dictionary.Commands;
import com.dict.console.dictionary.Dict;
import com.dict.console.dictionary.DictActionRequest;
import com.dict.console.dictionary.DictService;

import java.util.Arrays;
import java.util.Locale;

public class DictConsoleService extends DictService {

    public static DictActionRequest createActionRequestFromCommandLine(String commandString) {
        var args = commandString.toLowerCase(Locale.ROOT).split(" ");
        Arrays.sort(args);

        var request = new DictActionRequest();

        if (args.length < 1 || args[0] == "") {
            request.Message = Messages.NotEnoughArguments;
            return request;
        }
        for (String arg : args) {
            if (arg.matches("^exit$")) {
                request.Command = Commands.EXIT;
            } else if (arg.matches("^all$")) {
                request.Command = Commands.ALL;
            } else if (arg.matches("^add$")) {
                request.Command = Commands.ADD;
            } else if (arg.matches("^find$")) {
                request.Command = Commands.FIND;
            } else if (arg.matches("^delete$")) {
                request.Command = Commands.DELETE;
            } else if (arg.matches("^--dn=.*")) {
                var argValues = arg.split("=");
                if (argValues.length != 2) {
                    request.Message = Messages.DnValueError;
                    request.Valid = false;
                } else {
                    var val = arg.split("=")[1];
                    var isValidDict = false;
                    for (Dict validDictionary : ValidDictionaries) {
                        if (validDictionary.File.matches("^" + val + ".txt$")) {
                            request.Dn = validDictionary.File;
                            isValidDict = true;
                        }
                        ;
                    }
                    if (isValidDict == false) {
                        request.Message = Messages.NoSuchDictionary;
                    }
                }
            } else if (arg.matches("^--key=.*")) {
                var argValues = arg.split("=");
                if (argValues.length != 2) {
                    request.Valid = false;
                    request.Message = Messages.KeyValueError;
                } else {
                    request.Key = arg.split("=")[1];
                }
            } else if (arg.matches("^--value=.*")) {
                var argValues = arg.split("=");
                if (argValues.length != 2) {
                    request.Message = Messages.ValueValueError;
                    request.Valid = false;
                } else {
                    var val = arg.split("=")[1];
                    if (isValidDictionaryWord(request.Dn, val)) {
                        request.Value = val;
                    } else {
                        request.Valid = false;
                        request.Message = Messages.NotValidValue;
                    }
                }
            } else {
                request.Message = Messages.WrongArgument;
                request.Valid = false;
            }
        }
        return request;
    }

    public static void help() {
        System.out.println(Messages.Help);
    }

    public static void welcome(){
        System.out.println(Messages.Welcome);
    }
}
