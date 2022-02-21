package com.dict.services.ConsoleService;

import com.dict.services.DictionaryService.Commands;
import com.dict.services.DictionaryService.DictServiceActionRequest;
import com.dict.services.DictionaryService.DictService;

import java.util.Arrays;
import java.util.Locale;


public class ConsoleService extends DictService {

    private static boolean isRunning;

    public static boolean isRunning() {
        return isRunning;
    }

    public static void stop() {
        isRunning = false;
    }

    public static void run() {
        isRunning = true;
        ConsoleService.init();
        ConsoleService.help();

        while (isRunning()) {
            ConsoleService.welcome();
            var commandString = System.console().readLine();

            if (ConsoleService.isExit(commandString)) {
                stop();
            } else {
                var request = ConsoleService.createActionRequestFromCommandLine(commandString);

                if (request.valid) {
                    var response = ConsoleService.executeActionRequest(request);
                    if (response.success) {
                        System.out.println("Success");
                        System.out.println(response.result != null ? response.result : "Without result");
                    } else {
                        System.out.println("Error: " + response.message);
                    }
                } else {
                    System.out.println("Request not valid");
                }
            }
        }
    }

    private static DictServiceActionRequest createActionRequestFromCommandLine(String commandString) {
        var args = commandString.toLowerCase(Locale.ROOT).split(" ");
        Arrays.sort(args);

        var request = new DictServiceActionRequest();

        if (args.length < 1 || args[0].equals("")) {
            request.message = Messages.NotEnoughArguments;
            return request;
        }
        for (String arg : args) {
            if (arg.matches(ConsoleCommands.All)) {
                request.command = Commands.ALL;
            } else if (arg.matches(ConsoleCommands.Add)) {
                request.command = Commands.ADD;
            } else if (arg.matches(ConsoleCommands.Find)) {
                request.command = Commands.FIND;
            } else if (arg.matches(ConsoleCommands.Delete)) {
                request.command = Commands.DELETE;
            } else if (arg.matches(ConsoleCommands.DictionaryName)) {
                var argValues = arg.split(ConsoleCommands.ParamSeparator);
                if (argValues.length != 2) {
                    request.message = Messages.DnValueError;
                    request.valid = false;
                } else {
                    var val = arg.split(ConsoleCommands.ParamSeparator)[1];

                    request.dn = validDictionaryName(val);
                    if (request.dn == null) {
                        request.message = Messages.NoSuchDictionary;
                        request.valid = false;
                    }

                }
            } else if (arg.matches(ConsoleCommands.Key)) {
                var argValues = arg.split(ConsoleCommands.ParamSeparator);
                if (argValues.length != 2) {
                    request.valid = false;
                    request.message = Messages.KeyValueError;
                } else {
                    request.key = arg.split(ConsoleCommands.ParamSeparator)[1];
                }
            } else if (arg.matches(ConsoleCommands.Value)) {
                var argValues = arg.split(ConsoleCommands.ParamSeparator);
                if (argValues.length != 2) {
                    request.message = Messages.ValueValueError;
                    request.valid = false;
                } else {
                    var val = arg.split(ConsoleCommands.ParamSeparator)[1];
                    if (isValidDictionaryWord(request.dn, val)) {
                        request.value = val;
                    } else {
                        request.valid = false;
                        request.message = Messages.NotValidValue;
                    }
                }
            } else {
                request.message = Messages.WrongArgument;
                request.valid = false;
            }
        }
        return request;
    }


    private static boolean isExit(String command) {
        return command.matches(ConsoleCommands.Exit);
    }

    private static void help() {
        System.out.println(Messages.Help);
    }

    private static void welcome() {
        System.out.println(Messages.Welcome);
    }
}
