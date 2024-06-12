package core;

import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import widgets.WButton;

import static core.Const.*;

public class Activity {
    String _name;
    private int _hour, _min, _sec;
    private int _hourStart, _hourEnd;
    private int _salary, _xp, _tiredness;

    public Activity(String name, int hour, int minute, int salary, int xp, int tiredness, int startHour, int endHour) {
        this._name = name;
        this._hour = hour;
        this._min = minute;
        this._salary = salary;
        this._xp = xp;
        this._tiredness = tiredness;
        this._hourStart = startHour;
        this._hourEnd = endHour;
    }

    public String getName() {
        return _name;
    }
    public int getHour() {
        return _hour;
    }
    public int getMinute() {
        return _min;
    }
    public int getSecond() {
        return _sec;
    }
    public int getHourStart() {
        return _hourStart;
    }
    public int getHourEnd() {
        return _hourEnd;
    }
    public int getSalary() {
        return _salary;
    }
    public int getXp() {
        return _xp;
    }
    public int getTiredness() {
        return _tiredness;
    }
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