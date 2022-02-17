package com.dict.app;

import com.dict.console.dictConsole.DictConsoleService;

import java.util.*;

public class Word {
    public static void main(String[] args) {
        DictConsoleService.init();
        DictConsoleService.help();

        while (true) {
            Scanner in = new Scanner(System.in);
            DictConsoleService.welcome();
            String commandString = in.nextLine();

            if (DictConsoleService.isExit(commandString)) {
                return;
            }
            var request = DictConsoleService.createActionRequestFromCommandLine(commandString);

            if (request.Valid) {
                var response = DictConsoleService.executeActionRequest(request);
                if (response.Success) {
                    System.out.println("Success");
                } else {
                    System.out.print("Error: ");
                    System.out.println(response.Message);
                }
            }

        }
    }
}

