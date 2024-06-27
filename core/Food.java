package core;

public class Food extends Item {
    private int _satiety;

    public Food(String name, int price, int satiety){
        super(name, price);
        _satiety = satiety;
    }

    public int getSatiety(){return _satiety;}
}