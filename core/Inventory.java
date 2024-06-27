package core;

import java.util.HashMap;
import java.util.Map;

// The Inventory class represents a collection of items.
public class Inventory {
    // A map to store items and their corresponding quantities.
    private Map<Item, Integer> items;

    // Constructor for the Inventory class. Initializes the items map.
    public Inventory() {
        items = new HashMap<Item, Integer>();
    }

    // Method to add an item to the inventory. If the item already exists, its quantity is increased.
    public void addItem(Item item, int amount) {
        items.put(item, items.getOrDefault(item, 0) + amount);
    }

    // Method to remove an item from the inventory. If the quantity of the item is more than amount, it decreases the quantity by amount. Otherwise, it removes the item.
    // Return the quantity of Item removed.
    public int removeItem(Item item, int amount) {
        if (items.containsKey(item)) {
            int currentAmount = items.get(item);
            if (currentAmount > amount) {
                items.put(item, currentAmount - amount);
                return amount;
            } else {
                items.remove(item);
                return currentAmount;
            }
        }
        return 0;
    }

    // Method to get the quantity of a specific item in the inventory.
    public int getItemCount(Item item) {
        return items.getOrDefault(item, 0);
    }

    // Method to get a string representation of the inventory. Each item and its quantity are appended to the string.
    public String getInventoryString() {
        StringBuilder inventory = new StringBuilder();
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            inventory.append(entry.getKey()).append(", ").append(entry.getValue()).append("<br>");
        }
        return "<html>" + inventory.toString() + "</html>";
    }
}