package core;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    private String _name;
    private Map<String, Item> _storage;

    public Shop(String name) {
        _name = name;
        _storage = new HashMap<>();
    }

    public String getName(){return _name;}
    public Map<String, Item> getStorage() {return _storage;}
    public Item getItem(String key) {return _storage.get(key);}
    public void addITem(Item obj){
        _storage.put(obj.getName(), obj);
    }
}