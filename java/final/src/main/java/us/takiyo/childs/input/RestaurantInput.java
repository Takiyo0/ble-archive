package us.takiyo.childs.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

import us.takiyo.RestaurantSim;
import us.takiyo.elements.HowToPlay;
import us.takiyo.extensions.SocketData;
import us.takiyo.utils.Utils;

public class RestaurantInput implements Runnable {
    private final RestaurantSim master;
    private final BufferedReader in;
    private final PrintWriter out;

    public RestaurantInput(RestaurantSim restaurantSim, BufferedReader in, PrintWriter out) {
        this.master = restaurantSim;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Utils.clearTerminal();
                System.out.println("  \n  /$$   /$$                                        /$$   /$$ /$$   /$$               /$$                          \r\n | $$  /$$/                                       | $$  /$$/|__/  | $$              | $$                          \r\n | $$ /$$/   /$$$$$$  /$$$$$$  /$$$$$$$$ /$$   /$$| $$ /$$/  /$$ /$$$$$$    /$$$$$$$| $$$$$$$   /$$$$$$  /$$$$$$$ \r\n | $$$$$/   /$$__  $$|____  $$|____ /$$/| $$  | $$| $$$$$/  | $$|_  $$_/   /$$_____/| $$__  $$ /$$__  $$| $$__  $$\r\n | $$  $$  | $$  \\__/ /$$$$$$$   /$$$$/ | $$  | $$| $$  $$  | $$  | $$    | $$      | $$  \\ $$| $$$$$$$$| $$  \\ $$\r\n | $$\\  $$ | $$      /$$__  $$  /$$__/  | $$  | $$| $$\\  $$ | $$  | $$ /$$| $$      | $$  | $$| $$_____/| $$  | $$\r\n | $$ \\  $$| $$     |  $$$$$$$ /$$$$$$$$|  $$$$$$$| $$ \\  $$| $$  |  $$$$/|  $$$$$$$| $$  | $$|  $$$$$$$| $$  | $$\r\n |__/  \\__/|__/      \\_______/|________/ \\____  $$|__/  \\__/|__/   \\___/   \\_______/|__/  |__/ \\_______/|__/  |__/\r\n                                         /$$  | $$                                                                \r\n                                        |  $$$$$$/                                                                \r\n                                         \\______/                                                                 \r\n \r\n ");

                System.out.print("Enter \"Start\" to start the game!\n> ");
                String target = new BufferedReader(new InputStreamReader(System.in)).readLine();
                if (Objects.equals(target, "Start")) {
                    System.out.print("Enter the restaurant's name\n> ");
                    String name = new BufferedReader(new InputStreamReader(System.in)).readLine();
                    out.println("start#%true#" + name);
                    this.synchronize();
                    master.setStart(true);
                } else continue;
                handleMenus();
            }
        } catch (Exception ignored) {
        }
    }

    private void handleMenus() {
        try {
            int state = 0;
            String selectedItem = "";
            while (true) {
                Utils.clearTerminal();
                System.out.println("  \n  /$$   /$$                                        /$$   /$$ /$$   /$$               /$$                          \r\n | $$  /$$/                                       | $$  /$$/|__/  | $$              | $$                          \r\n | $$ /$$/   /$$$$$$  /$$$$$$  /$$$$$$$$ /$$   /$$| $$ /$$/  /$$ /$$$$$$    /$$$$$$$| $$$$$$$   /$$$$$$  /$$$$$$$ \r\n | $$$$$/   /$$__  $$|____  $$|____ /$$/| $$  | $$| $$$$$/  | $$|_  $$_/   /$$_____/| $$__  $$ /$$__  $$| $$__  $$\r\n | $$  $$  | $$  \\__/ /$$$$$$$   /$$$$/ | $$  | $$| $$  $$  | $$  | $$    | $$      | $$  \\ $$| $$$$$$$$| $$  \\ $$\r\n | $$\\  $$ | $$      /$$__  $$  /$$__/  | $$  | $$| $$\\  $$ | $$  | $$ /$$| $$      | $$  | $$| $$_____/| $$  | $$\r\n | $$ \\  $$| $$     |  $$$$$$$ /$$$$$$$$|  $$$$$$$| $$ \\  $$| $$  |  $$$$/|  $$$$$$$| $$  | $$|  $$$$$$$| $$  | $$\r\n |__/  \\__/|__/      \\_______/|________/ \\____  $$|__/  \\__/|__/   \\___/   \\_______/|__/  |__/ \\_______/|__/  |__/\r\n                                         /$$  | $$                                                                \r\n                                        |  $$$$$$/                                                                \r\n                                         \\______/                                                                 \r\n \r\n ");
                switch (state) {
                    case 0: {
                        System.out.println("1. restock");
                        System.out.println("2. how to play");
                        System.out.println("3. end game");
                        System.out.println("type the options above or 'process 1' to process order 1, etc.");
                        System.out.print("> ");
                        String target = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        if (Utils.parseInt(target) != 0 && Utils.parseInt(target) <= 3) {
                            state = Utils.parseInt(target);
                            continue;
                        } else {
                            String[] datas = target.split(" ");
                            if (datas[0].equals("process") && Utils.parseInt(datas[1]) != 0) {
                                String sesId = Utils.getUUID();
                                SocketData response = sendMessageWithReply("processExists", datas[1], sesId);
                                if (response == null) {
                                    System.out.println("Error while communicating with server");
                                    Utils.waitForEnter();
                                    continue;
                                }
                                if (Objects.equals(response.getData(), "false")) {
                                    System.out.println("That order does not exist");
                                    Utils.waitForEnter();
                                    continue;
                                }
                                state = 4;
                                System.out.println(response.getData());
                                selectedItem = datas[1] + "##" + response.getData();
                                continue;
                            } else {
                                System.out.println("invalid input");
                                continue;
                            }
                        }
                    }

                    case 1: {
                        SocketData response = sendMessageWithReply("restock", "bla", Utils.getUUID());
                        if (response == null) {
                            System.out.println("Error while communicating with server");
                            Utils.waitForEnter();
                            continue;
                        }
                        System.out.println(response.getData());
                        Utils.waitForEnter();
                        selectedItem = "";
                        state = 0;
                        continue;
                    }

                    case 2: {
                        out.println("htp#%show");
                        HowToPlay.printHowToPlay();
                        Utils.waitForEnter();
                        out.println("htp#%nah");
                        state = 0;
                        selectedItem = "";
                        continue;
                    }

                    case 3: {
                        SocketData response = sendMessageWithReply("getScore", "bla", Utils.getUUID());
                        out.println("exit#%bla");
                        HowToPlay.printExit(response.getData());
                        System.exit(0);
                    }

                    case 4: {
                        String[] datas = selectedItem.split("##");
                        String orderId = datas[0];
                        String recipeName = datas[1];
                        String[] availableIngredients = datas[2].split(", ");
                        System.out.println("Type these ingredients to make: " + datas[1]);
                        System.out.print("> ");
                        String target = new BufferedReader(new InputStreamReader(System.in)).readLine();
                        String[] res = target.split(", ");

                        boolean allIngredientsInRecipe = true;

                        for (String ingredient : res) {
                            if (!recipeName.toLowerCase().contains(ingredient.toLowerCase())) {
                                allIngredientsInRecipe = false;
                                System.out.println("Ingredient " + ingredient + " is not part of the recipe name.");
                            }
                        }

                        if (allIngredientsInRecipe) {
                            boolean allIngredientsMatch = true;

                            for (String ingredient : res) {
                                if (!Arrays.asList(availableIngredients).contains(ingredient)) {
                                    allIngredientsMatch = false;
                                    System.out.println("Ingredient " + ingredient + " is not available.");
                                }
                            }

                            if (allIngredientsMatch) {
                                System.out.println("You have successfully provided all the correct ingredients.");
                                SocketData response = sendMessageWithReply("bake", orderId, Utils.getUUID());
                                if (response == null) {
                                    System.out.println("Error while communicating with server");
                                    Utils.waitForEnter();
                                    selectedItem = "";
                                    continue;
                                }
                                System.out.println(response.getData());
                                Utils.waitForEnter();
                                state = 0;
                            } else {
                                System.out.println("Some ingredients are missing or incorrect.");
                                Utils.waitForEnter();
                                state = 0;
                            }
                        } else {
                            System.out.println("Some ingredients do not match the recipe.");
                            Utils.waitForEnter();
                            state = 0;
                        }
                        selectedItem = "";
                    }
                }
            }
        } catch (IOException ignored) {
        }
    }

    private SocketData sendMessageWithReply(String op, String data, String sessionId) {
        try {
            this.out.println(op + "#%" + data + "#%" + sessionId);
            while (true) {
                SocketData d = master.getSocketQueue().poll();
//                System.out.println(d == null ? "nda ada" : d.getSessionId());
                if (d != null && Objects.equals(sessionId, d.getSessionId())) {
                    return d;
                }
            }
        } catch (Exception ignored) {
            return null;
        }
    }

    private void synchronize() {
        try {
            int test = LocalDateTime.now().getNano();
            Thread.sleep(1000 - (test / 1_000_000));
        } catch (Exception ignored) {
        }
    }
}
