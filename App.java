import core.Game;
import widgets.WButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class App extends JFrame{
    private Game game;
    private Timer windowClock;
    private JLabel playerInfoLabel, clockLabel;
    private JButton sleepButton;

    // Const
    // [Color]
    public static final Color BG_BUTTON = new Color(90,90,90);
    public static final Color TEXT_COLOR = new Color(240,240,240);
    // [Font]
    public static final Font FONT_TITLE = new Font("Serif", Font.BOLD, 32);
    public static final Font FONT_TEXT = new Font("Serif", Font.BOLD, 24);
    public static final Font LITTLE_FONT_TEXT = new Font("Serif", Font.PLAIN, 18);
    // [String]
    public static final String APP_NAME = "Dollarmer";
    
    public App(){
        setTitle(APP_NAME);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        game = new Game();

        windowClock = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // update the time date
                clockLabel.setText("<html>"+game.getDate()+"<br>"+game.getTime()+"</html>");
                // update player stats
                playerInfoLabel.setText(game.getPlayer().getInfo());
            }
        });

        sleepButton = WButton.createButton("Sleep", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        sleepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (game.getCalendar().get(Calendar.HOUR_OF_DAY) >= 21 || game.getCalendar().get(Calendar.HOUR_OF_DAY) < 6) {
                    game.sleep();
                }
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

        // [Label]
        JLabel titleLabel= new JLabel(APP_NAME, SwingConstants.CENTER);
        titleLabel.setFont(FONT_TITLE);
        
        // [Label]
        clockLabel = new JLabel("<html>"+game.getDate()+"<br>"+game.getTime()+"</html>", SwingConstants.CENTER);
        clockLabel.setFont(FONT_TEXT);

        headPanel.add(titleLabel, BorderLayout.CENTER);
        headPanel.add(clockLabel, BorderLayout.WEST);
        
        // [Panel] Left
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 30, 30, 10));

        // [Label]
        playerInfoLabel = new JLabel(game.getPlayer().getInfo(), SwingConstants.CENTER);
        playerInfoLabel.setFont(FONT_TEXT);

        leftPanel.add(playerInfoLabel, BorderLayout.CENTER);
        leftPanel.add(sleepButton, BorderLayout.SOUTH);
        
        // [Panel] Center
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout()); 

        // [Panel] Shop
        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(new FlowLayout());

        // [Button]
        JButton goldButton = WButton.createButton(
            "Gold Ingot - 150$", 
            "", 
            5,
            5,
            5,
            5, 
            BG_BUTTON, 
            TEXT_COLOR, 
            FONT_TEXT
            );
        goldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (game.getPlayer().getMoney() >= 150) {
                    game.getPlayer().addItem("Gold Ingot");
                    game.getPlayer().spendMoney(150);
                }
            }
        });

        JButton carButton = WButton.createButton(
            "Car - 2000$", 
            "", 
            5,
            5,
            5,
            5, 
            BG_BUTTON, 
            TEXT_COLOR, 
            FONT_TEXT
            );
        carButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (game.getPlayer().getMoney() >= 2000) {
                    game.getPlayer().addItem("Car");
                    game.getPlayer().spendMoney(2000);
                }
            }
        });

        JButton tshirtButton = WButton.createButton(
            "Tshirt - 25$", 
            "", 
            5,
            5,
            5,
            5, 
            BG_BUTTON, 
            TEXT_COLOR, 
            FONT_TEXT
            );
        tshirtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (game.getPlayer().getMoney() >= 25) {
                    game.getPlayer().addItem("Tshirt");
                    game.getPlayer().spendMoney(25);
                }
            }
        });

        shopPanel.add(goldButton);
        shopPanel.add(carButton);
        shopPanel.add(tshirtButton);

        JButton inventButton = WButton.createButton("Inventory", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        inventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                inventWindow();
            }
        });

        centerPanel.add(inventButton, BorderLayout.EAST);
        centerPanel.add(shopPanel, BorderLayout.CENTER);

        // [Panel] Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        // [Button] DogSitting
        JButton workButton = createActivity("DogSitting 1h 10$", 1, 0, 10, 5, 6, 21);
        buttonPanel.add(workButton);

        // [Button] BabySitting
        workButton = createActivity("BabySitting 2h 30$", 2, 0, 30, 15, 6, 23);
        buttonPanel.add(workButton);

        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(headPanel, BorderLayout.NORTH);
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(centerPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
        
        setVisible(true);
    }

    public void inventWindow(){

        JFrame newWindow = new JFrame("Inventory");
        newWindow.setSize(500, 400);
        newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
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
                newWindow.dispose();
            }
        });

        panel.add(inventPanel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);
        newWindow.add(panel);
        
        windowClock.start();
        newWindow.setVisible(true);
    }

    private JButton createActivity(String name, int hour, int minute, int salary, int xp, int startHour, int endHour) {
        JButton workButton = WButton.createButton(
            name, 
            "", 
            5, 
            5, 
            5, 
            5, 
            BG_BUTTON, 
            TEXT_COLOR, 
            FONT_TEXT
            );

        workButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if ( startHour <= game.getCalendar().get(Calendar.HOUR_OF_DAY) && game.getCalendar().get(Calendar.HOUR_OF_DAY) < (endHour-hour)) {
                    game.getPlayer().earnMoney(salary);
                    game.getPlayer().gainXp(xp);
                                        
                    game.getCalendar().add(Calendar.HOUR_OF_DAY, hour);
                    game.getCalendar().add(Calendar.MINUTE, minute);
                    
                }
            }
        });
        return workButton;
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