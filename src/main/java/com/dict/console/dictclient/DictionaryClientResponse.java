package com.dict.console.dictclient;

/**
 * <p>Хранит состояние запроса (модель получения данных)</p>
 */

public class DictionaryClientResponse {
    public String dn;
    public String key;
    public String value;
    public DictionaryClientCommands command;
    public boolean success = true;
    public String message = "";
    public String result;
}
