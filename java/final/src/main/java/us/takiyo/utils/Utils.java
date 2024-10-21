package us.takiyo.utils;

import java.util.Scanner;

public class Utils {
    public static void clearTerminal() {
        for (int i = 0; i < 50; i++) System.out.println("\r\n");
    }

    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception ignored) {
            return 0;
        }
    }

    public static String getUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    public static void waitForEnter() {
        try {
            System.out.print("Press ENTER to continue...");
            new Scanner(System.in).nextLine();
        } catch (Exception ignored) {
        }
    }
}
