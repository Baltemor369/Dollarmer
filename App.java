import core.Activity;
import core.Game;
import static core.Const.*;
import widgets.WButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;

public class App extends JFrame{
    private Game game;
    private Timer windowClock;
    private JLabel playerInfoLabel, clockLabel;
    private JButton sleepButton;
    private HashMap<String, JFrame> windows = new HashMap<>();
    
    public App(){
        setTitle(APP_NAME);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        game = new Game();
        windows.put("invent", null);
        windows.put("work", null);

        windowClock = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // update the time date
                clockLabel.setText("<html>"+game.getDate()+"<br>"+game.getTime()+"</html>");
                // update player stats
                playerInfoLabel.setText(game.getPlayer().getInfo());
            }
        });

        windowClock.start();
        game.startClocks();
        mainWindow();
    }

    private void mainWindow(){
        // [Panel] main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // [Panel] Header
        JPanel headPanel = new JPanel();
        headPanel.setLayout(new BorderLayout());
        headPanel.setBorder(new EmptyBorder(30, 30, 0, 0));

        // [Panel] Left
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 30, 30, 10));
        
        // [Panel] Right
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(new EmptyBorder(10, 30, 30, 10));

        // [Panel] Center
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        panel.add(headPanel, BorderLayout.NORTH);
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        panel.add(centerPanel, BorderLayout.CENTER);

        // Header Panel //
        // [Label]
        JLabel titleLabel= new JLabel(APP_NAME, SwingConstants.CENTER);
        titleLabel.setFont(FONT_TITLE);
        
        // [Label]
        clockLabel = new JLabel("<html>"+game.getDate()+"<br>"+game.getTime()+"</html>", SwingConstants.CENTER);
        clockLabel.setFont(FONT_TEXT);

        headPanel.add(titleLabel, BorderLayout.CENTER);
        headPanel.add(clockLabel, BorderLayout.WEST);
        
        // LeftPanel
        // [Label] stats
        playerInfoLabel = new JLabel(game.getPlayer().getInfo(), SwingConstants.CENTER);
        playerInfoLabel.setFont(FONT_TEXT);

        sleepButton = WButton.createButton("Sleep", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        sleepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (game.getPlayer().getSleep() >= 70) {
                    game.sleep();
                }
            }
        });

        leftPanel.add(playerInfoLabel, BorderLayout.CENTER);
        leftPanel.add(sleepButton, BorderLayout.SOUTH);
        
        // Right Panel// 
        // [Button] inventory
        JButton inventButton = WButton.createButton("Inventory", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        inventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                inventWindow();
            }
        });

        // [Button] works
        JButton actyButton = WButton.createButton("Works", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        actyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                workWindow();
            }
        });

        rightPanel.add(inventButton, BorderLayout.NORTH);
        rightPanel.add(actyButton, BorderLayout.SOUTH);

        // Center Panel //
        
        add(panel, BorderLayout.CENTER);
        
        setVisible(true);
    }

    public JButton createActivityButton(Activity activity) {
        return activity.createActivityButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentHour = game.getCalendar().get(Calendar.HOUR_OF_DAY);
                if (activity.getHourStart() <= activity.getHourEnd()) {
                    if (currentHour >= activity.getHourStart() && currentHour-activity.getHour() < activity.getHourEnd()) {
                        game.getPlayer().earnMoney(activity.getSalary());
                        game.getPlayer().addExhaustion(activity.getExhaust());
                        
                        for (int i = 0; i < (activity.getTimeInMin()); i++) {
                            game.tictac();
                        }
                    }
                } else {
                    if (currentHour >= activity.getHourStart() || currentHour-activity.getHour() < activity.getHourEnd()) {
                        game.getPlayer().earnMoney(activity.getSalary());
                        game.getPlayer().addExhaustion(activity.getExhaust());
                        
                        for (int i = 0; i < (activity.getTimeInMin()); i++) {
                            game.tictac();
                        }
                    }
                }
            }
        });
    }

    public void workWindow(){
        if (windows.get("work")!=null){
            windows.get("work").toFront();
            return;
        }
    
        JFrame newWindow = new JFrame("Works");
        newWindow.setSize(500, 400);
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newWindow.setLocationRelativeTo(null);
    
        // [Panel] Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
    
        newWindow.add(buttonPanel);
    
        // [Button] DogSitting
        Activity activity1 = new Activity("DogSitting 1h 10$", 1, 0, 5, 10, 6, 21);
        buttonPanel.add(createActivityButton(activity1));
    
        // [Button] BabySitting
        Activity activity2 = new Activity("BabySitting 2h 30$", 2, 0, 30, 20, 6, 2);
        buttonPanel.add(createActivityButton(activity2));
        
        // [Button] Night Guard
        Activity activity3 = new Activity("Security Guard 6h 80$", 6, 0, 0, 30, 0, 6);
        buttonPanel.add(createActivityButton(activity3));
    
        newWindow.setVisible(true);
        windows.replace("work", newWindow);
    }

    public void inventWindow(){
        
        if (windows.get("invent")!=null){
            windows.get("invent").toFront();
            return;
        }

        JFrame newWindow = new JFrame("Inventory");
        newWindow.setSize(500, 400);
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newWindow.setLocationRelativeTo(null);
        
        
        JLabel label = new JLabel(game.getPlayer().getInvent().getInventoryString(), SwingConstants.CENTER);
        label.setFont(LITTLE_FONT_TEXT);

        windowClock = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // update player invent
                label.setText(game.getPlayer().getInvent().getInventoryString());
            }
        });
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inventPanel = new JPanel();
        inventPanel.setLayout(new FlowLayout());
        
        
        inventPanel.add(label);

        JButton backButton = WButton.createButton("Exit", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                windows.replace("invent", null);
                newWindow.dispose();
            }
        });

        panel.add(inventPanel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);
        newWindow.add(panel);

        windowClock.start();
        newWindow.setVisible(true);
        windows.replace("invent", newWindow);
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }
}