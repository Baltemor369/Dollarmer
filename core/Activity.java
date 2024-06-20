package core;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import widgets.WButton;

import static core.Const.*;

public class Activity {
    String _name;
    // duration
    private int _hour, _min, _sec;
    // access job time
    private int _hourStart, _hourEnd;
    // 
    private int _salary, _exhaustion;
    // skills requirements
    private int _technical, _artistic, _communication, _science;

    public Activity(String name, int hour, int minute, int salary, int exhaustion, int startHour, int endHour,int technical,int artistic,int communication,int science) {
        this._name = name;
        this._hour = hour;
        this._min = minute;
        this._salary = salary;
        this._exhaustion = exhaustion;
        this._hourStart = startHour;
        this._hourEnd = endHour;
        this._technical = technical;
        this._artistic = artistic;
        this._communication = communication;
        this._science = science;
    }

    @Override
    public String toString() { return _name + " exhaust:" + _exhaustion + " schedules:" + _hourStart + "-" + _hourEnd; }

    public String getName() { return _name; }
    // getter time
    public int getHour() { return _hour; }
    public int getMinute() { return _min; }
    public int getSecond() { return _sec; }
    public int getHourStart() { return _hourStart; }
    public int getHourEnd() { return _hourEnd; }
    public int getTimeInMin() { return _hour * 60 + _min; }
    public int getTimeInSec() { return _hour * 60 * 60 + _min * 60 + _sec; }

    // 
    public int getSalary() { return _salary; }
    public int getExhaust() { return _exhaustion; }

    // getter skils
    public int getTechnical() {return _technical;}
    public int getArtistic() {return _artistic;}
    public int getCommunication() {return _communication;}
    public int getScience() {return _science;}

    public JButton createActivityButton(ActionListener action) {
        JButton workButton = WButton.createButton(
            getName(), 
            "", 
            5, 
            5, 
            5, 
            5, 
            BG_BUTTON, 
            TEXT_COLOR, 
            FONT_TEXT
        );
    
        workButton.addActionListener(action);

        return workButton;
    }
    
}