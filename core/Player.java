package core;

public class Player{
    private int _money;
    private int _xp;
    private int _xp_max;
    private int _level;
    private int _sleepy;
    private Inventory _invent = new Inventory();
    // default constructor
    public Player(){
        this._money = 0;
        this._level = 1;
        this._xp = 0;
        this._xp_max = 100;
        this._sleepy = 0; // %
    }

    // constructor
    public Player(int m, int l, int xp, int xpMax, int sleepy){
        this._money = m;
        this._level = l;
        this._xp = xp;
        this._xp_max = xpMax;
        this._sleepy = sleepy;
    }

    public String getInfo() {
        return String.format("<html>Money: %d<br/>Xp: %d/%d<br/>Level: %d</html>", _money, _xp, _xp_max, _level);
    }

    public int getMoney(){
        return _money;
    }
    
    public int getLevel(){
        return _level;
    }
    
    public int getXp(){
        return _xp;
    }
    
    public int getXpMax(){
        return _xp_max;
    }
    
    public int getSleep(){
        return _sleepy;
    }

    public Inventory getInvent() {
        return _invent;
    }

    public void gainXp(int amount){
        this._xp += amount;
        if (this._xp>=this._xp_max) {
            this._money += this._level*100;
            this._level++;
            this._xp -=this._xp_max;
            this._xp_max *= 2;
        }
    }

    public void earnMoney(int amount){
        this._money += amount;
    }

    public void addItem(String item){
        _invent.addItem(item, 1);
    }
    public void addItem(String item, int amount){
        _invent.addItem(item, amount);
    }
}