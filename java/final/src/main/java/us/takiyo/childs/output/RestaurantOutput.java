package us.takiyo.childs.output;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import us.takiyo.RestaurantSim;
import us.takiyo.extensions.Inventory;
import us.takiyo.extensions.Order;
import us.takiyo.extensions.TakiyoList;
import us.takiyo.utils.Utils;

public class RestaurantOutput implements Runnable {
    private final RestaurantSim master;
    private final BufferedReader in;
    private final PrintWriter out;

    public RestaurantOutput(RestaurantSim restaurantSim, BufferedReader in, PrintWriter out) {
        this.master = restaurantSim;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        new Thread(this::listenForEnter).start();
        try {
            this.Synchronize();
            do {
                if (master.isStart()) {
                    Utils.clearTerminal();
                    TakiyoList<Order> orders = master.getOrders();
                    TakiyoList<Inventory> inventory = master.getInventory();
                    int score = master.getScore();

                    System.out.printf("%s's Restaurant\n\n", master.getOwnerName());

                    System.out.println("╔════╦════════════╦══════════════════════╦════════╦════════╗");
                    System.out.printf("║ %-3s║ %-11s║ %-21s║ %-7s║ %-7s║\n", "No.", "Customer", "Order", "Reward", "Time");
                    System.out.println("╠════╬════════════╬══════════════════════╬════════╬════════╣");

                    for (int i = 0; i < orders.size(); i++) {
                        Order order = orders.get(i);
                        System.out.printf("║ %-3d║ %-11s║ %-21s║ $%-6d║ %-7s║\n",
                                i + 1,
                                order.getCustomerName(),
                                order.getName(),
                                order.getTotalReward(),
                                order.getTime() + "s");
                    }

                    System.out.println("╚════╩════════════╩══════════════════════╩════════╩════════╝");

                    System.out.println("\n╔════════════════════════════════╗");
                    System.out.println("║     Current Inventory          ║");
                    System.out.println("╠══════════════╦═════════════════╣");
                    System.out.printf("║ %-13s║ %-16s║\n", "Ingredient", "Quantity");
                    System.out.println("╠══════════════╬═════════════════╣");

                    for (Inventory inv : inventory) {
                        System.out.printf("║ %-13s║ %-16d║\n", inv.getItemName(), inv.getQuantity());
                    }

                    System.out.println("╚══════════════╩═════════════════╝");

                    System.out.println("\n╔════════════════════════════════╗");
                    System.out.println("║         Game Stats             ║");
                    System.out.println("╚════════════════════════════════╝");
                    System.out.printf("Current Score: $%d\n\n", score);

                    System.out.println("Go to the other tab to manage inventory and orders!");
                    Thread.sleep(1200);
                }
            } while (true);
        } catch (Exception ignored) {
        }
    }


    private void listenForEnter() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty() && master.isStart()) {
                    out.println("start#%false");
                    master.setStart(false);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void Synchronize() {
        try {
            int time = LocalDateTime.now().getNano();
            int nextSecond = 1000 - (time / 1_000_000);
            Thread.sleep(nextSecond);
        } catch (Exception ignored) {
        }
    }
}
