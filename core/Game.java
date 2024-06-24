package core;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import static core.Const.RESTAURANT;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class Game {
    private Player _player;
    private Timer _exhaustClock, _timeClock;
    private Calendar _calendar = Calendar.getInstance();
    private Map<String, Shop> _shoppingMall;

    public Game(){
        _player = new Player();
        _calendar.set(2077, 0, 1, 6, 0, 0);
        
        _shoppingMall = new HashMap<>();

        _shoppingMall.put(RESTAURANT, new Shop(RESTAURANT));
        Shop shop = _shoppingMall.get(RESTAURANT);
        shop.addITem(new Food("Bread", 2, 2));
        shop.addITem(new Food("Fruit", 3, 7));
        shop.addITem(new Food("Pizza", 12, 19));
        shop.addITem(new Food("Bolognese", 15, 30));

        // every minues : player stats update
        _exhaustClock = new Timer(60000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                _player.addExhaustion(1);
                _player.addHungry(1);
            }
        });
        
        // 1s irl = 1m in game 
        _timeClock = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                tictac();
                
            }
        });
    }

    public void startClocks(){
        _exhaustClock.start();
        _timeClock.start();
    }

    public void tictac(){
        _calendar.add(Calendar.MINUTE, 1);

        if (_calendar.get(Calendar.MINUTE)==0) {
            _player.addExhaustion(1);
        }
        if (_player.getExhaust()>=100) {
            this.sleep();
            _player.addExhaustion(20);
        }
    }

    public void sleep(){
        _calendar.add(Calendar.HOUR, 7);
        _player.sleep();
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
    public Shop getShop(String name){return _shoppingMall.get(name);}
}