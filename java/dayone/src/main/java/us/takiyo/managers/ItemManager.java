package us.takiyo.managers;

import us.takiyo.Utils;
import us.takiyo.interfaces.Category;
import us.takiyo.interfaces.Item;

import java.util.*;

public class ItemManager {
    public Vector<Category> items = new Vector<>();

    public ItemManager() {
    }

    public Category GetCategory(String id) {
        for (Category category : this.items) {
            if (Objects.equals(category.Id, id)) {
                return category;
            }
        }
        return null;
    }

    public void AddItem(String category, String itemName) {
        Item newItem = new Item("PR" + Utils.generateNumber(101, 999), itemName);
        Category cate = this.GetCategory(category) == null ? new Category() : this.GetCategory(category);
        cate.Items.add(newItem);
        this.items.add(cate);
    }

    public void AddCategory(Category category) {
        this.items.add(category);
    }

    public void DeleteCategory(String category) {
        Category cate = this.GetCategory(category);
        if (cate != null) this.items.remove(cate);
    }

    public int GetItemCount(String category) {
        return this.GetCategory(category) == null ? 0 : this.GetCategory(category).Items.size();
    }

    public void RemoveItem(String category, String itemId) {
        Category cate = this.GetCategory(category);
        if (cate == null) {
            return;
        }
        Item targetItem = this.GetItem(cate.Items, itemId);
        if (targetItem == null) {
            return;
        }
        cate.Items.remove(targetItem);
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
