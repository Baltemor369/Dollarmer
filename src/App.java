
import javax.swing.*;

// import src.core.Clock;
import core.Player;
import widgets.WButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class App extends JFrame{
    private Player player;
    private JLabel playerInfoLabel, clockLabel;
    private JButton workButton;
    private Calendar calendar = Calendar.getInstance();

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

        calendar.set(2024, 0, 1, 6, 0, 0);
        
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String dateTimeString = dateFormat.format(calendar.getTime());
        String timeString = timeFormat.format(calendar.getTime());
        clockLabel = new JLabel("<html>"+dateTimeString+"<br>"+timeString+"</html>", SwingConstants.CENTER);
        clockLabel.setFont(FONT_TEXT);

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
                if ( 6 <= calendar.get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.HOUR_OF_DAY) < 21) {
                    player.earnMoney(10);
                    player.gainXp(20);
                    playerInfoLabel.setText(player.getInfo());
                    calendar.add(Calendar.MINUTE, 10);
                    
                    updateClockText();
                }
            }
        });
        
        buttonPanel.add(workButton);

        JButton sleepButton = WButton.createButton("Sleep", "", 5, 5, 5, 5, BG_BUTTON, TEXT_COLOR, FONT_TEXT);
        sleepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                
                int minutesUntil6AM;
                if (hour < 6 || (hour == 6 && minute == 0)) {
                    minutesUntil6AM = (6 - hour) * 60 - minute;
                } else {
                    minutesUntil6AM = (24 - hour + 6) * 60 - minute;
                }
                
                player.earnMoney((int)(minutesUntil6AM/10));
                playerInfoLabel.setText(player.getInfo());
                hour = calendar.get(Calendar.HOUR_OF_DAY);
        
                if (hour >= 6) {
                    calendar.add(Calendar.DATE, 1); // Avance d'un jour
                }
                
                calendar.set(Calendar.HOUR_OF_DAY, 6);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);

                updateClockText();
            }
        });

        buttonPanel.add(sleepButton);

        panel.add(headPanel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);

        startEarningMoney();

        setVisible(true);
    }

    private void updateClockText() {
        
        // update clock text
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String dateTimeString = dateFormat.format(calendar.getTime());
        String timeString = timeFormat.format(calendar.getTime());
        clockLabel.setText("<html>"+dateTimeString+"<br>"+timeString+"</html>");
    }

    private void startEarningMoney() {
        Timer moneyTimer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.earnMoney(1);
                playerInfoLabel.setText(player.getInfo());
            }
        });
        moneyTimer.start();
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