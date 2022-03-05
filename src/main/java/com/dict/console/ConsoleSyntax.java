package com.dict.console;

/**
 * <p>Отвечает за синтаксис консоли</p>
 */
public class ConsoleSyntax {
    public static final String ParamSeparator =  "=";
    public static final String ParamPrefix =  "--";
    public static String createParameterPattern(String command) {
        return "^" + ConsoleSyntax.ParamPrefix + command + ConsoleSyntax.ParamSeparator + ".*";
    }
}
