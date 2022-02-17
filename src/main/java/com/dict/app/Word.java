package com.dict.app;

import com.dict.console.DictConsoleService;
import java.util.*;

public class Word {
    public static void main() {
        DictConsoleService.init();
        DictConsoleService.help();


        while (true) {
            Scanner in = new Scanner(System.in);
            DictConsoleService.welcome();
            String commandString = in.next();

            var request =  DictConsoleService.createActionRequestFromCommandLine(commandString);

            if (request.Valid) {

            }

        }
    }
}

