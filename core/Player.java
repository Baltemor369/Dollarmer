package core;

public class Player{
    private int _money;
    // health stats
    private int _exhaust, _hungry, _happiness, _mentalHealth;
    // skills stats
    private int _technical, _artistic, _communication, _science;
    private Inventory _invent = new Inventory();
    
    // default constructor
    public Player(){
        this._money = 0;
        this._exhaust = 0; // %
        this._hungry = 0; // %
        this._happiness = 0; // %
        this._mentalHealth = 100; // %
        this._technical = 1;
        this._artistic = 1;
        this._communication = 1;
        this._science = 1;
    }

    // constructor
    public Player(int m, int l, int xp, int xpMax, int exhaust){
        this._money = m;
        this._exhaust = exhaust;
    }

    public String getInfo() {
        return String.format("<html>Stats : <br>Money: %d <br/>Hapiness: %d%% <br/>Hungry: %d%% <br/>Mental health: %d%% <br/>Exhaust: %d%% <br><br>Skills : <br>Technical : %d <br> Artistic : %d <br> Communication : %d <br> Science : %d </html>", _money, _happiness, _hungry, _mentalHealth, _exhaust, _technical, _artistic, _communication, _science);
    }

    public int getMoney(){return _money;}
    public int getExhaust(){return _exhaust;}
    public int getHungry() {return _hungry;}
    public int getHappiness() {return _happiness;}
    public int getMentalHealth() {return _mentalHealth;}
    public int getTechnical() {return _technical;}
    public int getArtistic() {return _artistic;}
    public int getCommunication() {return _communication;}
    public int getScience() {return _science;}
    public String getInventString() {return _invent.getInfo();}

    // money management
    public void addMoney(int amount){if (amount>0) {this._money += amount;}}
    public void removeMoney(int amount){if (0 < amount && amount <= _money) {this._money -= amount;}}
    public void buyItem(Item item){
        if (_money >= item.getPrice()*item.getCount()) {
            _money -= item.getPrice()*item.getCount();
            _invent.addItem(item);
        }
    }

    // inventory management
    public void addItem(Item item){_invent.addItem(item);}

    // exhaust management
    public void addExhaustion (int amount){
        _exhaust += amount;
        if (_exhaust >100) {
            _exhaust = 100;
        }
    }
    public void sleep(){
        _exhaust = 0;
    }

    // hungry management
    public void addHunger(int amount){
        _hungry += amount;
        if (_hungry>100) {
            _hungry = 100;
        }
    }
    public void removeHunger(int amount){
        _hungry -= amount;
        if (_hungry<0) {
            _hungry = 0;
        }
    }

    // mentalHealth management
    public void addMentalHealth(int amount){
        _mentalHealth += amount;
        if (_mentalHealth>100) {
            _mentalHealth = 100;
        }
    }
    public void removeMentalHealth(int amount){
        _mentalHealth -= amount;
        if (_mentalHealth<0) {
            _mentalHealth = 0;
        }
    }

    // hapiness management
    public void addHapiness(int amount){
        _happiness += amount;
        if (_happiness>100) {
            _happiness=100;
        }
    }
    public void removeHapiness(int amount){
        _happiness -= amount;
        if (_happiness<0) {
            _happiness=0;
        }
    }

    // skills management
    public void addSkill(String skill, int points){
        switch (skill) {
            case Const.TECHNICAL:
                _technical += points;
                break;
            case Const.ARTISTIC:
                _artistic += points;
                break;
            case Const.COMMUNICATION:
                _communication += points;
                break;
            case Const.SCIENCE:
                _science += points;
                break;
        
            default:
                break;
        }
    }
}
