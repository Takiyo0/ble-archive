package us.takiyo.controllers;

import us.takiyo.extensions.ResourceExtension;
import us.takiyo.interfaces.Factory;
import us.takiyo.interfaces.TradePair;
import us.takiyo.resources.Gold;
import us.takiyo.resources.Money;
import us.takiyo.resources.Stone;
import us.takiyo.resources.Wood;

import java.util.*;

public class Player {
    private int day = 1;
    List<ResourceExtension> resources = new ArrayList<>();
    Map<Integer, TradePair[]> tradePairs = new HashMap<>();
    List<Factory> factories = new ArrayList<>();
    String[] affectedResource = {"wood", "gold", "stone"};

    public Player() {
        resources.add(new Wood());
        resources.add(new Stone());
        resources.add(new Gold());
        resources.add(new Money());
    }

    public TradePair[] getTradePairs(int day) {
        return tradePairs.get(day);
    }

    public void addTradePairs(int day, TradePair[] pair) {
        tradePairs.put(day, pair);
    }

    public int getDay() {
        return day;
    }

    public void addFactory(Factory factory) {
        factories.add(factory);
    }

    public void onBuyFactory(Factory factory) {
        for (ResourceExtension resource : resources)
            if (Arrays.asList(affectedResource).contains(resource.getName()))
                resource.setAmount(resource.getAmount() - factory.getCosts());
    }

    public void printResources() {
        String reset = "\u001B[0m";
        String cyan = "\u001B[36m";
        String yellow = "\u001B[33m";
        String green = "\u001B[32m";

        System.out.print(cyan + """
                ╔═════════════════════════════╗
                ║        Your Resources       ║
                ╠═════════════╦═══════════════╣
                """ + reset);

        for (ResourceExtension resource : resources) {
            System.out.printf(cyan + "║" + yellow + " %-11s " + cyan + "║" + green + " %-13d " + cyan + "║\n" + reset,
                    resource.getName(), resource.getAmount());
        }

        System.out.println(cyan + "╚═════════════╩═══════════════╝" + reset);
    }


    public boolean isMoneyEnough(us.takiyo.interfaces.Money[] costs) {
        for (us.takiyo.interfaces.Money cost : costs) {
            ResourceExtension resource = this.getResource(cost.item);
            if (resource == null) return false;
            if (resource.getAmount() < cost.costs) return false;
        }
        return true;
    }

    public void useResource(us.takiyo.interfaces.Money[] costs) {
        for (us.takiyo.interfaces.Money cost : costs) {
            ResourceExtension resource = this.getResource(cost.item);
            if (resource == null) continue;
            resource.setAmount(resource.getAmount() + cost.costs);
        }
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public void onNextDay() {
        for (Factory factory : factories) {
            String type = factory.getType().toString().toLowerCase();
            for (ResourceExtension resource : resources)
                if (Objects.equals(resource.getName(), type))
                    resource.setAmount(resource.getAmount() + 3);
        }
        this.day++;
    }

    public ResourceExtension getResource(String name) {
        for (ResourceExtension r : resources)
            if (Objects.equals(r.getName(), name))
                return r;
        return null;
    }

    public ResourceExtension getResourceByIndex(int index) {
        return resources.get(index);
    }

    public int getResourceCount() {
        return resources.size();
    }

    public void setDay(int day) {
        this.day = day;
    }
}