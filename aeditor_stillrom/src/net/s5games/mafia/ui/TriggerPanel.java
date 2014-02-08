package net.s5games.mafia.ui;

import net.s5games.mafia.model.MobTrigger;
import net.s5games.mafia.model.MudConstants;
import net.s5games.mafia.model.MobileProgram;
import net.s5games.mafia.model.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * George Frick
 *
 */

public class TriggerPanel extends JPanel {
    Area area;
    int vnum;
    Mobile mob;

    JComboBox typeBox, mprogBox;
    JTextField phraseField;
    JList currentList;
    JButton addButton, removeButton;
    String[] noDataString = {"Currently there are no", "triggers on this mobile."};

    public TriggerPanel(int vnum, Area data) {
        area = data;
        this.vnum = vnum;

        mob = data.getMobile(vnum);
        if (mob == null) {
            System.out.println("Bad mobile in trigger menu!");
            return;
        }

        addButton = new JButton("Add/Update");
        removeButton = new JButton("Remove");
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new GridLayout(1, 2));
        bPanel.add(addButton);
        bPanel.add(removeButton);

        typeBox = new JComboBox(MudConstants.triggerNames);
        mprogBox = new JComboBox(area.getMobprogs().toArray(new MobileProgram[area.getMobprogs().size()]));
        typeBox.setBorder(new TitledBorder("Type"));
        mprogBox.setBorder(new TitledBorder("Program to run"));
        JPanel cPanel = new JPanel();
        cPanel.setLayout(new BorderLayout());
        cPanel.add(typeBox, BorderLayout.WEST);
        cPanel.add(mprogBox, BorderLayout.CENTER);

        phraseField = new JTextField(45);
        phraseField.setBorder(new TitledBorder("Phrase/Chance/etc"));
        phraseField.setColumns(19);
        JPanel pPanel = new JPanel();
        pPanel.setLayout(new GridLayout(2, 1));
        pPanel.add(cPanel);
        pPanel.add(phraseField);

        if (mob.getTriggers().size() == 0)
            currentList = new JList(noDataString);
        else
            currentList = new JList(mob.getTriggers().toArray(new MobTrigger[mob.getTriggers().size()]));

        currentList.setBorder(new TitledBorder("Current Triggers"));
        JScrollPane listPane = new JScrollPane(currentList);

        setLayout(new BorderLayout());
        add(pPanel, BorderLayout.NORTH);
        add(listPane, BorderLayout.CENTER);
        add(bPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MobTrigger temp;

                int mType = MudConstants.triggerFlags[typeBox.getSelectedIndex()];
                MobileProgram mTemp = (MobileProgram) mprogBox.getSelectedItem();
                int mVnum = mTemp.getVnum();
                String mPhrase = phraseField.getText();

                if (currentList.getSelectedValue() != null) {
                    temp = (MobTrigger) currentList.getSelectedValue();
                    temp.setType(mType);
                    temp.setVnum(mVnum);
                    temp.setPhrase(mPhrase);
                } else {
                    temp = new MobTrigger(mType, mVnum, mPhrase);
                    mob.addTrigger(temp);
                }
                currentList.setListData(mob.getTriggers().toArray(new MobTrigger[mob.getTriggers().size()]));
                phraseField.setText("");
                typeBox.setSelectedIndex(0);
                mprogBox.setSelectedIndex(0);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MobTrigger temp = (MobTrigger) currentList.getSelectedValue();

                if (temp == null)
                    return;

                mob.removeTrigger(temp);
                if (mob.getTriggers().size() == 0)
                    currentList.setListData(noDataString);
                else
                    currentList.setListData(mob.getTriggers().toArray(new MobTrigger[mob.getTriggers().size()]));

                phraseField.setText("");
                typeBox.setSelectedIndex(0);
                mprogBox.setSelectedIndex(0);
            }
        });

        currentList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                MobTrigger temp = (MobTrigger) currentList.getSelectedValue();

                if (temp == null)
                    return;

                phraseField.setText(temp.getPhrase());
                typeBox.setSelectedItem(MobTrigger.sLookup(temp.getType()));
                mprogBox.setSelectedItem(area.getMProgByVnum(temp.getVnum()));
            }
        });
    }
}