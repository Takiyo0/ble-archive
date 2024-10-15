package us.takiyo.managers;

import us.takiyo.Utils;
import us.takiyo.interfaces.Category;
import us.takiyo.interfaces.Item;

import java.util.*;

public class ItemManager {
    public Map<String, Category> items = new HashMap<>();

    public ItemManager() {
    }

    public Category GetCategory(String id) {
        return this.items.get(id);
    }

    public void AddItem(String category, String itemName) {
        Item newItem = new Item("PR" + Utils.generateNumber(101, 999), itemName);
        Category cate = this.items.get(category) == null ? new Category() : this.items.get(category);
        cate.Items.add(newItem);
        this.items.put(category, cate);
    }

    public void DeleteCategory(String category) {
        this.items.remove(category);
    }

    public int GetItemCount(String category) {
        return this.items.get(category) == null ? 0 : this.items.get(category).Items.size();
    }

    public int RemoveItem(String category, String itemId) {
        Category cate = this.items.get(category);
        if (cate == null) {
            return 0;
        }
        Item targetItem = this.GetItem(cate.Items, itemId);
        if (targetItem == null) {
            return 0;
        }
        cate.Items.remove(targetItem);
        return 1;
    }

    public Item GetItem(List<Item> items, String id) {
        for (Item item : items) {
            if (Objects.equals(item.Id, id)) {
                return item;
            }
        }
        return null;
    }
}
