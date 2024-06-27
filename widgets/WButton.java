package widgets;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;

public class WButton {
    
    public static JButton createButton(String text, String iconPath, int top, int left, int bottom, int right, Color bg, Color fg, Font font) {
        JButton button = new JButton(text);

        if (iconPath != null && !iconPath.isEmpty()) {
            button.setIcon(new ImageIcon(iconPath));
        }

        button.setForeground(fg);
        button.setBackground(bg);
        button.setFont(font);
        button.setBorder(BorderFactory.createCompoundBorder(
            new BevelBorder(BevelBorder.RAISED), // Outer border
            new EmptyBorder(top, left, bottom, right) // Inner border for padding
        ));
        
        return button;
    }
}
