package com.dict.console.dictclient;

/**
 * <p>Хранит состояние запроса (модель получения данных)</p>
 */

public class DictionaryClientRequest {
    public String dn;
    public String key;
    public String value;
    public DictionaryClientCommands command;
    public boolean valid = true;
    public String message = "";
}
