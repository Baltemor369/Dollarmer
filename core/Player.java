package core;

public class Player{
    private int _money;
    private int _sleepy;
    private Inventory _invent = new Inventory();
    
    // default constructor
    public Player(){
        this._money = 0;
        this._sleepy = 0; // %
    }

    // constructor
    public Player(int m, int l, int xp, int xpMax, int sleepy){
        this._money = m;
        this._sleepy = sleepy;
    }

    public String getInfo() {
        return String.format("<html>Money: %d<br/>Exhaust: %d%% </html>", _money, _sleepy);
    }

    public int getMoney(){
        return _money;
    }
    
    public int getSleep(){
        return _sleepy;
    }

    public Inventory getInvent() {
        return _invent;
    }

    public void earnMoney(int amount){
        if (amount>0) {
            this._money += amount;
        }
    }
    public void spendMoney(int amount){
        if (0 < amount && amount <= _money) {
            this._money -= amount;
        }
    }

    public void addItem(String item){
        _invent.addItem(item, 1);
    }
    public void addItem(String item, int amount){
        _invent.addItem(item, amount);
    }

    public void addExhaustion (int amount){
        _sleepy += amount;
        if (_sleepy >100) {
            _sleepy = 100;
        }
    }

    public void sleep(){
        _sleepy = 0;
    }
}