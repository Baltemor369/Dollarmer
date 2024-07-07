import widgets.WButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import src.Activity;
import src.Food;
import src.Game;
import src.Item;
import src.Shop;

import static src.Const.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;

public class App extends JFrame{
    private Game game;
    private Timer windowClock;
    private JLabel playerInfoLabel, clockLabel;
    private JPanel contentPanel= new JPanel();
    
    public App(){
        setTitle(APP_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        game = new Game();
        
        windowClock = new Timer(300, new ActionListener() {
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
        // [Panel] Top 
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
            clockLabel = new JLabel("<html>"+game.getDate()+"<br>"+game.getTime()+"</html>", SwingConstants.CENTER);
            clockLabel.setFont(FONT_TEXT);
            topPanel.add(clockLabel, BorderLayout.WEST);
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());

                JButton sleepButton = WButton.createButton("Sleep");
                sleepButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        if (game.getPlayer().getExhaust() >= 70) {
                            game.sleep();
                        }
                    }
                });
                // [Button] inventory
                JButton inventButton = WButton.createButton("Inventory");
                inventButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        inventWindow();
                    }
                });
                // [Button] jobs
                JButton actyButton = WButton.createButton("Jobs");
                actyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        jobsWindow();
                    }
                });
                // [Button] mall
                JButton shopButton = WButton.createButton("Mall");
                shopButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        mallWindow();
                    }
                });
            buttonPanel.add(sleepButton);
            buttonPanel.add(inventButton);
            buttonPanel.add(actyButton);
            buttonPanel.add(shopButton);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        // [Panel] Left
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
            playerInfoLabel = new JLabel(game.getPlayer().getInfo(), SwingConstants.CENTER);
            playerInfoLabel.setFont(FONT_TEXT);
            leftPanel.add(playerInfoLabel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        contentPanel.setLayout(new BorderLayout());    

        add(contentPanel, BorderLayout.CENTER);
        
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

    public void jobsWindow(){
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        // title "Jobs"
        JLabel title = new JLabel("Jobs", SwingConstants.CENTER);
        title.setFont(FONT_TITLE);
        contentPanel.add(title, BorderLayout.NORTH);
        
        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        for (Activity activity : game.getAllJobs().values()) {
            buttonPanel.add(createActivityButton(activity));        
        }
        
        contentPanel.add(buttonPanel, BorderLayout.CENTER);
        contentPanel.setVisible(true);
    }

    public void inventWindow(){
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        // title "Inventory"
        JLabel title = new JLabel("Inventory", SwingConstants.CENTER);
        title.setFont(FONT_TITLE);
        contentPanel.add(title, BorderLayout.NORTH);
        
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new FlowLayout());
        bodyPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        JPanel foodPanel = new JPanel();
        foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));
        foodPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        HashMap<String, Food> items = game.getPlayer().getInventory().getAllFood();
        for (Food it : items.values()) {
            JButton button = WButton.createButton(it.toString());
            button.addActionListener(e -> {
                game.getPlayer().eat(it);
                button.setText(it.toString());
                if (!game.getPlayer().getInventory().getAllFood().containsKey(it.getName())) {
                    foodPanel.remove(button);
                }
            });
            foodPanel.add(button);
        }
        foodPanel.revalidate();
        foodPanel.repaint();
        
        bodyPanel.add(foodPanel);

        contentPanel.add(bodyPanel, BorderLayout.CENTER);
        
        contentPanel.setVisible(true);
    }

    public void mallWindow(){
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        // title "Mall"
        JLabel title = new JLabel("Mall", SwingConstants.CENTER);
        title.setFont(FONT_TITLE);
        contentPanel.add(title, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
        
        for (Shop myshop : game.getAllShop().values()) {
            JButton shopButton = WButton.createButton(myshop.getName());
            shopButton.addActionListener(e -> shopWindow(myshop));
            buttonPanel.add(shopButton);
        }
        contentPanel.add(buttonPanel);
        contentPanel.setVisible(true);
    }

    public void shopWindow(Shop shop){
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        // title "Shop"
        JLabel title = new JLabel("Shop", SwingConstants.CENTER);
        title.setFont(FONT_TITLE);
        contentPanel.add(title, BorderLayout.NORTH);

        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new FlowLayout());
        
        for (String key : shop.getItems().keySet()) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BorderLayout());
            
            JButton productButton = WButton.createButton(shop.getItems().get(key).getName() +" "+shop.getItems().get(key).getPrice()+"$");
            productButton.addActionListener(e -> {
                game.getPlayer().buyItem(shop.getItems().get(key));
            });
            itemPanel.add(productButton, BorderLayout.CENTER);
            
            JPanel subPanel = new JPanel();
            subPanel.setLayout(new FlowLayout());
            itemPanel.add(subPanel, BorderLayout.SOUTH);

            JLabel countLabel = new JLabel(" " + shop.getItems().get(key).getCount() + " ");
            countLabel.setFont(FONT_TEXT);
            
            JButton minusButton = new JButton("-");
            minusButton.addActionListener(e -> {
                if (shop.getItems().get(key).getCount() > 1) {
                    shop.getItems().get(key).removeAmount(1);
                    updateItemCountLabel(countLabel, shop.getItems().get(key));
                }
            });
            
            JButton plusButton = new JButton("+");
            plusButton.addActionListener(e -> {
                shop.getItems().get(key).addAmount(1);;
                updateItemCountLabel(countLabel, shop.getItems().get(key));
            });

            subPanel.add(minusButton);
            subPanel.add(countLabel);
            subPanel.add(plusButton);

            itemPanel.add(subPanel, BorderLayout.SOUTH);
            itemsPanel.add(itemPanel);
        }

        contentPanel.add(itemsPanel, BorderLayout.CENTER);
        contentPanel.setVisible(true);
    }

    private void updateItemCountLabel(JLabel label, Item item) {
        label.setText(" "+item.getCount()+" ");
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

