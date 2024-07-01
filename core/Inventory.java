package core;

import java.util.HashMap;

// The Inventory class represents a collection of items.
public class Inventory {
    // A map to store items and their corresponding quantities.
    private HashMap<String, Item> items;

    // Constructor for the Inventory class. Initializes the items map.
    public Inventory() {
        items = new HashMap<String, Item>();
    }

    public String getInfo(){
        String text = "<html><ul>";
        for(Item item : items.values()) {
            text += "<li>" + item.getName() + ": " + item.getPrice() + "</li>";
        }
        text += "</ul></html>";
        return text;
    }
    public Item getItem(String key) {return items.get(key);}
    public HashMap<String, Item> getAllItem() {return items;}
    public int getItemCount(String key) {
        if (items.containsKey(key)) {
            return items.get(key).getCount();
        }
        return 0;
    }

    // Method to add an item to the inventory. If the item already exists, its quantity is increased.
    public void addItem(Item item) {
        if (!items.containsKey(item.getName())) {
            items.put(item.getName(), item);
        }else{
            items.get(item.getName()).addAmount(item.getCount());
        }
    }

    // Method to remove an item from the inventory. If the quantity of the item is more than amount, it decreases the quantity by amount. Otherwise, it removes the item.
    // Return the quantity of Item removed.
    public int removeItem(Item item, int amount) {
        if (items.containsKey(item.getName())) {
            int currentAmount = getItemCount(item.getName());
            if (currentAmount > amount) {
                items.get(item.getName()).removeAmount(amount);;
                return amount;
            } else {
                items.remove(item.getName());
                return currentAmount;
            }
        }
        return 0;
    }    
}