package sps.util;

public class JSON {
    public static String pad(String key, String value) {
        return "\"" + key + "\":\"" + value + "\"";
    }

    public static String pad(String key, int value) {
        return pad(key, value + "");
    }

    public static String pad(String key, float value) {
        return pad(key, value + "");
    }

    public static String delimit(String... paddedValues) {
        String result = "";
        for (String s : paddedValues) {
            result += s;
            if (!s.equalsIgnoreCase(paddedValues[paddedValues.length - 1])) {
                result += ",";
            }
        }
        return result;
    }
}
