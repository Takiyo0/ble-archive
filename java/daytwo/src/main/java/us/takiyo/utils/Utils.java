package us.takiyo.utils;

public class Utils {
    public static int convertToInt(String a, int def) {
        try {
            return Integer.parseInt(a);
        } catch (Exception ignored) {
            return def;
        }
    }
}
