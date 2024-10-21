package us.takiyo.extensions;

import us.takiyo.RestaurantSim;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Order {
    private int tempIndex;
    private Customer customer;
    private Food recipe;
    private int totalReward;
    private int time;

    public Order(Customer customer, Food recipe, int totalReward, int time) {
        this.customer = customer;
        this.recipe = recipe;
        this.totalReward = totalReward;
        this.time = time;
    }

    public int decrementTime() {
        return --this.time;
    }

    public String getName() {
        return this.recipe.getName();
    }

    public String getIngredients() {
        return this.recipe.getIngredients().stream().map(Ingredient::getName).collect(Collectors.joining(", "));
    }

    public String bake(RestaurantSim master) {
        TakiyoList<Inventory> inventory = master.getInventory();
        TakiyoList<Ingredient> ingredients = this.recipe.getIngredients();
        Map<String, Integer> parsedIngredients = new HashMap<>();

        Map<String, Integer> missingIngredients = new HashMap<>();

        for (Ingredient ingredient : ingredients) {
            parsedIngredients.put(ingredient.getName(), parsedIngredients.getOrDefault(ingredient.getName(), 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : parsedIngredients.entrySet()) {
            String ingredientName = entry.getKey();
            int requiredQuantity = entry.getValue();

            Inventory inv = inventory.stream()
                    .filter(i -> i.getItemName().equals(ingredientName))
                    .findFirst().orElse(null);

            if (inv == null || inv.getQuantity() < requiredQuantity) {
                missingIngredients.put(ingredientName, requiredQuantity - (inv != null ? inv.getQuantity() : 0));
            }
        }

        if (!missingIngredients.isEmpty()) {
            return "Not enough ingredients: " + missingIngredients.entrySet().stream()
                    .map(entry -> entry.getKey() + " (missing " + entry.getValue() + ")")
                    .collect(Collectors.joining(", "));
        }

        for (Map.Entry<String, Integer> entry : parsedIngredients.entrySet()) {
            String ingredientName = entry.getKey();
            int quantityToUse = entry.getValue();
            inventory.stream()
                    .filter(i -> i.getItemName().equals(ingredientName))
                    .findFirst().ifPresent(inv -> inv.setQuantity(inv.getQuantity() - quantityToUse));
        }

        master.setScore(master.getScore() + this.totalReward);
        return "Baked!";
    }

    public String haveEnoughIngredients(RestaurantSim master) {
        TakiyoList<Inventory> inventory = master.getInventory();
        TakiyoList<Ingredient> ingredients = this.recipe.getIngredients();
        Map<String, Integer> parsedIngredients = new HashMap<>();
        TakiyoList<Ingredient> have = new TakiyoList<Ingredient>();

        for (Ingredient ingredient : ingredients)
            if (parsedIngredients.containsKey(ingredient.getName()))
                parsedIngredients.put(ingredient.getName(), parsedIngredients.get(ingredient.getName()) + 1);
            else
                parsedIngredients.put(ingredient.getName(), 1);

        for (Inventory inv : inventory) {
            if (parsedIngredients.containsKey(inv.getItemName())) {
                if (parsedIngredients.get(inv.getItemName()) <= inv.getQuantity())
                    have.add(new Ingredient(inv.getItemName()));
            }
        }

        return have.stream().map(Ingredient::getName).collect(Collectors.joining(", "));
    }

    public String getCustomerName() {
        return this.customer.getName();
    }

    public int getTotalReward() {
        return this.totalReward;
    }

    public int getTime() {
        return this.time;
    }

    public Order(String data, RestaurantSim master) {
        String[] datas = data.split("#");
        try {
            String customerName = datas[1];
            String recipeName = datas[2];

            customer = master.getCustomers().get(Customer::getName, customerName);
            recipe = master.getRecipes().get(Food::getName, recipeName);
            totalReward = Integer.parseInt(datas[3]);
            time = Integer.parseInt(datas[4]);
        } catch (Exception ignored) {
            System.out.println("Failed to parse");
        }
    }

    public void setTempIndex(int index) {
        tempIndex = index;
    }

    public String getSaveData() {
        return String.format("%d#%s#%s#%d#%d", tempIndex, this.customer.getName(), this.recipe.getName(), this.totalReward, this.time);
    }

    public String getSaveData(int number) {
        return String.format("%d#%s#%s#%d#%d", number, this.customer.getName(), this.recipe.getName(), this.totalReward, this.time);
    }
}
