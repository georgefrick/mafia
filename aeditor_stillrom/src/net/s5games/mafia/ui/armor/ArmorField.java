package net.s5games.mafia.ui.armor;

import net.s5games.mafia.beans.Armor;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Holds text fields to represent armor types (@see Armor). Defaults to 100.
 * Will not allow blank field or non numeric value.
 * Only creates an event on actual change.
 */
public class ArmorField extends Container implements FocusListener {

    // The target ArmorForm
    ArmorForm target;

    private JTextField[] fields;
    private final int fieldSize = 5;
    private final String defaultArmor = "100";

    // Hold which has focus and it's starting value to compare when it loses focus.
    private Armor selected;
    private String value;


    /**
     * Called when one of the JTextFields gains focus
     *
     * @param event FocusEvent
     */
    public void focusGained(FocusEvent event) {
        if (!(event.getComponent() instanceof JTextField))
            return;

        JTextField field = (JTextField) event.getComponent();

        for (Armor at : Armor.values()) {
            if (field == fields[at.ordinal()]) {
                selected = at;
                value = field.getText();
            }
        }
    }

    /**
     * Called when one of the JTextFields loses focus, reverts change if box is blanked out
     * or changed to a non-number. Discards the event if the value did not change.
     *
     * @param event FocusEvent
     */
    public void focusLost(FocusEvent event) {
        if (!(event.getComponent() instanceof JTextField))
            return;

        JTextField field = (JTextField) event.getComponent();

        if (selected == null || field != fields[selected.ordinal()])
            return;

        if (field.getText().equals(value))
            return;

        if (field.getText().length() == 0)
            field.setText(value);

        int iValue = 0;
        try {
            iValue = Integer.parseInt(field.getText());
        } catch (Exception ex) {
            field.setText(value);
        }

        target.setArmor(selected, iValue);
        selected = null; // lost focus.
        value = null;
    }

    /**
     * Create the JTextfields and put them a bordered, titled box.
     *
     * @param target The ArmorForm this field is managing
     */
    public ArmorField(ArmorForm target) {
        this.target = target;

        Box myBox = Box.createHorizontalBox();
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(1, 1, 1, 1);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        fields = new JTextField[Armor.values().length];
        for (Armor at : Armor.values()) {
            fields[at.ordinal()] = new JTextField(fieldSize);
            fields[at.ordinal()].setText(defaultArmor);
            fields[at.ordinal()].addFocusListener(this);
            myBox.add(fields[at.ordinal()]);
        }

        this.add(myBox);
        setEnabled(true);
        StringBuffer buf = new StringBuffer("Armor: ");
        boolean first = true;
        for (Armor at : Armor.values()) {
            if (!first)
                buf.append("/");
            else
                first = false;

            buf.append(at.name());
        }
        myBox.setBorder(new TitledBorder(buf.toString()));
    }

    /**
     * Set a value for one of the armor fields
     *
     * @param type  Which Armor Type Field to set.
     * @param value the new value.
     */
    public void setValue(Armor type, int value) {
        fields[type.ordinal()].setText(Integer.toString(value));
    }

    /**
     * Get the value for one of the armor fields
     *
     * @param type Which Armor Type Field to set.
     * @return the value of field -type-
     */
    public int getValue(Armor type) {
        return Integer.parseInt(fields[type.ordinal()].getText());
    }

    /**
     * Turn the fields on or off, can only do them all, no single selection.
     *
     * @param val true to enable, false to disable.
     */
    public void setEnabled(boolean val) {
        for (Armor at : Armor.values()) {
            fields[at.ordinal()].setEnabled(val);
        }
    }
}