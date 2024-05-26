package components;

import javax.swing.*;
import java.awt.*;

public class CButton {
    private JButton button;

    public CButton(String text, Dimension size, Color backgroundColor, Color textColor, Font font) {
        button = new JButton(text);

        if (size != null) {
            button.setPreferredSize(size);
            button.setMinimumSize(size);
            button.setMaximumSize(size);
        }

        if (backgroundColor != null) {
            button.setBackground(backgroundColor);
        }

        if (textColor != null) {
            button.setForeground(textColor);
        }

        if (font != null) {
            button.setFont(font);
        }

        button.setBorderPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
    }

    public JButton getButton() {
        return button;
    }
}
