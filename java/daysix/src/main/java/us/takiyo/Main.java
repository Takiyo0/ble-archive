package us.takiyo;

import us.takiyo.extensions.Master;
import us.takiyo.extensions.Player;
import us.takiyo.extensions.Role;
import us.takiyo.extensions.TakiyoList;
import us.takiyo.players.Lycan;
import us.takiyo.players.Seer;
import us.takiyo.players.Villager;
import us.takiyo.players.Werewolf;
import us.takiyo.utils.Colors;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main extends Master {
    public static int PLAYER_COUNT = 8;

    private final TakiyoList<Player> availablePlayers = new TakiyoList<>();

    private TakiyoList<Player> currentPlayers = new TakiyoList<>();
    private String username;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        this.loadPlayers();

        int state = 0; // 0 = home; 1 = play game;
        while (true) {
            clearTerminal();
            switch (state) {
                case 0: {
                    System.out.println(Colors.RED_BOLD +
                            "  ·▄▄▄▄  ▄▄▌ ▐ ▄▌▄▄▄ .▄▄▄  ▄▄▄ . ▐▄▄▄ ▄▄▄·  ▄▄· ▄ ?▄  ▄▄▄· ▄▄▌  \n" +
                            "  ██? ██ ██· █▌▐█▀▄.▀·▀▄ █·▀▄.▀·  ·██▐█ ▀█ ▐█ ▌?█▌▄▌?▐█ ▀█ ██?\n" +
                            "  ▐█· ▐█▌██?▐█▐▐▌▐▀▀?▄▐▀▀▄ ▐▀▀?▄?▄ ██▄█▀▀█ ██ ▄▄▐▀▀▄·▄█▀▀█ ██?\n" +
                            "  ██. ██ ▐█▌██▐█▌▐█▄▄▌▐█?█▌▐█▄▄▌▐▌▐█▌▐█ ?▐▌▐███▌▐█.█▌▐█ ?▐▌▐█▌▐▌\n" +
                            "  ▀▀▀▀▀?  ▀▀▀▀ ▀? ▀▀▀ .▀  ▀ ▀▀▀  ▀▀▀? ▀  ▀ ·▀▀▀ ·▀  ▀ ▀  ▀ .▀▀▀" + Colors.RESET);

                    System.out.print(Colors.YELLOW_BRIGHT +
                            "\n1. " + Colors.GREEN_BRIGHT + "Play DwereJackal\n" + Colors.YELLOW_BRIGHT +
                            "2. " + Colors.GREEN_BRIGHT + "Rules\n" + Colors.YELLOW_BRIGHT +
                            "3. " + Colors.RED_BRIGHT + "Quit" + Colors.PURPLE_BRIGHT +
                            "\n> " + Colors.RESET);
                    int choice = getChoice();
                    if (choice < 1 || choice > 3) {
                        sendError(Colors.RED_BOLD + "Invalid choice. " + Colors.RESET);
                        continue;
                    }
                    if (choice == 3) {
                        printExit();
                        System.exit(0);
                    }
                    state = choice;
                    break;
                }

                case 1: {
                    username = getUsername();
                    playGame();
                    state = 0;
                    break;
                }

                case 2: {
                    showRules();
                    state = 0;
                    break;
                }
            }
        }
    }

    private void printExit() {
        String[] yes = {
//                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⢀⣶⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠲⢿⠃⢀⡱⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠘⢷⣄⠿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡀⠀⠀⠀⠀⠀⢳⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⠛⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⡀⡤⢤⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⠀⠀⠀⢰⠁⠀⡸⠀⠀⡠⠦⣄⠀⠀⠀⠀⠀⠀⢠⣧⡀⠀\n",
//                "⠀⠀⠀⠀⠀⠀⠰⡎⠹⡀⣀⠤⢆⠀⠰⠁⡠⢬⠁⢸⠀⢰⠋⠻⠖⠒⢤⠀⢀⠎⢸⠃⠀⠀⢞⠀⠀⠑⠢⠴⠁⠀⡸⠁⠀⠀⠀⠀⠀⢻⣼⠃⠀\n",
//                "⠀⢀⡠⠔⠊⣙⡄⠱⠀⠛⡠⢄⠘⡄⢸⠀⠓⠚⠃⠘⡇⡎⠀⠖⢐⡄⢸⠁⡞⢀⣯⠤⢤⡀⠈⢑⠀⠀⢠⢄⣀⠜⠀⠀⠀⠀⠀⠀⠀⢀⣁⡀⠀\n",
//                "⠸⣽⣦⡄⢻⡁⠀⠀⢧⠀⡇⠘⣄⣹⠌⣶⠖⢺⣷⣖⣧⣓⡜⡀⣸⣀⡏⠸⠀⣴⠀⢰⠊⠁⠀⡞⠀⠀⣆⠀⡆⠐⠐⠂⢲⠀⡖⠙⡆⢸⡀⢰⠀\n",
//                "⠾⡟⢈⣿⠀⠱⡀⠀⠈⠧⠘⠀⣉⣤⠶⠛⠛⠉⠉⠉⠉⠉⠹⠛⢻⠶⣤⡓⠒⠃⣄⣨⡄⠀⠼⢄⣠⢴⡟⡷⢻⣼⣉⣧⣿⢄⢸⠀⠱⠤⠇⠈⡇\n",
//                "⠀⢰⡏⠀⢧⣀⠽⠀⠀⣠⡴⠟⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠧⠟⠀⡞⢹⣦⡀⠀⠀⠀⠀⠀⢀⡾⠃⠀⠀⠈⢸⡿⠿⣅⡤⢿⣦⣤⠐⠚⠉⠁\n",
//                "⠀⠀⠀⠀⠈⠁⠀⣠⣾⡿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠷⠊⠈⣷⡄⠀⠀⠀⢠⣾⠓⠄⣀⠀⠀⣼⢃⣾⠛⠀⠀⠀⣿⡆⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⠀⣴⢿⠘⡟⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠿⣷⠀⠀⣰⠟⠚⠀⠀⠈⠙⢲⡟⢸⠃⠀⠀⠀⢰⡿⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⣰⡇⠘⡄⠘⠛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣆⡾⠯⠀⠀⠀⠀⠀⣠⠏⠀⢸⠉⠉⠒⢦⡿⠁⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⢀⡟⢧⠀⠹⡄⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣟⠁⠀⠀⠀⠀⠀⣰⠋⠀⠀⡿⢤⣀⣠⣾⠁⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⢸⣧⠈⢆⠀⢹⠀⠀⢳⡀⠀⠀⠀⣴⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⠀⠀⠀⠀⢀⡾⠃⠀⠀⠀⡇⠀⢀⣿⠃⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⢸⣏⢣⡈⠳⠶⠃⠀⠈⢷⡀⢀⣼⣃⠀⢀⣴⠇⠀⠀⠀⠀⠀⠀⠀⢀⣿⡏⠀⠀⠀⢠⠟⢻⣦⣀⠀⣸⠗⠢⣼⡟⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⢸⠉⠙⠁⠀⠀⠀⠀⠀⠈⠛⠋⢏⣸⣷⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⠉⠑⠠⡀⠀⠀⠯⠞⣻⣦⠿⡀⣰⣿⠁⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⣼⠀⠀⠀⣀⠀⠀⠀⢠⠐⡢⢀⡠⠛⠛⠒⣖⠠⢄⠀⠀⠀⠀⢀⣼⣿⠇⠀⠀⠀⠙⣄⠀⠀⠸⠥⢿⡠⣼⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⣿⣤⡴⠞⠛⠻⣦⣄⣨⡿⠿⡏⠀⢀⣀⣤⡿⠀⠀⢱⡀⠀⣠⣾⣿⠏⠀⠀⠀⠀⠀⠘⡄⠀⠀⠰⣻⡟⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡹⡇⠀⠘⡄⢤⡼⢿⣿⣿⡄⠀⢠⣧⣼⣟⣼⠋⠀⠀⠀⠀⠀⠀⠀⠃⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⠤⠴⣀⠀⠙⢄⡀⠀⠈⣉⣫⣴⣾⣿⠙⡌⣻⠀⠀⠀⠀⠀⠀⠀⠀⡆⠀⠀⠀⣸⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠛⠓⠒⠛⠛⠛⠋⠁⠸⣇⠈⠛⠛⠃⠀⠀⠀⠀⠀⠀⣀⡜⠁⠀⠀⢠⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢷⣄⣀⣀⣀⣀⣀⣤⡶⠟⠉⠀⠀⠀⢰⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⠃⠀⠀⠀⠈⣷⠀⠀⠀⠀⠀⣘⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⢉⡀⠀⠀⢸⡏⠉⠉⣉⢉⣉⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⠀⣀⣾⠋⠛⠟⠛⠯⠷⣄⣀⠀⠀⠀⠀\n",
//                "⠀⠀⠀⠀⠀⡻⠁⠠⠁⠂⠀⣊⣀⣤⣤⣬⣀⣈⡄⠀\n",
//                "⢀⣴⠦⠖⣻⠁⠂⠀⣰⡎⣉⡁⠀⠀⠀⠀⠀⠀⠈⣿\n",
//                "⣿⠂⠑⢸⡇⠀⠄⠀⠜⠢⣝⡧⣟⣿⣿⠶⢏⢉⣀⡿\n",
//                "⣿⠯⡇⢸⡇⠹⡄⠀⠀⡀⠀⠁⡉⠛⠛⠛⢻⡇⠀⠀\n",
//                "⣯⢇⡉⢸⡇⡌⢂⠀⠀⠠⠀⠂⠀⠀⠅⠀⢸⡇⠀⠀\n",
//                "⣿⢦⡵⣼⡇⣓⡕⡇⠀⡀⠐⠀⠁⠀⠠⣀⣸⠃⠀⠀\n",
//                "⠀⠑⠻⢭⣇⢄⠢⡁⠣⣆⡉⢃⣦⡃⢆⠉⣿⠀⠀⠀\n",
//                "⠀⠀⠀⠀⢷⣧⣇⠉⠆⣿⠉⢹⣏⣆⡁⢁⡟⠀⠀⠀\n",
                "⠀⠀⠀⠀⠀⠉⠙⠛⠛⠃⠀⠀⠛⠛⠓⠉⠀⠀⠀⠀⠀⠀⠀",
                Colors.CYAN_BOLD +
                        "\n\n\n\n\n\n\n\n\n\n\n\n                                ....:::::::.....\n",
                "                           ..:-=+**###%%%%%%##**+=-:..\n",
                "                       ..:=+#%%%%%%%%%%%%%%%%%%##%%%#*=-:.\n",
                "                     .:=*%%%%%%%%%%%%%%%%%%%%%%#+%%%%%%%#+-:.\n",
                "                  .:-*%%%%%%%%%%%%%%%%%%%%%%%%%*#*+%%%%%%%%#=:.\n",
                "                .:=#%%%%%%%%%%%%%%%%%%%%#%%%%%#+--#%%%%%%%%%%#+:.\n",
                "               :-*%%%%%%%%%%%%%%%%%%%%%*#%%%#+::=##%%%%%%%%%%%%#=:.\n",
                "             .:+#%%%%%%%%%%%%%%%%%%%%#*+*+=-::=###%%%%%%%%%%%%%%%*:.\n",
                "            .:*%%%%%%%%%%%%%%%%##*+=-:::::--+#%**%%%%%%%%%%%%%%%%%#-:\n",
                "           .:*%%%%%%#%%##%#+=-::::::::--+*####+*%%%%%%%%%%%%%%%%%%%#-:\n",
                "          .:*%%%%%%####%#=::::..::-=+*#########%%%%%%%%%%%%%%%%%%%%%#-:\n",
                "          :=%%%%%%%==#%*:::...::=+##%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*:.\n",
                "         ::#%%%%%%#-*%#:::...:-=*##%%%%%%%%%%%%%%#%%%%%#%%%%%%%%%%%%%%=:\n",
                "         :+%%%%%%%#-#%+::.. .:-#%%%%%%%#*==-----===-=++#%%%%%%%%%%%%%%#:.\n",
                "        .:*%%%%%%%%+#%+::.. .:-*#%%%##=:::::::....:::::=#%%%%%%%%%%%%%%-.\n",
                "        .:#%%%%%%%%##%*::.. .::=++###****+++===::.. .:::-###%%%%%%%%%%%-:\n",
                "        .:%%%%%%%%%%##%-::.. ..:-==+****######+==::. ..::=%##%%%%%%%%%%=:\n",
                "        .:#%%%%%%%%%%%%#=::::.....::::::::+#*%%#*+::. .::-%%*#%%%%%%%%%-:\n",
                "         :*%%%%%%%%%%%%%#==---=-::::::-=+#%%%%%%%#::...::-%%+*%%%%%%%%#:.\n",
                "         :-%%%%%%%%%%%%#*#%%##*%%####%%%%%%%%###*=::..:::+%%=+%%%%%%%%+:\n",
                "         .:*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%#*=::..::::*%+-*%%%%%%%#-.\n",
                "          :-#%%%%%%%%%%%%%%%%%%%%%#%%%##%%#*+=-::...:::=####*#%%%%%%%=:\n",
                "           :-#%%%%%%%%%%%%%%%%%%%**####*+=-:::::::::-+#%##%%#%%%%%%%+:.\n",
                "            :=#%%%%%%%%%%%%%%%%#+*##*=-:::::--=+**#%%%%%%%%%%%%%%%%+:.\n",
                "             :-#%%%%%%%%%%%%%%###*=:::-=+*#%%%%%%%%%%%%%%%%%%%%%%%+:.\n",
                "              .:+%%%%%%%%%%%%###-::+#%%%%%%%%%%%%%%%%%%%%%%%%%%%#-:\n",
                "                :-#%%%%%%%%%%%*:-+#%%%%%%%%%%%%%%%%%%%%%%%%%%%#=:.\n",
                "                 .:=*%%%%%%%%*=**#%%%%%%%%%%%%%%%%%%%%%%%%%%#+:.\n",
                "                   .:-+#%%%%%#%**%%%%%%%%%%%%%%%%%%%%%%%%#*=:.\n",
                "                      .:-+#%%%%%#%%%%%%%%%%%%%%%%%%%%%#*=:.\n",
                "                         ..:=+*#%%%%%%%%%%%%%%%%%#*+=-:.\n",
                "                            ...:--==+++++++==--:...\n",
                "                                       .\n",
                "\n",
                "\n",
                Colors.YELLOW_BRIGHT,
                "             24-1 Relentlessly move forward and achieve our dreams.",
                Colors.RESET
        };

        for (int i = 0; i < yes.length; i++) {
            try {
                for (int j = 0; j < yes[i].length(); j++) {
                    System.out.print(yes[i].charAt(j));
//                    TimeUnit.NANOSECONDS.sleep(i <= 35 ? 1 : 10);
                    TimeUnit.NANOSECONDS.sleep(10);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public TakiyoList<Player> getCurrentPlayers() {
        return currentPlayers;
    }

    private void playGame() {
        int day = 1;

        TakiyoList<Number> killed = new TakiyoList<>();
        TakiyoList<Number> bad = new TakiyoList<>();
        TakiyoList<Number> good = new TakiyoList<>();
        while (true) {
            clearTerminal();
            System.out.println(Colors.YELLOW_BRIGHT + "╔══════════════════════════════════════╗");
            System.out.println("║" + Colors.GREEN_BRIGHT + centerText("Monya", 37) + Colors.YELLOW_BRIGHT + " ║");
            System.out.println("║" + Colors.GREEN_BRIGHT + centerText("Day " + day, 37) + Colors.YELLOW_BRIGHT + " ║");
            System.out.println("╠══════════════════════════════════════╣");
            if (day == 1)
                System.out.println("║ " + centerText("There's werewolves in this village", 36) + " ║");
            else {
                if (!killed.isEmpty())
                    for (Number number : killed) {
                        Player player = currentPlayers.get(number.intValue());
                        System.out.println("║ " + Colors.RED + centerText(player.getName() + " was killed by werewolf", 36) + Colors.YELLOW_BRIGHT + " ║");
                    }

                if (!bad.isEmpty())
                    for (Number number : bad) {
                        Player player = currentPlayers.get(number.intValue());
                        System.out.println("║ " + Colors.RED + centerText("Seer told that " + player.getName() + " was bad", 36) + Colors.YELLOW_BRIGHT + " ║");
                    }

                if (!good.isEmpty())
                    for (Number number : good) {
                        Player player = currentPlayers.get(number.intValue());
                        System.out.println("║ " + Colors.GREEN_BRIGHT + centerText("Seer told that " + player.getName() + " was good", 36) + Colors.YELLOW_BRIGHT + " ║");
                    }
            }
            System.out.println("╚══════════════════════════════════════╝" + Colors.RESET);

            if (day == 1) {
                TakiyoList<Player> temp = new TakiyoList<>();
                for (int i = 0; i < availablePlayers.size(); i++) {
                    int toMake = availablePlayers.get(i).getDefaultCount();
                    for (int j = 0; j < toMake; j++)
                        temp.add(availablePlayers.get(i));
                }
                Collections.shuffle(temp);

                currentPlayers = new TakiyoList<>();
                for (int i = 0; i < temp.size(); i++)
                    currentPlayers.add(temp.get(i).clone(i == 0 ? this.username : "Player " + (i + 1)));
            }

            int firstHalfIndex = (Main.PLAYER_COUNT + 1) / 2;
            for (int i = 0; i < firstHalfIndex; i++) {
                Player leftPlayer = currentPlayers.get(i);
                System.out.printf((leftPlayer.isDead() ? Colors.RED : Colors.BLUE_BRIGHT) + "%-10s: " + (leftPlayer.isDead() ? Colors.RED : Colors.GREEN) + "%-15s", leftPlayer.getName(), (i == 0 || leftPlayer.isDead()) ? leftPlayer.getRole().getName() : "??");
                if (firstHalfIndex + i < Main.PLAYER_COUNT) {
                    Player rightPlayer = currentPlayers.get(firstHalfIndex + i);
                    System.out.printf((rightPlayer.isDead() ? Colors.RED : Colors.BLUE_BRIGHT) + "%-10s: " + (rightPlayer.isDead() ? Colors.RED : Colors.GREEN) + "%-15s" + Colors.RESET, rightPlayer.getName(), rightPlayer.isDead() ? rightPlayer.getRole().getName() : "??");
                }
                System.out.println();
            }

            if (day != 1) {
                int werewolfAlive = 0;
                for (int i = 0; i < currentPlayers.size(); i++)
                    if (!currentPlayers.get(i).isDead() && currentPlayers.get(i).getRole().getName().equalsIgnoreCase("werewolf"))
                        werewolfAlive++;

                int villagersAlive = 0;
                for (int i = 0; i < currentPlayers.size(); i++)
                    if (!currentPlayers.get(i).isDead() && !currentPlayers.get(i).getRole().getName().equalsIgnoreCase("werewolf"))
                        villagersAlive++;

                if (werewolfAlive == 0) {
                    System.out.println(Colors.GREEN_BRIGHT + "╔══════════════════════════════════════╗");
                    System.out.println("║ " + Colors.BLUE_BRIGHT + centerText("Villagers Win!", 37) + Colors.GREEN_BRIGHT + " ║");
                    System.out.println("║ " + Colors.BLUE_BRIGHT + centerText(werewolfAlive + " werewolf alive, " + villagersAlive + " villagers alive", 37) + Colors.GREEN_BRIGHT + " ║");
                    System.out.println("╚══════════════════════════════════════╝" + Colors.RESET);
                    waitForEnter();
                    break;
                } else if (villagersAlive <= werewolfAlive) {
                    System.out.println(Colors.RED + "╔══════════════════════════════════════╗");
                    System.out.println("║ " + Colors.BLUE_BRIGHT + centerText("Werewolves Win!", 37) + Colors.RED + "║");
                    System.out.println("║ " + Colors.BLUE_BRIGHT + centerText(werewolfAlive + " werewolf alive, " + villagersAlive + " villagers alive", 37) + Colors.RED + "║");
                    System.out.println("╚══════════════════════════════════════╝" + Colors.RESET);
                    waitForEnter();
                    break;
                }
            }

            // day time...
            Map<Integer, Integer> votes = new HashMap<>();
            Player player = currentPlayers.get(0);
            if (!player.isDead()) {
                int target;
                while (true) {
                    System.out.printf(Colors.PURPLE_BOLD + "Who do you want to vote? (1-%s): " + Colors.RESET, currentPlayers.size());
                    int choice = getChoice();
                    if (choice < 1 || choice >= currentPlayers.size() || currentPlayers.get(choice - 1).isDead()) {
                        sendError(Colors.RED_BOLD + "Invalid choice or player is dead. " + Colors.RESET);
                        continue;
                    }
                    if (choice == 1) {
                        sendError(Colors.RED_BOLD + "You voted for yourself, you're a bad person. Retry." + Colors.RESET);
                        continue;
                    }
                    target = choice;
                    break;
                }
                votes.put(target - 1, 1);
                System.out.printf(Colors.PURPLE_BOLD + "    you voted for" + Colors.BLUE_BRIGHT + " %s\n\n" + Colors.RESET, currentPlayers.get(target - 1).getName());
                animate(Colors.PURPLE_BOLD + "Waiting for other players to vote" + Colors.RESET, 3, false);
                System.out.println();
            } else {
                System.out.printf(Colors.BLUE_BRIGHT + "    %s" + Colors.RED + " is dead.\n\n" + Colors.RESET, player.getName());
                animate(Colors.BLUE_BRIGHT + "Waiting for other players to vote" + Colors.RESET, 5, false);
                System.out.println();
            }

            for (int i = 1; i < currentPlayers.size(); i++) {
                player = currentPlayers.get(i);
                if (!player.isDead()) {
                    int ind = player.performDayAction(i, bad);
                    if (ind != -1)
                        votes.put(ind, votes.getOrDefault(ind, 0) + 1);
                    System.out.printf(Colors.BLUE_BRIGHT + "    %s" + Colors.RED + " voted for " + Colors.BLUE_BRIGHT + " %s\n" + (i != currentPlayers.size() - 1 ? "" : "\n") + Colors.RESET, player.getName(), currentPlayers.get(ind).getName());
                    sleepSecond(1);
                }
            }
            List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(votes.entrySet());
            entryList.sort(Map.Entry.<Integer, Integer>comparingByValue().reversed());
            LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();
            for (Map.Entry<Integer, Integer> entry : entryList) {
                sortedMap.put(entry.getKey(), entry.getValue());
            }

            int[] keys = sortedMap.keySet().stream()
                    .mapToInt(Integer::intValue)
                    .toArray();

            for (int i = 0; i < sortedMap.size(); i++) {
                System.out.printf(Colors.YELLOW_BRIGHT + "Top %d: " + Colors.BLUE_BRIGHT + "%s\n" + Colors.RESET, i + 1, currentPlayers.get(keys[i]).getName());
            }

            Player aboutToKill = currentPlayers.get(keys[0]);
//            System.out.printf("%s is about to be killed\n", aboutToKill.getName());
            animate(String.format(Colors.BLUE_BRIGHT + "    %s" + Colors.RED + " is about to be killed" + Colors.RESET, aboutToKill.getName()), 5, false);
            System.out.println();
            currentPlayers.get(keys[0]).kill();
            System.out.printf(Colors.BLUE_BRIGHT + "    %s" + Colors.RED + " has been killed\n" + Colors.RESET, aboutToKill.getName());
            waitForEnter();

            animate(Colors.BLUE + "    Night is coming" + Colors.RESET, 5, false);
            System.out.println();
            // night time...
            System.out.println(Colors.RED_BOLD + "╔══════════════════════════════════════╗");
            System.out.println("║" + Colors.BLUE_BRIGHT + centerText("Night Time...", 37) + Colors.RED_BOLD + " ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ " + Colors.BLUE_BRIGHT + centerText("Waiting all role do their action..", 36) + Colors.RED_BOLD + " ║");
            System.out.println("╚══════════════════════════════════════╝" + Colors.RESET);
            killed = new TakiyoList<>();
            bad = new TakiyoList<>();
            good = new TakiyoList<>();

            player = currentPlayers.get(0);
            if (player.getRole().getName().equalsIgnoreCase("werewolf") && !player.isDead()) {
                int target;
                while (true) {
                    System.out.printf(Colors.RED_BOLD + "Who do you want to kill? (enter player's number): " + Colors.RESET + "\n");
                    target = getChoice();
                    if (target < 1 || target >= currentPlayers.size() || currentPlayers.get(target - 1).isDead()) {
                        sendError(Colors.RED_BOLD + "Invalid choice or dead player. " + Colors.RESET);
                        continue;
                    }
                    if (target == 1) {
                        sendError(Colors.RED_BOLD + "You voted for yourself. Kill someone else, not you..." + Colors.RESET);
                        continue;
                    }
                    break;
                }
                killed.add(target - 1);
                currentPlayers.get(target - 1).kill();
                System.out.printf(Colors.RED_BOLD + "You killed %s\n" + Colors.RESET, currentPlayers.get(target - 1).getName());
            } else if (player.getRole().getName().equalsIgnoreCase("seer") && !player.isDead()) {
                int target;
                while (true) {
                    target = getChoice();
                    if (target < 1 || target >= currentPlayers.size() || currentPlayers.get(target).isDead() || currentPlayers.get(target).getRole().isRoleRevealed()) {
                        sendError(Colors.RED_BOLD + "Invalid choice or dead player or already revealed. " + Colors.RESET);
                        continue;
                    }
                    break;
                }
                if (!currentPlayers.get(target - 1).getRole().isBad()) bad.add(target - 1);
                else good.add(target - 1);
                System.out.printf(Colors.RED_BOLD + "You revealed %s\n" + Colors.RESET, currentPlayers.get(target - 1).getName());
            } else if (player.isDead() && (player.getRole().getName().equalsIgnoreCase("werewolf") || player.getRole().getName().equalsIgnoreCase("seer"))) {
                sendError(Colors.RED_BOLD + "You are dead thus not able to do actions. " + Colors.RESET);
            }

            sleepSecond(1);

            for (int i = 1; i < currentPlayers.size(); i++) {
                player = currentPlayers.get(i);
                if (!player.isDead()) {
                    int ind = player.performNightAction(i);
                    if (ind != -200) {
                        if (player.getRole().isBad())
                            killed.add(ind);
                        else {
                            if (currentPlayers.get(ind).getRole().isBad()) bad.add(ind);
                            else good.add(ind);
                        }
                    }
                }
            }

            for (Number badPlayer : bad)
                currentPlayers.get(badPlayer.intValue()).getRole().revealRole();
            for (Number goodPlayer : good)
                currentPlayers.get(goodPlayer.intValue()).getRole().revealRole();
            sleepSecond(1);
            day++;
        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, padding));
    }

    private void showRules() {
        System.out.println(Colors.YELLOW + "The game " + Colors.CYAN_BOLD + "DWereJackal" + Colors.YELLOW + " begins with the morning phase, \n" +
                "  Where an announcement is made: " + Colors.RED_BOLD + "\"There are Werewolves in this village.\"" + Colors.YELLOW + "\n" +
                "  The villagers' goal is to vote out all the werewolves and protect their village.\n" +
                "  The werewolves' goal is to kill all the villagers.\n" +
                "  There are only four roles in this game:\n" +
                "\n" +
                Colors.GREEN_BOLD + "  1. Villager: " + Colors.GREEN + "An ordinary human with no special abilities.\n" +
                Colors.RED_BOLD + "  2. Werewolf: " + Colors.RED + "A team of killers working together to eliminate villagers.\n" +
                Colors.PURPLE_BOLD + "  3. Seer: " + Colors.PURPLE + "A villager who can discover the roles of other players each night.\n" +
                Colors.YELLOW_BOLD + "  4. Lycan: " + Colors.YELLOW + "Appears as a werewolf when checked by the Seer but is actually a villager.\n" +
                "\n" +
                Colors.CYAN_BOLD + "  Each morning, two pieces of information are revealed:\n" + Colors.YELLOW +
                "  \"Who was killed?\" and \"Whose role was revealed by the Seer?\"\n" +
                "  After receiving this information, the villagers must vote to execute one player.\n" +
                "  At night, the Seer will perform their role of discovering another player's role\n" +
                "  While the werewolves will choose their next victim.\n" +
                "\n" +
                Colors.GREEN_BOLD + "  Good luck to all the players!" + Colors.RESET);


        waitForEnter();
    }

    private void loadPlayers() {
        Player[] players = {new Lycan(null, this), new Seer(null, this), new Villager(null, this), new Werewolf(null, this)};
        this.availablePlayers.addAll(Arrays.asList(players));
    }
}