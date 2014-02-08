package net.s5games.mafia.ui;

import javax.swing.*;
import java.awt.*;

public class TitledComponent extends JPanel {
    JLabel label;

    public TitledComponent(Component j, String title) {
        super();
        this.setLayout(new GridLayout(2, 1));
        label = new JLabel(" " + title);
        this.add(label);
        this.add(j);
    }

    public void setText(String newTitle) {
        label.setText(" " + newTitle);
    }

}