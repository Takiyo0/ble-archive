package us.takiyo.childs.output;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import us.takiyo.RestaurantSim;
import us.takiyo.extensions.Customer;
import us.takiyo.extensions.Food;
import us.takiyo.extensions.Order;
import us.takiyo.extensions.TakiyoList;
import us.takiyo.utils.Utils;

// kitchen means backend process
public class RestaurantKitchen implements Runnable {
    private final RestaurantSim master;
    private final BufferedReader in;
    private final PrintWriter out;

    public RestaurantKitchen(RestaurantSim restaurantSim, BufferedReader in, PrintWriter out) {
        this.master = restaurantSim;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        this.printHome();
        try {
            while (true) {
                tick();
                Thread.sleep(1000);
            }
        } catch (InterruptedException | IllegalThreadStateException e) {
//            throw new RuntimeException(e);
        }
    }

    private void tick() {
        TakiyoList<Order> orders = master.getOrders();
        TakiyoList<String> saveData = new TakiyoList<>();

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.decrementTime() <= 0) orders.remove(order);
            else saveData.add(order.getSaveData(i));
        }
        if (orders.isEmpty() || (orders.size() == ThreadLocalRandom.current().nextInt(1, 3) && ThreadLocalRandom.current().nextBoolean())) {
            Order[] newOrders = generateOrder(ThreadLocalRandom.current().nextInt(1, 3));
            int prevSize = orders.size();
            orders.addAll(Arrays.asList(newOrders));
            for (int i = prevSize; i < orders.size(); i++)
                saveData.add(orders.get(i).getSaveData(i));
        }

        master.saveEverything();
    }

    private Order[] generateOrder(int size) {
        Order[] orders = new Order[size];
        for (int i = 0; i < size; i++) {
            Customer customer = master.getCustomers().get(ThreadLocalRandom.current().nextInt(master.getCustomers().size()));
            customer.setVip(ThreadLocalRandom.current().nextBoolean());
            Food recipe = master.getRecipes().get(ThreadLocalRandom.current().nextInt(master.getRecipes().size()));
            int totalReward = 5 + (recipe.ingredientsLength() * 2) + (customer.isVip() ? 10 : 0);
            int time = ThreadLocalRandom.current().nextInt(1, 21) + 15;
            orders[i] = new Order(customer, recipe, totalReward, time);
        }
        return orders;
    }

    private void printHome() {
        try {
            Utils.clearTerminal();
            System.out.println("  \n  /$$   /$$                                        /$$   /$$ /$$   /$$               /$$                          \r\n | $$  /$$/                                       | $$  /$$/|__/  | $$              | $$                          \r\n | $$ /$$/   /$$$$$$  /$$$$$$  /$$$$$$$$ /$$   /$$| $$ /$$/  /$$ /$$$$$$    /$$$$$$$| $$$$$$$   /$$$$$$  /$$$$$$$ \r\n | $$$$$/   /$$__  $$|____  $$|____ /$$/| $$  | $$| $$$$$/  | $$|_  $$_/   /$$_____/| $$__  $$ /$$__  $$| $$__  $$\r\n | $$  $$  | $$  \\__/ /$$$$$$$   /$$$$/ | $$  | $$| $$  $$  | $$  | $$    | $$      | $$  \\ $$| $$$$$$$$| $$  \\ $$\r\n | $$\\  $$ | $$      /$$__  $$  /$$__/  | $$  | $$| $$\\  $$ | $$  | $$ /$$| $$      | $$  | $$| $$_____/| $$  | $$\r\n | $$ \\  $$| $$     |  $$$$$$$ /$$$$$$$$|  $$$$$$$| $$ \\  $$| $$  |  $$$$/|  $$$$$$$| $$  | $$|  $$$$$$$| $$  | $$\r\n |__/  \\__/|__/      \\_______/|________/ \\____  $$|__/  \\__/|__/   \\___/   \\_______/|__/  |__/ \\_______/|__/  |__/\r\n                                         /$$  | $$                                                                \r\n                                        |  $$$$$$/                                                                \r\n                                         \\______/                                                                 \r\n \r\n ");
            System.out.println("Please access the other tab to control the game.");
//                String target = new Scanner(System.in).nextLine();
//                if (master.isStart()) return;
//                if (Objects.equals(target, "halo")) {
//                    out.println("start#%true");
//                    this.synchronize();
//                    master.setStart(true);
//                    break;
//                }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void synchronize() throws InterruptedException {
        int current = LocalDateTime.now().getNano();
        Thread.sleep(1000 - (current / 1_000_000));
    }
}
