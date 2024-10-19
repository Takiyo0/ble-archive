package us.takiyo.managers;

import us.takiyo.controller.Player;
import us.takiyo.extensions.Master;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

public class IOManager {
    public static Scanner scanner = new Scanner(System.in);

    public static String getUsername(String prompt, int minLength, int maxLength, List<Player> players) {
        while (true) {
            Master.clearTerminal();
            System.out.print(prompt + "\n> ");
            String line = scanner.nextLine();
            if (Objects.equals(line, "BACK")) {
                return null;
            }
            if (line.length() < minLength || line.length() > maxLength) {
                Master.sendError(String.format("Invalid username length (must be between %d and %d)", minLength, maxLength));
                continue;
            }
            if (players != null) {
                for (Player player : players)
                    if (Objects.equals(player.getUsername(), line)) {
                        Master.sendError(String.format("Username named %s already exists. Try another username", line));
                        continue;
                    }
            }
            return line;
        }
    }

    public static String getPassword(String prompt, int minLength, int maxLength) {
        while (true) {
            Master.clearTerminal();
            System.out.println(prompt);
            String line = scanner.nextLine();
            if (Objects.equals(line, "BACK")) {
                return null;
            }
            if (line.length() < minLength || line.length() > maxLength) {
                Master.sendError("Invalid password length (must be between %d and %d)");
                continue;
            }
            return line;
        }
    }

    public static int handleChoice(String[] choices, Runnable before) {
        while (true) {
            Master.clearTerminal();
            before.run();
            for (int i = 0; i < choices.length; i++)
                System.out.printf("%d. %s\n", i + 1, choices[i]);
            System.out.print("> ");
            int choice = IOManager.getInt();
            if (choice < 1 || choice > choices.length) {
                Master.sendError(String.format("Invalid choice %d", choice));
                continue;
            }
            return choice;
        }
    }

    public static int handleRoleSelection(String[] roles, Runnable before) {
        while (true) {
            Master.clearTerminal();
            before.run();
            StringBuilder selection = new StringBuilder();
            selection.append("|");
            for (int i = 0; i <= roles.length; i++)
                selection.append(String.format("%15s", i == roles.length ? "" : (i + 1) + ". " + roles[i]));
            selection.append("|");

            for (int i = 0; i < selection.length(); i++)
                System.out.print("=");
            System.out.println();

            System.out.print("|");
            for (int i = 0; i < selection.length() - 2; i++)
                System.out.print(" ");
            System.out.println("|");

            System.out.println(selection);

            System.out.print("|");
            for (int i = 0; i < selection.length() - 2; i++)
                System.out.print(" ");
            System.out.println("|");

            for (int i = 0; i < selection.length(); i++)
                System.out.print("=");
            System.out.print("\n> ");

            int choice = IOManager.getInt();
            if (choice < 1 || choice > roles.length) {
                Master.sendError(String.format("Invalid choice %d", choice));
                continue;
            }
            return choice;
        }
    }

    public static int getInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ignored) {
            return -1;
        }
    }
}
