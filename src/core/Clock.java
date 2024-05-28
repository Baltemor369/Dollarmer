package src.core;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Clock {
    private Calendar calendar;
    private Timer timer;
    private JLabel clockLabel;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    public Clock(JLabel clockLabel) {
        this(1000, clockLabel, 12, 0, 0, 1, 1, 2024);
    }

    public Clock(int delay, JLabel clockLabel, int hour, int minute, int second, int day, int month, int year) {
        this.calendar = Calendar.getInstance();
        this.clockLabel = clockLabel;
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.timeFormat = new SimpleDateFormat("HH:mm");

        if (isValidDate(hour, minute, second, day, month-1, year)) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, second);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.MONTH, month-1);
            calendar.set(Calendar.YEAR, year);
        } else {
            throw new IllegalArgumentException("Date ou heure invalide");
        }
        
        this.timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tick();
                updateClockLabel();
            }
        });

        updateClockLabel();
    }

    public void start() {
        timer.start();
    }

    public void pause() {
        timer.stop();
    }

    private void tick() {
        calendar.add(Calendar.MINUTE, 1);
    }

    private void updateClockLabel() {
        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());
        clockLabel.setText("<html><div style='text-align: center;'>" + date + "<br>" + time + "</div></html>");
    }

    public int getHours() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinutes() {
        return calendar.get(Calendar.MINUTE);
    }

    public int getSeconds() {
        return calendar.get(Calendar.SECOND);
    }

    public boolean isNightTime() {
        int hour = getHours();
        return (hour >= 21 || hour < 6);
    }

    public void addMinutes(int minutesToAdd) {
        calendar.add(Calendar.MINUTE, minutesToAdd);
        updateClockLabel();
    }

    private boolean isValidDate(int hour, int minute, int second, int day, int month, int year) {
        if (hour < 0 || hour >= 24) return false;
        if (minute < 0 || minute >= 60) return false;
        if (second < 0 || second >= 60) return false;
        if (month < 0 || month >= 12) return false;

        Calendar tempCal = Calendar.getInstance();
        tempCal.setLenient(false);
        tempCal.set(Calendar.DAY_OF_MONTH, day);
        tempCal.set(Calendar.MONTH, month);
        tempCal.set(Calendar.YEAR, year);
        
        try {
            tempCal.getTime();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
