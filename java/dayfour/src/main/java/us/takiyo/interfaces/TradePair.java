package us.takiyo.interfaces;

public class TradePair {
    public String itemType;
    public int quantity;
    public int moneyReward = 100;
    public boolean bought = false;

    public TradePair(String itemType, int quantity) {
        this.itemType = itemType;
        this.quantity = quantity;
    }

    public TradePair(String itemType, int quantity, int moneyReward) {
        this.itemType = itemType;
        this.quantity = quantity;
        this.moneyReward = moneyReward;
    }
}
