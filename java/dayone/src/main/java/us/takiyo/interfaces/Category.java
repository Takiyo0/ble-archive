package us.takiyo.interfaces;

import us.takiyo.Utils;

import java.util.ArrayList;
import java.util.List;

public class Category {
    public String Id;
    public String Name;
    public List<Item> Items = new ArrayList<>();

    public Category() {
        this.Id = "CA" + Utils.generateNumber(101, 999);
    }
}
