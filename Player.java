
public class Player{
    private int money;
    private int xp;
    private int xp_max;
    private int level;

    public Player(){
        this.money = 0;
        this.level = 1;
        this.xp = 0;
        this.xp_max = 100;

    }

    public Player(int m, int l, int xp, int xpMax){
        this.money = m;
        this.level = l;
        this.xp = xp;
        this.xp_max = xpMax;
    }

    public void displayInfo() {
        System.out.println("Player Info: ");
        System.out.println("Money: " + money);
        System.out.println("Xp: " + xp + "/" + xp_max);
        System.out.println("Level: " + level);
    }

    public int getMoney(){
        return money;
    }
    
    public int getLevel(){
        return level;
    }
    
    public int getXp(){
        return xp;
    }
    
    public int getXpMax(){
        return xp_max;
    }

    public void gainXp(int amount){
        this.xp += amount;
        if (this.xp>=this.xp_max) {
            this.level++;
            this.money += this.level*100;
            this.xp -=this.xp_max;
            this.xp_max *= 2;
        }
    }

    public void earnMoney(int amount){
        this.money += amount;
    }
}