package core;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import widgets.WButton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static core.Const.*;

public class Activity {
    String _name;
    // duration
    private int _hour, _min, _sec;
    // access job time
    private int _hourStart, _hourEnd;
    // 
    private int _salary, _exhaustion, _hungry;
    // skills requirements
    private Map<String, Integer> _skillsRequirements = new HashMap<>();
    private Map<String, Integer> _skillsBonus = new HashMap<>();

    public Activity(String name, int hour, int minute, int salary, int exhaustion, int hungry, int startHour, int endHour) {
        this._name = name;
        this._hour = hour;
        this._min = minute;
        this._salary = salary;
        this._exhaustion = exhaustion;
        this._hungry = hungry;
        this._hourStart = startHour;
        this._hourEnd = endHour;

        _skillsRequirements.put("technical", 1);
        _skillsRequirements.put("artistic", 1);
        _skillsRequirements.put("communication", 1);
        _skillsRequirements.put("science", 1);

        _skillsBonus.put("technical", 0);
        _skillsBonus.put("artistic", 0);
        _skillsBonus.put("communication", 0);
        _skillsBonus.put("science", 0);
    }
    public Activity(String name, int hour, int minute, int salary, int exhaustion, int startHour, int endHour, List<Integer> requirements, List<Integer> bonuses) {
        this._name = name;
        this._hour = hour;
        this._min = minute;
        this._salary = salary;
        this._exhaustion = exhaustion;
        this._hourStart = startHour;
        this._hourEnd = endHour;

        if (requirements.size() ==_skillsRequirements.size()) {    
            _skillsRequirements.put(Const.TECHNICAL, requirements.get(0));
            _skillsRequirements.put(Const.ARTISTIC, requirements.get(1));
            _skillsRequirements.put(Const.COMMUNICATION, requirements.get(2));
            _skillsRequirements.put(Const.SCIENCE, requirements.get(3));
        }

        if (bonuses.size() ==_skillsBonus.size()) {
            _skillsBonus.put(Const.TECHNICAL, bonuses.get(0));
            _skillsBonus.put(Const.ARTISTIC, bonuses.get(1));
            _skillsBonus.put(Const.COMMUNICATION, bonuses.get(2));
            _skillsBonus.put(Const.SCIENCE, bonuses.get(3));
        }
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
    public int getHungry() { return _hungry; }

    // getter skils
    public int getSkillRequirement(String skill) {return _skillsRequirements.get(skill);}
    public int getSkillBonus(String skill) {return _skillsBonus.get(skill);}

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