import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import components.CButton;

public class App extends JFrame{
    private Player player;
    private JLabel playerInfoLabel;
    private JLabel clockLabel;
    private JButton workButton;
    private int minutes,hours=12;
    
    public App(){
        setTitle("MoneyGame");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titleLabel= new JLabel("Money Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        
        player = new Player();
        playerInfoLabel = new JLabel(player.getInfo(),SwingConstants.CENTER);
        playerInfoLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());

        CButton customButton = new CButton(
            "Work",
            new Dimension(100, 50),
            new Color(80,80,80),
            Color.WHITE,
            new Font("Serif", Font.PLAIN, 17)
        );
        
        workButton = customButton.getButton();
        workButton.setFont(new Font("Serif",Font.PLAIN, 18));
        workButton.setPreferredSize(new Dimension(150, 50));
        workButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                player.earnMoney(10);
                player.gainXp(20);
                playerInfoLabel.setText(player.getInfo());
                addMinutes(10);
            }
        });

        buttonPanel.add(workButton);

        clockLabel = new JLabel("", SwingConstants.CENTER);
        clockLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        startClock();
        startEarningMoney();

        add(titleLabel, BorderLayout.NORTH);
        add(playerInfoLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(clockLabel, BorderLayout.EAST);

        setVisible(true);
    }

    private void startClock() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minutes++;
                if (minutes == 60) {
                    minutes = 0;
                    hours++;
                    if (hours == 24) {
                        hours = 0;
                    }
                }
                clockLabel.setText(String.format("%02dh%02d", hours, minutes));    
                checkButtonState();
            }
        });

        timer.start();
    }

    private void startEarningMoney() {
        Timer timer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.earnMoney(1);
                playerInfoLabel.setText(player.getInfo());
            }
        });

        timer.start();
    }

    private void addMinutes(int additionalMinutes) {
        minutes += additionalMinutes;
        while (minutes >= 60) {
            minutes -= 60;
            hours++;
            if (hours == 24) {
                hours = 0;
            }
        }
        // Mettre à jour le JLabel avec l'heure actuelle
        clockLabel.setText(String.format("%02dh%02d", hours, minutes));
        checkButtonState();
    }

    private void checkButtonState() {
        if (hours >= 21 || hours < 6) {
            workButton.setEnabled(false);
        } else {
            workButton.setEnabled(true);
        }
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
