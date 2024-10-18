package us.takiyo.extensions;

import java.io.IOException;
import java.util.Scanner;

public class Master {
    public Scanner scanner;

    public Master() {
        this.scanner = new Scanner(System.in);
    }

    public void clearTerminal(boolean addSpacing) {
        for (int i = 0; i < 50; i++) {
            System.out.print(addSpacing ? " " : "");
        }
    }

    public int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
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
        try {
            System.out.println("Press ENTER to continue...");
            int a = System.in.read();
        } catch (IOException ignored) {
        }
    }

    public void sendError(String message) {
        System.out.println(message);
        this.waitForEnter();
    }
}
