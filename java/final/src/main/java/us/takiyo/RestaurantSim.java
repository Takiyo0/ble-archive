package us.takiyo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import us.takiyo.childs.input.RestaurantInput;
import us.takiyo.childs.input.SocketInput;
import us.takiyo.childs.output.RestaurantKitchen;
import us.takiyo.childs.output.RestaurantOutput;
import us.takiyo.childs.output.SocketOutput;
import us.takiyo.elements.HowToPlay;
import us.takiyo.extensions.*;
import us.takiyo.foods.BaconBurger;
import us.takiyo.foods.CheeseBurger;
import us.takiyo.foods.DeluxeBurger;
import us.takiyo.foods.VeggieBurger;
import us.takiyo.ingredients.Bacon;
import us.takiyo.ingredients.Bun;
import us.takiyo.ingredients.Cheese;
import us.takiyo.ingredients.Lettuce;
import us.takiyo.ingredients.Onion;
import us.takiyo.ingredients.Patty;
import us.takiyo.ingredients.Pickle;
import us.takiyo.ingredients.Tomato;
import us.takiyo.managers.FileManager;
import us.takiyo.utils.Utils;

public class RestaurantSim {
    public enum ProcessType {
        Server,
        Client
    }

    private TakiyoList<Ingredient> ingredients = new TakiyoList<Ingredient>();
    private TakiyoList<Food> foods = new TakiyoList<Food>();
    private TakiyoList<Customer> customers = new TakiyoList<Customer>();
    private final BlockingQueue<SocketData> socketQueue = new LinkedBlockingQueue<>();


    private TakiyoList<Inventory> inventory = new TakiyoList<Inventory>();
    private TakiyoList<Order> orders = new TakiyoList<Order>();
    private int score = 0;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private ProcessType processType;
    private String ownersName;
    private boolean start;


    public static void main(String[] args) {
        new RestaurantSim();
    }

    private void loadIngredients() {
        Ingredient[] ingredients = {new Bacon(), new Bun(), new Cheese(), new Lettuce(), new Onion(), new Patty(), new Pickle(), new Tomato()};
        this.ingredients.addAll(Arrays.asList(ingredients));
    }

    public synchronized BlockingQueue<SocketData> getSocketQueue() {
        return this.socketQueue;
    }

    private void loadFoods() {
        Food[] foods = {new BaconBurger(), new CheeseBurger(), new DeluxeBurger(), new VeggieBurger()};
        this.foods.addAll(Arrays.asList(foods));
    }

    private void loadCustomers() {
        String[] names = {"alice", "bob", "charlie", "Diana", "Eve", "Frank", "Grace", "Hank"};
        for (String name : names) {
            Customer customer = new Customer(name);
            this.customers.add(customer);
        }
    }

    private void loadInventory() {
        if (this.inventory.size() > 2) return;
        Ingredient[] ingredients = {new Bacon(), new Bun(), new Cheese(), new Lettuce(), new Onion(), new Patty(), new Pickle(), new Tomato()};
        Inventory[] inventory = new Inventory[ingredients.length];
        for (int i = 0; i < ingredients.length; i++) {
            inventory[i] = new Inventory(ingredients[i].getName(), 20);
        }
        this.inventory.addAll(Arrays.asList(inventory));
    }

    public synchronized TakiyoList<Customer> getCustomers() {
        return this.customers;
    }

    public synchronized TakiyoList<Food> getRecipes() {
        return this.foods;
    }

    public synchronized TakiyoList<Order> getOrders() {
        return this.orders;
    }

    public synchronized int getScore() {
        return score;
    }

    public synchronized void setScore(int score) {
        this.score = score;
        saveEverything();
    }

    public synchronized void restockIngredients() {
        for (Inventory inventory : this.inventory) {
            inventory.setQuantity(inventory.getQuantity() + 10);
        }

        this.setScore(this.getScore() - 10);
        saveEverything();
    }

    public synchronized void saveEverything() {
        FileManager restaurant = new FileManager("restaurant.txt"); // No#CustomerName#RecipeName#TotalReward#Time
        FileManager inventory = new FileManager("inventory.txt"); // IngredientName#Quantity
        FileManager score = new FileManager("score.txt"); // Score

        String[] orders = new String[this.orders.size()];
        for (int i = 0; i < this.orders.size(); i++)
            orders[i] = this.orders.get(i).getSaveData(i + 1);
        restaurant.write(orders);

        String[] inventoryData = new String[this.inventory.size()];
        for (int i = 0; i < this.inventory.size(); i++)
            inventoryData[i] = this.inventory.get(i).getSaveData();
        inventory.write(inventoryData);

        score.write(String.valueOf(this.getScore()));
    }

    public synchronized void loadEverything() {
        FileManager restaurant = new FileManager("restaurant.txt");
        FileManager inventory = new FileManager("inventory.txt");
        FileManager score = new FileManager("score.txt");

        Vector<String> orders = restaurant.read();
        if (!orders.isEmpty()) {
            Order[] parsedOrders = new Order[orders.size()];
            for (int i = 0; i < orders.size(); i++)
                parsedOrders[i] = new Order(orders.get(i), this);
            this.orders.addAll(Arrays.asList(parsedOrders));
        }

        Vector<String> inventoryData = inventory.read();
        if (inventoryData.size() > 2) {
            Inventory[] parsedInventory = new Inventory[inventoryData.size()];
            for (int i = 0; i < inventoryData.size(); i++)
                parsedInventory[i] = new Inventory(inventoryData.get(i));
            this.inventory.addAll(Arrays.asList(parsedInventory));
        }

        this.setScore(Utils.parseInt(score.readString()));
    }

    public synchronized TakiyoList<Inventory> getInventory() {
        return inventory;
    }

    private void determineSocket() {
        Socket socket = new Socket();
        try {
            SocketAddress address = new InetSocketAddress(4569);
            socket.connect(address, 1000);

            System.out.println("[CLIENT->Input] Connected to output successfully!");
            processType = ProcessType.Client;

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            this.startThreading();
        } catch (IOException ignored) {
            this.startSocketServer();
        }
    }

    private void startSocketServer() {
        try (ServerSocket socket = new ServerSocket(4569)) {
            System.out.println("[MASTER->Output] Server started");
            HowToPlay.printWaitingForController();
            Socket client = socket.accept();

            System.out.println("[MASTER->Output] Input has connected to output");
            HowToPlay.printControllerConnected();
            processType = ProcessType.Server;

            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            this.startThreading();
        } catch (Exception ignored) {
        }
    }

    private void startThreading() {
        TakiyoList<Runnable> threads = new TakiyoList<Runnable>();
        if (processType == ProcessType.Server)
            threads.addAll(Arrays.asList(new Runnable[]{new RestaurantOutput(this, in, out), new RestaurantKitchen(this, in, out), new SocketOutput(this, in, out)}));
        else
            threads.addAll(Arrays.asList(new Runnable[]{new RestaurantInput(this, in, out), new SocketInput(this, in, out)}));

        for (Runnable run : threads) new Thread(run).start();
    }

    public synchronized String getOwnerName() {
        return ownersName;
    }

    public synchronized void setOwnerName(String newOwnerName) {
        ownersName = newOwnerName;
    }

    public RestaurantSim() {
        this.loadIngredients();
        this.loadFoods();
        this.loadCustomers();
        this.loadEverything();
        this.loadInventory();
        this.determineSocket();
    }

    public synchronized boolean isStart() {
        return start;
    }

    public synchronized void setStart(boolean start) {
        this.start = start;
    }
}