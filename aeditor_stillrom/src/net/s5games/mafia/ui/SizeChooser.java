// George Frick
// SizeChooser.java
// Area Editor Project, Spring 2002
package net.s5games.mafia.ui;

import net.s5games.mafia.model.Size;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class SizeChooser extends JComboBox {
    private final static String[] sizes = {"XS", "Small", "Medium", "Large", "XL"};

    public SizeChooser() {
        super(sizes);
        setBorder(new TitledBorder("Size"));
        setMaximumRowCount(5);
        setEditable(false);
    }

    public Size currentSize() {
        String s = (String) getSelectedItem();
        return new Size(s);
    }

    public Size getSelectedSize() {
        String s = (String) getSelectedItem();
        return new Size(s);
    }

    public void setCurrentSize(Size s) {
        setSelectedItem(sizes[s.getSize()]);
    }
}