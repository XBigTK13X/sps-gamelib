package sps.util;

import java.lang.reflect.Field;

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

    public static String formatFields(Class target) {
        String config = "\"" + target.getSimpleName() + "\":{";
        Field[] fields = target.getDeclaredFields();
        int c = 0;
        for (Field f : fields) {
            try {
                config += "\"" + f.getName() + "\":\"" + f.get(null) + "\"";
                if (c++ < fields.length - 1) {
                    config += ",";
                }
            }
            catch (IllegalAccessException swallow) {
                //This will happen when trying to access private static fields,
                //These aren't needed when printing config values
            }
        }
        config += "}";
        return config;
    }
}
