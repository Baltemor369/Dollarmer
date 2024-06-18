package core;

import java.util.Calendar;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class Game {
    private Player _player;
    private Timer _afkMoneyClock, _timeClock;
    private Calendar _calendar = Calendar.getInstance();

    public Game(){
        _player = new Player();
        _calendar.set(2024, 0, 1, 6, 0, 0);

        // every 20s player earn 1$
        _afkMoneyClock = new Timer(20000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                _player.earnMoney(1);
            }
        });
        
        // 1s irl = 1m in game 
        _timeClock = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                _calendar.add(Calendar.MINUTE, 1);
                
            }
        });
    }

    public void startClocks(){
        _afkMoneyClock.start();
        _timeClock.start();
    }

    public void sleep(){
        if (_calendar.get(Calendar.HOUR_OF_DAY) >= 21) {
            _calendar.add(Calendar.DATE, 1);
        }
        
        _calendar.set(Calendar.HOUR_OF_DAY, 6);
        _calendar.set(Calendar.MINUTE, 0);
        _calendar.set(Calendar.SECOND, 0);

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

    public Player getPlayer(){
        return _player;
    }

    public Calendar getCalendar(){
        return _calendar;
    }
}
