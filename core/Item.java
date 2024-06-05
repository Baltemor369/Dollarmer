package core;

public class Item {
    private String _name;
    private int _price;

    public Item(String name, int price){
        _name = name;
        _price = price;
    }

    public String getName(){return _name;}
    public int getPrice(){return _price;}
    public void valueVariation(int amount){_price += amount;}
}
