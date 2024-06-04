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
    // [String]
    public static final String APP_NAME = "Money Game";
    
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
                game.sleep();
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

<<<<<<< HEAD:src/App.java

        headPanel.add(titleLabel, BorderLayout.CENTER);
=======
        Timer clock = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.MINUTE, 1);
                updateClockText();
            }
        });
        clock.start();

        Timer clockMoneyGain = new Timer(20000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.earnMoney(1);
                playerInfoLabel.setText(player.getInfo());
            }
        });
        clockMoneyGain.start();
>>>>>>> cf9b57d7b1b2969782d2497dd9e977c405608445:App.java
        headPanel.add(clockLabel, BorderLayout.WEST);
        
        // [Panel] Left
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10, 30, 30, 10));

<<<<<<< HEAD:src/App.java
        // [Label]
        playerInfoLabel = new JLabel(game.getPlayer().getInfo(), SwingConstants.CENTER);
=======
        // [Button] Settings
        JButton settingsButton = WButton.createButton(
            "Settings",
            "",
            5,
            5,
            5,
            5,
            BG_BUTTON,
            TEXT_COLOR,
            FONT_TEXT
            );
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

            }
        });

        headPanel.add(settingsButton, BorderLayout.EAST);

        // [Panel] stats
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BorderLayout()); 

        player = new Player();

        playerInfoLabel = new JLabel(player.getInfo(), SwingConstants.CENTER);
>>>>>>> cf9b57d7b1b2969782d2497dd9e977c405608445:App.java
        playerInfoLabel.setFont(FONT_TEXT);

        leftPanel.add(playerInfoLabel, BorderLayout.CENTER);
        leftPanel.add(sleepButton, BorderLayout.SOUTH);
        
        // [Panel] Center
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout()); 

        // [Panel] Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        // [Button] DogSitting
        JButton workButton = createActivity("DogSitting 1h 10$", 1, 0, 10, 5);
        buttonPanel.add(workButton);

        // [Button] DogSitting
        workButton = createActivity("BabySitting 2h 30$", 2, 0, 30, 15);
        buttonPanel.add(workButton);

        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(headPanel, BorderLayout.NORTH);
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(centerPanel, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createActivity(String name, int hour, int minute, int salary, int xp) {
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
                if ( 6 <= game.getCalendar().get(Calendar.HOUR_OF_DAY) && game.getCalendar().get(Calendar.HOUR_OF_DAY) < 21) {
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