package core;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Integer> items;

    public Inventory() {
        items = new HashMap<>();
    }

    // Ajoute un item à l'inventaire
    public void addItem(String item, int amount) {
        items.put(item, items.getOrDefault(item, 0) + amount);
    }

    // Retire un item de l'inventaire
    public void removeItem(String item) {
        if (items.containsKey(item) && items.get(item) > 1) {
            items.put(item, items.get(item) - 1);
        } else {
            items.remove(item);
        }
    }

    // Retourne la quantité d'un item spécifique
    public int getItemCount(String item) {
        return items.getOrDefault(item, 0);
    }

    // Affiche l'inventaire
    public String getInventoryString() {
        StringBuilder inventory = new StringBuilder();
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            inventory.append(entry.getKey()).append(", ").append(entry.getValue()).append("<br>");
        }
        return "<html>" + inventory.toString() + "</html>";
    }
}
