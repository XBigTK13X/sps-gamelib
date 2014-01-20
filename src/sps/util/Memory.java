package sps.util;

public class Memory {
    public static String getDebugInfo() {
        int mb = 1024 * 1024;
        String result = "";
        Runtime rt = Runtime.getRuntime();

        result += "Used:" + (rt.totalMemory() - rt.freeMemory()) / mb + ", ";
        result += "Free:" + (rt.freeMemory() / mb) + ", ";
        result += "Total:" + rt.totalMemory() / mb + ", ";
        result += "Max :" + rt.maxMemory() / mb;
        return result;
    }
}
