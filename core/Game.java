package core;

import java.util.Calendar;
import java.util.HashMap;

import javax.swing.Timer;

import static core.Const.BABY_SITTING;
import static core.Const.DOG_SITTING;
import static core.Const.NIGHT_GUARD;
import static core.Const.RESTAURANT;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class Game {
    private int _lastUpdate;
    private Player _player;
    private Timer _timeClock;
    private Calendar _calendar = Calendar.getInstance();
    private HashMap<String, Shop> _shoppingMall;
    private HashMap<String, Activity> _jobs;

    public Game(){
        _player = new Player();
        _calendar.set(2077, 0, 1, 6, 0, 0);
        
        // shops init
        _shoppingMall = new HashMap<>();
        _shoppingMall.put(RESTAURANT, new Shop(RESTAURANT));
        Shop shop = _shoppingMall.get(RESTAURANT);
        shop.addITem(new Food("Bread", 2, 2, 1));
        shop.addITem(new Food("Fruit", 3, 7, 1));
        shop.addITem(new Food("Pizza", 12, 19, 1));
        shop.addITem(new Food("Bolognese", 15, 30, 1));

        // jobs init
        _jobs = new HashMap<>();
        _jobs.put(DOG_SITTING, new Activity("DogSitting 1h 10$", 1, 0, 10, 9, 0, 0, 21));
        _jobs.put(BABY_SITTING, new Activity("BabySitting 2h 30$", 2, 0, 30, 18, 0, 6, 2));
        _jobs.put(NIGHT_GUARD, new Activity("Security Guard 6h 80$", 6, 0, 0, 54, 0, 0, 6));
        
        // 1s irl = 1m in game 
        _timeClock = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                tictac();
            }
        });
    }

    public void startClocks(){
        _timeClock.start();
    }

    public void tictac(){
        _calendar.add(Calendar.MINUTE, 1);
        _lastUpdate++;
        if (_lastUpdate >= 60) {
            _player.addExhaustion(1);
            _player.addHunger(1);
            _lastUpdate = 0;
        }
        if (_player.getExhaust()>=100) {
            this.sleep();
        }
    }

    public void sleep(){
        _calendar.add(Calendar.HOUR, 7);
        _player.sleep();
        _player.addHunger(20);
        _player.addExhaustion(20);
        _lastUpdate = 0;
    }

    public String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateTimeString = dateFormat.format(_calendar.getTime());
        
        return dateTimeString;
    }

    public String getTime(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String timeString = timeFormat.format(_calendar.getTime());
        return timeString;
    }

    public Player getPlayer(){return _player;}
    public Calendar getCalendar(){return _calendar;}
    public HashMap<String, Shop> getAllShop(){return _shoppingMall;}
    public Shop getShop(String name){return _shoppingMall.get(name);}
    public HashMap<String, Activity> getAllJobs(){return _jobs;}
    public Activity getJob(String name){return _jobs.get(name);}
}