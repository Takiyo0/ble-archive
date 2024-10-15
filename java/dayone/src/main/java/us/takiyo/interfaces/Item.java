package us.takiyo.interfaces;

import us.takiyo.Utils;

public class Item {
    public String Id;
    public String Name;

    public Item(String name) {
        this.Id = "PR" + Utils.generateNumber(101, 999);
        this.Name = name;
    }

    public Item(String id, String name) {
        this.Id = id;
        this.Name = name;
    }
}
