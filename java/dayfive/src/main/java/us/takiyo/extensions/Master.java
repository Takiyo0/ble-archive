package us.takiyo.extensions;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Master {
    public Master() {
    }

    public static void clearTerminal() {
        for (int i = 0; i < 50; i++)
            System.out.println("\r\n");
    }

    public static void sleepSecond(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ignored) {
        }
    }

    public static void sendError(String error) {
        System.out.println(error);
        Master.waitForEnter();
    }

    public static void sendWithEnter(String msg) {
        System.out.println(msg);
        Master.waitForEnter();
    }

    public static void waitForEnter() {
        try {
            System.out.println("Press ENTER to continue...");
            int a = System.in.read();
        } catch (IOException ignored) {
        }
    }
}
