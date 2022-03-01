package com.dict.services.ConsoleService;

import com.dict.services.StoragePool;
import com.dict.services.DictionaryService.*;

import java.util.Arrays;
import java.util.Locale;


public class ConsoleService {

    private  boolean isRunning;

    public  boolean isRunning() {
        return isRunning;
    }

    public  void stop() {
        isRunning = false;
    }

    StoragePool storagePool = new DictPool();

    public  void run() {
        isRunning = true;
        storagePool.init();
        help();

        while (isRunning()) {
            welcome();
            var commandString = System.console().readLine();

            if (isExit(commandString)) {
                stop();
            } else {
                var request = createActionRequestFromCommandLine(commandString);

                if (request.valid) {
                    var response = storagePool.executeActionRequest(request);
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

    private ActionRequest createActionRequestFromCommandLine(String commandString) {
        var args = commandString.toLowerCase(Locale.ROOT).split(" ");
        Arrays.sort(args);

        var request = new ActionRequest();

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

                    request.dn = storagePool.validDictionaryName(val);
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
                    if (storagePool.isValidDictionaryWord(request.dn, val)) {
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


    private  boolean isExit(String command) {
        return command.matches(ConsoleCommands.Exit);
    }

    private  void help() {
        System.out.println(Messages.Help);
    }

    private  void welcome() {
        System.out.println(Messages.Welcome);
    }
}
