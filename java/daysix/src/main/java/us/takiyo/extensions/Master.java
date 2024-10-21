package us.takiyo.extensions;

import us.takiyo.utils.Colors;

import java.io.IOException;
import java.util.Scanner;

public class Master {
    public Scanner scanner;

    public Master() {
        this.scanner = new Scanner(System.in);
    }

    public void clearTerminal() {
        for (int i = 0; i < 50; i++) {
            System.out.print("\r\n");
        }
    }

    public int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void animate(String message, int loopHowMuch, boolean withN) {
        for (int i = 1; i <= loopHowMuch; i++) {
            System.out.print("\r" + message + ".".repeat(i % 4));
            if (withN) System.out.print("\n");
            sleepSecond(1);
        }
    }

    public String getUsername() {
        while (true) {
            System.out.print(Colors.PURPLE_BRIGHT + "Enter your username: " + Colors.RESET);
            String username = scanner.nextLine();
            if (!username.isEmpty()) return username;
            else sendError(Colors.RED_BOLD + "Username cannot be empty." + Colors.RESET);
        }
    }

    public void sleepSecond(int s) {
        try {
            Thread.sleep(s * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int parseInt(String num) {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void waitForEnter() {
        System.out.print(Colors.YELLOW + "Press" + Colors.GREEN_BRIGHT + " ENTER " + Colors.YELLOW + "to continue..." + Colors.RESET);
        scanner.nextLine();
    }

    public void sendError(String message) {
        System.out.println(message);
        this.waitForEnter();
    }
}