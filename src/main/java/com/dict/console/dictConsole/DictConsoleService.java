package com.dict.console.dictConsole;
import com.dict.console.dictionary.Commands;
import com.dict.console.dictionary.DictActionRequest;
import com.dict.console.dictionary.DictService;

import java.util.Arrays;
import java.util.Locale;

public class DictConsoleService extends DictService {

    public static boolean isExit(String command){
        return command.matches(ConsoleCommands.Exit);
    }

    public static DictActionRequest createActionRequestFromCommandLine(String commandString) {
        var args = commandString.toLowerCase(Locale.ROOT).split(" ");
        Arrays.sort(args);

        var request = new DictActionRequest();

        if (args.length < 1 || args[0] == "") {
            request.Message = Messages.NotEnoughArguments;
            return request;
        }
        for (String arg : args) {
             if (arg.matches(ConsoleCommands.All)) {
                request.Command = Commands.ALL;
            } else if (arg.matches(ConsoleCommands.Add)) {
                request.Command = Commands.ADD;
            } else if (arg.matches(ConsoleCommands.Find)) {
                request.Command = Commands.FIND;
            } else if (arg.matches(ConsoleCommands.Delete)) {
                request.Command = Commands.DELETE;
            } else if (arg.matches(ConsoleCommands.DictionaryName)) {
                var argValues = arg.split(ConsoleCommands.ParamSeparator);
                if (argValues.length != 2) {
                    request.Message = Messages.DnValueError;
                    request.Valid = false;
                } else {
                    var val = arg.split(ConsoleCommands.ParamSeparator)[1];

                    request.Dn = isValidDictionaryName(val);
                    if (request.Dn == null){
                        request.Message = Messages.NoSuchDictionary;
                        request.Valid = false;
                    }

                }
            } else if (arg.matches(ConsoleCommands.Key)) {
                var argValues = arg.split(ConsoleCommands.ParamSeparator);
                if (argValues.length != 2) {
                    request.Valid = false;
                    request.Message = Messages.KeyValueError;
                } else {
                    request.Key = arg.split(ConsoleCommands.ParamSeparator)[1];
                }
            } else if (arg.matches(ConsoleCommands.Value)) {
                var argValues = arg.split(ConsoleCommands.ParamSeparator);
                if (argValues.length != 2) {
                    request.Message = Messages.ValueValueError;
                    request.Valid = false;
                } else {
                    var val = arg.split(ConsoleCommands.ParamSeparator)[1];
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
