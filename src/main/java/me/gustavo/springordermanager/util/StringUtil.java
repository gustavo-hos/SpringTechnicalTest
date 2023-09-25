package me.gustavo.springordermanager.util;

public class StringUtil {

    public static boolean isEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

}
