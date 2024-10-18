package us.takiyo.interfaces;

public class Money {
    public String item;
    public int costs = 0;

    public Money(String item, int costs) {
        this.item = item;
        this.costs = costs;
    }
}