/*
 * George Frick
 * LabeledField.java
 * Area Editor Project, Spring 2002
 * 12/19/2015
 */
package net.s5games.mafia.ui;

import net.s5games.mafia.model.MudConstants;
import net.s5games.mafia.model.*;
import net.s5games.mafia.ui.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class LabeledField extends JPanel {
    
    public LabeledField(String label, Component field) {
        setLayout(new MigLayout());
        add(new JLabel(label + ": "));
        add(field);
    }

    public LabeledField(String label, Component field, boolean wrapBeforeLabel) {
        setLayout(new MigLayout());
        
        if( wrapBeforeLabel == true ) {
            add(new JLabel(label + ": "), "wrap");
            
        } else {
            add(new JLabel(label + ": "));
            
        }
        add(field);
    }

}
 