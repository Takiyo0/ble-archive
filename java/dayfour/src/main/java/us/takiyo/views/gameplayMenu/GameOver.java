package us.takiyo.views.gameplayMenu;

import us.takiyo.Main;
import us.takiyo.controllers.Player;
import us.takiyo.extensions.ViewExtension;
import us.takiyo.interfaces.View;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GameOver extends ViewExtension {
    Player player;
    Main main;

    private static final String RESET = "\u001B[0m";
    private static final String BRIGHT_RED = "\u001B[91m";
    private static final String BRIGHT_YELLOW = "\u001B[93m";
    private static final String BRIGHT_CYAN = "\u001B[96m";
    private static final String BOLD = "\u001B[1m";

    public GameOver(Main main, Player player) {
        super(ViewType.Normal);
        this.player = player;
        this.main = main;
        this.addOption();
    }

    public void addOption() {
        View page = new View(0);
        page.function = (currentState) -> {
            int target = 0, brk = 0;
            String[] gameOverMessage = {
                    "█████▀█████████████████████",
                    "█─▄▄▄▄██▀▄─██▄─▀█▀─▄█▄─▄▄─█",
                    "█─██▄─██─▀─███─█▄█─███─▄█▀█",
                    "▀▄▄▄▄▄▀▄▄▀▄▄▀▄▄▄▀▄▄▄▀▄▄▄▄▄▀",
                    "",
                    "████████████████████████",
                    "█─▄▄─█▄─█─▄█▄─▄▄─█▄─▄▄▀█",
                    "█─██─██▄▀▄███─▄█▀██─▄─▄█",
                    "▀▄▄▄▄▀▀▀▄▀▀▀▄▄▄▄▄▀▄▄▀▄▄▀"
            };

            try {
                for (int i = 0; i < gameOverMessage.length; i++) {
                    clearConsole();
                    System.out.println(BRIGHT_RED + BOLD + "GAME OVER" + RESET);
                    for (int j = 0; j <= i; j++) {
                        System.out.println(BRIGHT_CYAN + gameOverMessage[j] + RESET);
                    }
                    Thread.sleep(300);
                }

                System.out.println(BRIGHT_RED + "╔══════════════════════════════════╗");
                System.out.println("║      " + BRIGHT_YELLOW + BOLD + "   FINAL SCORE: " + this.player.getResource("money").getAmount() + RESET + BRIGHT_RED + "           ║");
                System.out.println(BRIGHT_RED + "╚══════════════════════════════════╝" + RESET);
                System.out.println(BRIGHT_CYAN + "Thanks for playing!" + RESET);
                System.out.println(BRIGHT_CYAN + "Press ENTER to exit." + RESET);

                int a = System.in.read();
                System.exit(0);
            } catch (InterruptedException | IOException ignored) {
            }
            return "" + target + brk;
        };

        this.options.add(page);
    }

    private static void clearConsole() {
        for (int i = 0; i < 50; ++i) System.out.println("\r\n");
        System.out.flush();
    }
}
