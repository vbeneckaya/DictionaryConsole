package com.dict.app;

import com.dict.console.ConsoleRunner;
import com.dict.console.dictclient.DictionaryClient;
import com.dict.console.reader.*;
import com.dict.dictionary.DictionaryStorage;
import com.dict.dictionary.config.Configuration;
import com.dict.dictionary.file.FileStorageEntityFactory;


public class Word {
    public static void main(String[] args) {
        Context context = initContext();

        var console = new ConsoleRunner(context.dictionaryClient, context.consoleReader);
        console.run();
    }

    private static Context initContext() {
        Context context = new Context();
        context.configuration = new Configuration("./target/res");
        context.storage = new DictionaryStorage(context.configuration, new FileStorageEntityFactory());
        context.dictionaryClient = new DictionaryClient(context.storage
                , new com.dict.dictionary.reader.Reader(
                    new com.dict.dictionary.reader.AddCommand(context.storage),
                    new com.dict.dictionary.reader.ReadAllCommand(context.storage),
                    new com.dict.dictionary.reader.DeleteCommand(context.storage),
                    new com.dict.dictionary.reader.FindCommand(context.storage)
                    )
        );
        context.consoleReader = new ConsoleReader(
                context.dictionaryClient,
                new AddCommand(),
                new AllCommand(),
                new DeleteCommand(),
                new FindCommand(),
                new DictionaryNameCommand(),
                new KeyCommand(),
                new ValueCommand(),
                new ExitCommand()
        );
        return context;
    }
}

