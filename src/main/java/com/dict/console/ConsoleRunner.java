package com.dict.console;

import com.dict.console.dictclient.DictionaryClient;
import com.dict.console.dictclient.DictionaryClientRequest;
import com.dict.console.reader.ConsoleReader;


import java.util.Arrays;
import java.util.Locale;

/**
 * <p>Обрабатывает действия пользователя в консоли</p>
 */

public class ConsoleRunner {
    private boolean isRunning;
    private DictionaryClient dictionaryClient;
    private ConsoleReader consoleReader;

    public ConsoleRunner(DictionaryClient dictionaryClient, ConsoleReader reader) {
        this.dictionaryClient = dictionaryClient;
        this.consoleReader = reader;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stop() {
        isRunning = false;
    }

    public void run() {
        isRunning = true;
        help();

        while (isRunning()) {
            welcome();
            var commandString = System.console().readLine();

            if (!commandString.isEmpty())
                if (consoleReader.isExitCommand(commandString)) {
                    stop();
                } else {
                    var request = createDictionaryClientRequestFromCommandLine(commandString);

                    if (request.valid) {
                        var response = dictionaryClient.executeActionRequest(request);
                        if (response.success) {
                            System.out.println("Success");
                            System.out.println(response.result != null ? response.result : "Without result");
                        } else {
                            System.out.println("Error: " + response.message);
                        }
                    } else {
                        System.out.println(request.message);
                    }
                }
        }
    }

    private DictionaryClientRequest createDictionaryClientRequestFromCommandLine(String commandString) {
        var args = commandString.toLowerCase(Locale.ROOT).split(" ");
        Arrays.sort(args);

        var request = new DictionaryClientRequest();

        if (args.length < 1 || args[0].equals("")) {
            request.message = Messages.NotEnoughArguments;
            return request;
        }
        for (String arg : args) {
            if (consoleReader.isAllCommand(arg)) {
                request = consoleReader.all(request, arg);
                continue;
            }

            if (consoleReader.isAddCommand(arg)) {
                request = consoleReader.add(request, arg);
                continue;
            }

            if (consoleReader.isFindCommand(arg)) {
                request = consoleReader.find(request, arg);
                continue;
            }

            if (consoleReader.isDeleteCommand(arg)) {
                request = consoleReader.delete(request, arg);
                continue;
            }
            if (consoleReader.isDictionaryNameCommand(arg)) {
                request = consoleReader.dictionaryName(request, arg);
                continue;
            }

            if (consoleReader.isKeyCommand(arg)) {
                request = consoleReader.key(request, arg);
                continue;
            }

            if (consoleReader.isValueCommand(arg)) {
                request = consoleReader.value(request, arg);
                continue;
            }
            request.message = Messages.WrongArgument;
            request.valid = false;
            return request;
        }
        return request;
    }

    private void help() {
        System.out.println(Messages.Help);
    }

    private void welcome() {
        System.out.println(Messages.Welcome);
    }
}
