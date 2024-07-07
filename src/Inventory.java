package src;

import java.util.Collection;
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
            text += "<li>" + item.getName() + " "+ item.getPrice() +"$ : " + item.getCount() + "</li>";
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
    public HashMap<String, Food> getAllFood() {
        HashMap<String, Food> foodItems = new HashMap<String, Food>();
        for (String key : items.keySet()) {
            if (items.get(key) instanceof Food) {
                foodItems.put(items.get(key).getName(), (Food) items.get(key));
            }
        }
        return foodItems;
    }

    // Method to add an item to the inventory. If the item already exists, its quantity is increased.
    public void addItem(Item itemShop, int amount) {
        if (!items.containsKey(itemShop.getName())) {
            items.put(itemShop.getName(), itemShop);
        } else {
            items.get(itemShop.getName()).addAmount(amount);
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

    public Collection<Item> values(){
        return items.values();
    }
}