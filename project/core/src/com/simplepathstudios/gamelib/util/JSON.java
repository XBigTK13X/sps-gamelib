package com.simplepathstudios.gamelib.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Field;

public class JSON {
    private static JsonParser __parser;
    private static Gson __gson;

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

    public static JsonObject getObject(String raw) {
        if (__parser == null) {
            __parser = new JsonParser();
        }
        JsonElement element = __parser.parse(raw);
        return element.getAsJsonObject();
    }

    public static Gson getGson() {
        if (__gson == null) {
            __gson = new Gson();
        }
        return __gson;
    }
}
