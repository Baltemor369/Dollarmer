package core;

public class Item {
    private String _name;
    private int _price, _count;

    public Item(String name, int price, int count){
        _name = name;
        _price = price;
        _count = count;
    }

    public String getName(){return _name;}
    public int getPrice(){return _price;}
    public int getCount(){return _count;}
    public void inflation(int amount){_price += amount;}
    public void addAmount(int amount){_count += amount;}
    public void removeAmount(int amount){_count -= amount;}
}
