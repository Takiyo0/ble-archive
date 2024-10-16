package us.takiyo;

import us.takiyo.interfaces.User;
import us.takiyo.managers.UserManager;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    Scanner scanner = new Scanner(System.in);
    UserManager userManager = new UserManager();

    private void clearTerminal() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    static int CountFibonacci(int i) {
        if (i <= 1) return i;
        return CountFibonacci(i - 1) + CountFibonacci(i - 2);
    }

    private int getInt() {
        try {
            return Integer.parseInt(this.scanner.nextLine());
        } catch (Exception ignored) {
            return -1;
        }
    }

    private void waitForEnter() {
        try {
            System.out.print("Press ENTER to continue...");
            int a = System.in.read();
        } catch (Exception ignored) {
        }
    }

    private void sendError(String message) {
        System.out.println(message);
        this.waitForEnter();
    }

    private void exit() {
        String[] art = new String[]{"                                                                                                    ",

                "                                              .::...::.                                             ",
                "                                      :=                     .*                                     ",
                "                                  -    +@@@@@@@@@@@@@@@@@@@@@@.   :.     .                          ",
                "                              +:  #@@@@@@@%%%%%%%%%%%%%%%%@@@@@@@@@   =.                            ",
                "                           :-  @@@@@@%%%%%%%%%%%%%%%%%%%%%@+ @%%%@@@@@   *                          ",
                "                         *  @@@@@%%%%%%%%%%%%%%%%%%%%%%%%%@* @@@@%%%%@@@@  --                       ",
                "                       #  @@@@%%%%%%%%%%%%%%%%%%%%%%%%%%%@@ @@ -@%%%%%%@@@@@ :=                     ",
                "                     *  @@@@%%%%%%%%%%%%%%%%%%%%%@%@%%%%@@@@.  @@%%%%%%%%%@@@% -.                   ",
                "                   :- @@@@%%%%%%%%%%%%%%%%%%%%%%@@@@%%@@@@:   @@%%%%%%%%%%%%@@@  *                  ",
                "                  *  @@@%%%%%%%%%%%%%%%%%%%%%%%@@ @@@@@@+   @@@%%%%%%%%%%%%%%%@@@ -.                ",
                "                 * @@@%%%%%%%%%%%%%%%%%%%%%%@@@@+@@@+     @@@=+@%%%%%%%%%%%%%%%@@@  *               ",
                "                + @@@%%%%%%%%%%%%%%%@@@@@@@@@@         .@@@@ =@@%%%%%%%%%%%%%%%%%@@. %              ",
                "               = @@@%%%%@@@%@@@@@@@@@@@-            +@@@@@  @@@%%%%%%%%%%%%%%%%%%%@@+ *             ",
                "              + @@@%%%%@#+@@@=#@@@        :.    ++@@@@@  : @@@%%%%%%%%%%%%%%%%%%%%%@@= *            ",
                "             % @@%%%%%%@%@@:.@@    - +  :   #*%@@@@: :#@@*@@@%%%%%%%%%%%%%%%%%%%%%%%@@ .+  .        ",
                "            =  @@%%%%%@@   @@@  :::*  -  *+@@@@@@@@@@@@%*@@%%%%%%%%%%%%%%%%%%%%%%%%%%@@ +           ",
                "            # @@%%%%%%@@  @@@  -:#   = @@@@@@@%%%%%%%%%@%%%%%%%%%%%%@@@%%%%%%%%%%%%%%@@@ @          ",
                "           % =@%%%%%%%@@ @@@@ -+=. .# @- @@%%%%%%%@@@@@@@@@@@@@@@@@@@+@%%%%%%%%%%%%%%%@@ #          ",
                "           @ @@%%%%%%%@% @@@  =* - -+ %@@@%%%%%@@@@@         @@   @@ @@%%%%%%%%%%%%%%%%@= -         ",
                "        .  * @@%%%%%%%@@ @@@ -+ : ::* @@@@%%@@@@@        .:-:   ##   @@@%%%%%%%%%%%%%%%@@ @         ",
                "        . -:.@%%%%%%%%@@ @@@ .+ - .:# @ #@@%@ @      .:        :++=-  @@@@@@%%%%%%%%%%%@@ @         ",
                "        . * #@%%%%%%%%%@+=@@. == . =.= @@ @@@@@@@@@@=   @@   * . .=*+  @@*:@@%%%%%%%%%%@@ @         ",
                "        . % %@%%%%%%%%%@@-=@@  +:-. ::+  @@  *@@@@@@@@@@@. @@ ---  . +  @@:-@@%%%%%%%%%@@ @         ",
                "        . + *@%%%%%%%%%%@@@-@@  =+%=    :  .:-#%*=    @ @@@* @ *.+ + *- @@@ @@%%%%%%%%%@@ @         ",
                "          .- @%%%%%%%%%%%%@@@@@   --::=.            +@ @@%@@%@ :*- + *- #@@::@%%%%%%%%%@@ @         ",
                "        .  % @@%%%%%%%%%%%%%%@@:#.  :@            @@@@@@%%%@@@:.#: - +- @@@@ @@%%%%%%%%@@ @         ",
                "           % @@%%%%%%%%%%%%%%@ @@@@@@@#@@@@@@@@@@@@@%%%%@@@@@=.:# : *=  @@@  @@%%%%%%%@@ :          ",
                "           +: @@%%%%%%%%%%%%%@@@@%%%@@@@%%%%%%%%%%%%%%%%%@@@%@ +  .+=- @@@@  @@%%%%%%%@@ @          ",
                "            % @@%%%%%%%%%%%%%%%%%%%%%%%%%%@@@%%@@@@@@@@@@@*@  =  *:=:  :@@  %@@%%%%%%@@-.+          ",
                "            :- @@%%%%%%%%%%%%%%%%%%%%%%%@@@=@@@%*-=@@@@+#-  :  +-:-   @@=+@@@@%%%%%%%@@ %           ",
                "             +  @@%%%%%%%%%%%%%%%%%%%%%@@@:#  -@@@@@#+    :  -      @@*.@@@ @@%%%%%%@@ +            ",
                "              @ @@@%%%%%%%%%%%%%%%%%%%@@  %@@@@+*             :@@@@%@@@@@#@@%%%%%%@@ :=            ",
                "               # @@@%%%%%%%%%%%%%%%%%@@  @@@@*          *@@@@@@@@%%@@@%%%%%%%%%%%%@@ .-             ",
                "                # @@@%%%%%%%%%%%%%%%@@-%@@@      -@@@@@@@@@%%%%%%%%%%%%%%%%%%%%%@@@ ::              ",
                "                 +  @@@%%%%%%%%%%%%@@ @@@    @@@@@@@%%%%%%%%%%%%%%%%%%%%%%%%%%%@@@ =                ",
                "               .  .: @@@%%%%%%%%%%%@+@@   -@@@@%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%@@@: #                 ",
                "                    *  @@@%%%%%%%%%@@@  +@@@@%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%@@@@ :=                  ",
                "                      + .@@@%%%%%%%%@  @@#@@%%%%%%%%%%%%%%%%%%%%%%%%%%%%%@@@@  +                    ",
                "                        =  @@@@%%%%@@@@=.@@%%%%%%%%%%%%%%%%%%%%%%%%%%%@@@@@  %                      ",
                "                          +  @@@@@%%@@@: @%%%%%%%%%%%%%%%%%%%%%%%%%%@@@@= .+                        ",
                "                            -:  @@@@@@@@@@%%%%%%%%%%%%%%%%%%%%%@@@@@@   +                           ",
                "                               +:   @@@@@@@@@%%%%%%%%%%%%@@@@@@@@@   =                              ",
                "                                   .:    =@@@@@@@@@@@@@@@@@%:    :                                  ",
                "                                         =.                --                                       ",
                "                                                                                                    ",
                "                           Relentlessy Move Forward And Achieve Our Dreams                          ",
                "                                                 24-1                                               "};

        for (String s : art) {
            for (int a = 0; a < s.length(); a++) {
                System.out.print(s.charAt(a));
                try {
                    Thread.sleep(1L);
                } catch (Exception ignored) {
                }

            }
            System.out.println();
        }
    }

    public Main() {
        int state = 0; // 0 = home; 1 = play game; 2 = how to play; 3 = exit;
        while (true) {
            this.clearTerminal();
            switch (state) {
                case 0: {
                    System.out.print(
                            "=========================================\n" +
                                    "|             Zwitch Kreasure           |\n" +
                                    "=========================================\n" +
                                    "| 1. Play Game                          |\n" +
                                    "| 2. Leaderboard                        |\n" +
                                    "| 3. How To Play                        |\n" +
                                    "| 4. Exit                               |\n" +
                                    "=========================================\n> "
                    );
                    int choice = this.getInt();
                    if (choice == -1) {
                        this.sendError("Choice must be a number");
                        continue;
                    }
                    if (choice < 1 || choice > 5) {
                        this.sendError("Choice must be between 1-4");
                        continue;
                    }
                    state = choice;
                    break;
                }

                case 1: {
                    this.PlayGame();
                    state = 0;
                    break;
                }

                case 2: {
                    this.RenderLeaderboard();
                    state = 0;
                    break;
                }

                case 3: {
                    this.printTutorial();
                    state = 0;
                    break;
                }

                case 4: {
                    this.exit();
                    return;
                }

//                case 699999999: {
//                    this.userManager.addUser(new User("takiyo", 69, 6969));
//                    System.out.println("Debug done");
//                    this.waitForEnter();
//                    state = 0;
//                    break;
//                }
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }

    public void PlayGame() {
        int state = 0; // 0 = game; 1 = about to quit;

        int[] lamps = {0, 0, 0, 0, 0};
        int[][] leverSet = new int[5][5];
//        {
//            {0,1,0,0,0}
//            {0,1,0,1,0}
//            {0,0,1,0,0}
//            {1,0,0,0,0}
//            {1,0,1,0,1}
//        }
        int random = ThreadLocalRandom.current().nextInt(2); // 0-1
        int[] lampsAffected;
        if (random == 0) lampsAffected = new int[]{2, 2, 1};
        else lampsAffected = new int[]{1, 3, 1};

        while (!isWinnable(lamps, leverSet)) {
            for (int i = 0; i < leverSet.length; i++)
                for (int a = 0; a < lamps.length; a++)
                    leverSet[i][a] = 0;

            for (int i = 0; i < leverSet.length; i++) {
                int leversToAffect = lampsAffected[ThreadLocalRandom.current().nextInt(lampsAffected.length)];
                int affected = 0;

                while (affected < leversToAffect) {
                    int lampIndex = ThreadLocalRandom.current().nextInt(lamps.length);
                    if (leverSet[i][lampIndex] == 0) {
                        leverSet[i][lampIndex] = 1;
                        affected++;
                    }
                }
            }
        }

        int totalMove = 0;
        boolean win = false;
        while (totalMove <= 25) {
            this.clearTerminal();
            switch (state) {
                case 0: {
                    System.out.printf(
                            "Lamp status:" +
                                    "\n╔══════════════════════════════════════════════════════╗" +
                                    "\n║  Lamp 1  ║  Lamp 2  ║  Lamp 3  ║  Lamp 4  ║  Lamp 5  ║" +
                                    "\n║   %-4s   ║   %-4s   ║   %-4s   ║   %-4s   ║   %-4s   ║" +
                                    "\n╚══════════════════════════════════════════════════════╝\n",
                            this.G(lamps[0]), this.G(lamps[1]), this.G(lamps[2]), this.G(lamps[3]), this.G(lamps[4]));
                    System.out.println("Your Move: " + totalMove);
                    System.out.print("Choose a lever to pull (1-5)[0 to give up]\n> ");
                    int choice = this.getInt();
                    if (choice == -1) {
                        sendError("Choice must be a number");
                        continue;
                    }
                    if (choice == 0) {
                        state = 1;
                        continue;
                    }
                    if (choice < 1 || choice > 5) {
                        sendError("Choice must be between 1-5");
                        continue;
                    }

                    int[] lampToSwitch = leverSet[choice - 1];
                    for (int i = 0; i < lampToSwitch.length; i++)
                        if (lampToSwitch[i] == 1)
                            lamps[i] = (lamps[i] == 0) ? 1 : 0;
                    if (isWin(lamps)) {
                        win = true;
                        break;
                    }
                    totalMove++;
                    continue;
                }

                case 1: {
                    System.out.print("You sure you want to give up? [Y/N]\n> ");
                    String res = this.scanner.nextLine();
                    if (Objects.equals(res, "Y")) {
                        return;
                    } else if (Objects.equals(res, "N")) {
                        state = 0;
                    } else sendError("Invalid option");
                    break;
                }
            }
        }

        if (!win) {
            System.out.println("Aww you lose :( better luck next time!");
            this.waitForEnter();
            return;
        }

        int score = Main.CountFibonacci((100 - totalMove / 4) + 5);
        while (true) {
            System.out.print("Enter your name\n> ");
            String name = this.scanner.nextLine();
            if (name.length() < 5) {
                sendError("Name must be at least 5 characters");
                continue;
            }
            if (this.userManager.GetUser(name) != null) {
                this.userManager.GetUser(name).Moves += totalMove;
                this.userManager.GetUser(name).Scores += score;
            } else {
                this.userManager.addUser(new User(name, totalMove, score));
            }
            this.userManager.save();
            break;
        }

        System.out.println("Congrats you win! You solved this puzzle with " + totalMove + " moves. You got " + score + " score");
        waitForEnter();
    }

    private void RenderLeaderboard() {
        List<User> sorted = this.userManager.GetSortedUsers();

        System.out.printf(
                "Leaderboard:\n" +
                        "\n╔════════════════════════╦═══════════╦══════════════╗" +
                        "\n║  %-20s  ║  %-7s  ║  %-10s  ║" +
                        "\n╠════════════════════════╬═══════════╬══════════════╣\n", "Username", "Moves", "Scores");

        for (User user : sorted) {
            System.out.printf("║  %-20s  ║  %-7d  ║  %-10d  ║", user.Username, user.Moves, user.Scores);
        }
        System.out.println("\n╠════════════════════════╩═══════════╩══════════════╝\n");
        waitForEnter();
    }


    private String G(int a) {
        return a == 1 ? "OFF" : "ON";
    }

    private boolean isWinnable(int[] lamps, int[][] leverSet) {
        Arrays.fill(lamps, 0);

        for (int[] ints : leverSet)
            for (int j = 0; j < lamps.length; j++)
                if (ints[j] == 1)
                    lamps[j] = (lamps[j] == 0) ? 1 : 0;

        return isWin(lamps);
    }

    private boolean isWin(int[] lamps) {
        for (int lamp : lamps)
            if (lamp == 0) return false;
        return true;
    }

    private void printTutorial() {
        System.out.println("===========================================");
        System.out.println("      Welcome to the Lever and Lamp Game   ");
        System.out.println("===========================================");
        System.out.println();
        System.out.println("How to Play:");
        System.out.println("1. The game consists of 5 levers and 5 lamps.");
        System.out.println("2. Each lever, when pulled, will affect 1 to 3 lamps.");
        System.out.println("   The effect could be turning a lamp ON or OFF.");
        System.out.println("3. Your goal is to turn on ALL the lamps to win the game.");
        System.out.println();
        System.out.println("Instructions:");
        System.out.println("1. You can pull a lever by selecting a number between 1 to 5.");
        System.out.println("   Each lever affects a specific set of lamps:");
        System.out.println("   - Some levers may turn ON a lamp.");
        System.out.println("   - Some levers may toggle a lamp that is already ON, turning it OFF.");
        System.out.println("2. Pay attention to the effect of each lever.");
        System.out.println("   Figure out which combination of lever pulls will turn on all the lamps.");
        System.out.println("3. If you want to give up, input '0' to quit the game.");
        System.out.println();
        System.out.println("Winning Condition:");
        System.out.println(" - You win the game when all 5 lamps are turned on at the same time.");
        System.out.println();
        System.out.println("Example:");
        System.out.println(" - Pulling Lever 1 might turn on Lamp 1 and Lamp 3.");
        System.out.println(" - Pulling Lever 2 might turn on Lamp 2 but turn off Lamp 1.");
        System.out.println();
        System.out.println("Good luck! Try to light up all the lamps!");
        System.out.println();
        waitForEnter();
    }
}