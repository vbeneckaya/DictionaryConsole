package com.dict.console.reader;


import com.dict.console.dictclient.DictionaryClient;
import com.dict.console.dictclient.DictionaryClientRequest;

/**
 * Консолидирует варианты разбора коммандной строки на параметры
 */
public class ConsoleReader {
    private Command add;
    private Command all;
    private Command delete;
    private Command find;
    private Command dictionaryName;
    private Command key;
    private Command value;
    private Command exit;
    private DictionaryClient dictionaryClient;

    public ConsoleReader(DictionaryClient dictionaryClient, Command add, Command all, Command delete, Command find, Command dictionaryName, Command key, Command value, Command exit) {
        this.dictionaryClient = dictionaryClient;
        this.add = add;
        this.all = all;
        this.delete = delete;
        this.find = find;
        this.dictionaryName = dictionaryName;
        this.key = key;
        this.value = value;
        this.exit = exit;
    }

    public DictionaryClientRequest add(DictionaryClientRequest request, String argString) {
        return add.execute(request, argString, dictionaryClient);
    }

    public boolean isAddCommand(String argString){
        return add.hasMatch(argString);
    }

    public DictionaryClientRequest all(DictionaryClientRequest request, String argString) {
        return all.execute(request, argString, dictionaryClient);
    }

    public boolean isAllCommand(String argString){
        return all.hasMatch(argString);
    }

    public DictionaryClientRequest delete(DictionaryClientRequest request, String argString) {
        return delete.execute(request, argString, dictionaryClient);
    }

    public boolean isDeleteCommand(String argString){
        return delete.hasMatch(argString);
    }

    public DictionaryClientRequest find(DictionaryClientRequest request, String argString) {
        return find.execute(request, argString, dictionaryClient);
    }

    public boolean isFindCommand(String argString){
        return find.hasMatch(argString);
    }

    public DictionaryClientRequest dictionaryName (DictionaryClientRequest request, String argString) {
        return dictionaryName.execute(request, argString, dictionaryClient);
    }

    public boolean isDictionaryNameCommand(String argString){
        return dictionaryName.hasMatch(argString);
    }

    public DictionaryClientRequest key (DictionaryClientRequest request, String argString) {
        return key.execute(request, argString, dictionaryClient);
    }

    public boolean isKeyCommand(String argString){
        return key.hasMatch(argString);
    }

    public DictionaryClientRequest value (DictionaryClientRequest request, String argString) {
        return value.execute(request, argString, dictionaryClient);
    }

    public boolean isValueCommand(String argString){
        return value.hasMatch(argString);
    }

    public boolean isExitCommand(String argString){
        return exit.hasMatch(argString);
    }
}
