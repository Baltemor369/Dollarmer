package src;
import javax.swing.*;

import src.core.Clock;
import src.core.Player;
import src.widgets.WButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class App extends JFrame{
    private Player player;
    private Clock clock;
    private JLabel playerInfoLabel, clockLabel;
    private JButton workButton;

    // Const
    // [Color]
    public static final Color BG_BUTTON = new Color(90,90,90);
    public static final Color TEXT_COLOR = new Color(240,240,240);
    // [Font]
    public static final Font FONT_TITLE = new Font("Serif", Font.BOLD, 24);
    public static final Font FONT_TEXT = new Font("Serif", Font.PLAIN, 18);
    // [String]
    public static final String APP_NAME = "Money Game";
    
    public App(){
        setTitle(APP_NAME);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // [Panel] main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // [Panel] Header
        JPanel headPanel = new JPanel();
        headPanel.setLayout(new BorderLayout()); 
        
        JLabel titleLabel= new JLabel(APP_NAME, SwingConstants.CENTER);
        titleLabel.setFont(FONT_TITLE);
        headPanel.add(titleLabel, BorderLayout.CENTER);
        
        // [Clock]
        clockLabel = new JLabel("", SwingConstants.CENTER);
        clockLabel.setFont(FONT_TEXT);
        
        clock = new Clock(clockLabel);
        clock.start();

        headPanel.add(clockLabel, BorderLayout.WEST);

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
        playerInfoLabel.setFont(FONT_TEXT);

        statsPanel.add(playerInfoLabel, BorderLayout.CENTER);
        
        // [Panel] buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        workButton = WButton.createButton(
            "Work", 
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
                if (!clock.isNightTime()) {
                    player.earnMoney(10);
                    player.gainXp(20);
                    playerInfoLabel.setText(player.getInfo());
                    clock.addMinutes(10);    
                }
            }
        });
        
        buttonPanel.add(workButton);

        JButton sleepButton = WButton.createButton("Sleep", "", 5, 5, 5, 5, new Color(90,90,90), new Color(240,240,240), new Font("Serif", Font.PLAIN, 17));
        sleepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
            }
        });

        buttonPanel.add(sleepButton);

        panel.add(headPanel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
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