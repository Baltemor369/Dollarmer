import core.Activity;
import core.Game;
import core.Item;
import core.Shop;

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
    private HashMap<String, JFrame> windows = new HashMap<>();
    
    public App(){
        setTitle(APP_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        game = new Game();
        windows.put("invent", null);
        windows.put("work", null);
        windows.put("mall", null);
        windows.put("shop", null);

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

        // [Panel] Left
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 30, 30, 10));

        // [Panel] Center
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setBorder(new EmptyBorder(30,0,0,0));

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // LeftPanel
        // [Label]
        clockLabel = new JLabel("<html>"+game.getDate()+"<br>"+game.getTime()+"</html>", SwingConstants.CENTER);
        clockLabel.setFont(FONT_TEXT);
        
        // [Label] stats
        playerInfoLabel = new JLabel(game.getPlayer().getInfo(), SwingConstants.CENTER);
        playerInfoLabel.setFont(FONT_TEXT);

        leftPanel.add(clockLabel, BorderLayout.NORTH);
        leftPanel.add(playerInfoLabel, BorderLayout.CENTER);

        // Center Panel //
        // [Button] sleep
        JButton sleepButton = WButton.createButton("Sleep", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        sleepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (game.getPlayer().getExhaust() >= 70) {
                    game.sleep();
                }
            }
        });

        // [Button] inventory
        JButton inventButton = WButton.createButton("Inventory", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        inventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (!windowExists(INVENT)) {
                    JFrame newWindow = new JFrame("Inventory");
                    inventWindow(newWindow);
                    windows.put(INVENT, newWindow);
                }else {
                    windows.get(INVENT).toFront();
                }
            }
        });

        // [Button] jobs
        JButton actyButton = WButton.createButton("Jobs", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        actyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (!windowExists(INVENT)) {
                    JFrame newWindow = new JFrame("Jobs");
                    workWindow(newWindow);
                    windows.put(WORK, newWindow);
                }else {
                    windows.get("work").toFront();
                }
            }
        });
        
        // [Button] mall
        JButton shopButton = WButton.createButton("Mall", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (!windowExists(INVENT)) {
                    JFrame newWindow = new JFrame("Mall");
                    mallWindow(newWindow);
                    windows.put(MALL, newWindow);
                }else {
                    windows.get(MALL).toFront();
                }
            }
        });

        centerPanel.add(inventButton);
        centerPanel.add(sleepButton);
        centerPanel.add(actyButton);
        centerPanel.add(shopButton);
        
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
                        game.getPlayer().addMoney(activity.getSalary());
                        game.getPlayer().addExhaustion(activity.getExhaust());
                        
                        for (int i = 0; i < (activity.getTimeInMin()); i++) {
                            game.tictac();
                        }
                    }
                } else {
                    if (currentHour >= activity.getHourStart() || currentHour-activity.getHour() < activity.getHourEnd()) {
                        game.getPlayer().addMoney(activity.getSalary());
                        game.getPlayer().addExhaustion(activity.getExhaust());
                        
                        for (int i = 0; i < (activity.getTimeInMin()); i++) {
                            game.tictac();
                        }
                    }
                }
            }
        });
    }

    public void workWindow(JFrame newWindow){
        newWindow.setSize(500, 400);
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newWindow.setLocationRelativeTo(null);
        newWindow.setLayout(new BorderLayout());
    
        // [Panel] Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
    
        newWindow.add(buttonPanel, BorderLayout.CENTER);
    
        for (Activity activity : game.getAllJobs().values()) {
            buttonPanel.add(createActivityButton(activity));        
        }

        JButton backButton = WButton.createButton("Exit", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                windows.replace("work", null);
                newWindow.dispose();
            }
        });
        
        newWindow.add(backButton, BorderLayout.SOUTH);
    
        newWindow.setVisible(true);
    }

    public void inventWindow(JFrame newWindow){
        newWindow.setSize(500, 400);
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newWindow.setLocationRelativeTo(null);
        newWindow.setLayout(new BorderLayout());
        
        
        JLabel label = new JLabel(game.getPlayer().getInventString(), SwingConstants.CENTER);
        label.setFont(LITTLE_FONT_TEXT);

        windowClock = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // update player invent
                label.setText(game.getPlayer().getInventString());
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
        newWindow.add(panel, BorderLayout.CENTER);
        newWindow.add(backButton, BorderLayout.SOUTH);

        windowClock.start();
        newWindow.setVisible(true);
    }

    public void mallWindow(JFrame newWindow){
        newWindow.setSize(500, 400);
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newWindow.setLocationRelativeTo(null);
        newWindow.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(new FlowLayout());

        JButton backButton = WButton.createButton("Exit", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                windows.replace("Mall", null);
                newWindow.dispose();
            }
        });

        HashMap<String, Shop> shops = game.getAllShop();
        for (Shop myshop : shops.values()) {
            JButton shopButton = WButton.createButton(myshop.getName(), "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
            shopButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!windowExists(INVENT)) {
                        JFrame newWindow = new JFrame("Mall");
                        shopWindow(newWindow, myshop);
                        windows.put(SHOP, newWindow);
                    }else {
                        windows.get(SHOP).toFront();
                    }
                    
                }
            });
            shopPanel.add(shopButton);
        }
        
        panel.add(shopPanel, BorderLayout.CENTER);

        newWindow.add(panel, BorderLayout.CENTER);
        newWindow.add(backButton, BorderLayout.SOUTH);

        newWindow.setVisible(true);
    }

    public void shopWindow(JFrame newWindow, Shop shop){
        newWindow.setSize(500, 400);
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newWindow.setLocationRelativeTo(null);
        newWindow.setLayout(new BorderLayout());

        JPanel itemPanel = new JPanel();
        
        for (Item item : shop.getItems().values()) {
            // create a button for each product
            JButton productButton = WButton.createButton(item.getName(), "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
            productButton.addActionListener(e -> game.getPlayer().buyItem(item, 1));
            itemPanel.add(productButton);
        }

        JButton backButton = WButton.createButton("Exit", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                windows.replace("shop", null);
                newWindow.dispose();
            }
        });

        newWindow.add(backButton, BorderLayout.SOUTH);
        newWindow.add(itemPanel, BorderLayout.CENTER);
        newWindow.setVisible(true);
    }

    public boolean windowExists(String windowName) {
        return windows.containsKey(windowName) && windows.get(windowName) != null;
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

