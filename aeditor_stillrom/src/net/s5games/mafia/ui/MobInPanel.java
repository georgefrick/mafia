package net.s5games.mafia.ui;

import net.s5games.mafia.model.MudConstants;
import net.s5games.mafia.ui.EditorView;
import net.s5games.mafia.model.Area;
import net.s5games.mafia.model.Mobile;
import net.s5games.mafia.model.MudObject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
/*
 * George Frick
 * Custom component for a screen to equip a mobile.
 */

public class MobInPanel extends JPanel {

    Area area;
    int vnum;
    JLabel label1;
    JList items;
    JComboBox list;
    JScrollPane pane;
    JButton addButton, removeButton;
    JButton clearButton;
    JCheckBox isShop;
    Mobile mob;
    EditorView myParent;


    public MobInPanel(Area data, EditorView p) {
        area = data;
        vnum = -1;

        this.setLayout(new BorderLayout());

        myParent = p;
        isShop = new JCheckBox("ShopKeeper?");
        items = new JList();
        items.setBorder(new TitledBorder("Mobile Inventory"));
        items.setSelectionModel(new ToggleSelectionModel());
        list = new JComboBox();
        list.setBorder(new TitledBorder("Object to add"));
        pane = new JScrollPane(items);

        addButton = new JButton("Add Object");
        removeButton = new JButton("Remove Object");
        clearButton = new JButton("Clear objects");
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 2));
        panel1.add(addButton);
        panel1.add(clearButton);
        panel1.add(removeButton);
        panel1.add(isShop);

        this.add(pane, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2, 1));
        panel2.add(list);
        panel2.add(panel1);
        this.add(panel2, BorderLayout.EAST);

        isShop.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                mob = area.getMobile(vnum);
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    // remove shop

                    mob.removeShop();
                    myParent.update();
                } else if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (mob.getShop() == null)
                        mob.addShop();

                    myParent.update();
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mob = area.getMobile(vnum);
                mob.getInventory().clear();
                update();
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mob = area.getMobile(vnum);

                if (mob == null) {
                    System.out.println("problem" + Integer.toString(vnum));
                    return;
                }

                MudObject myObj = (MudObject) list.getSelectedItem();

                if (myObj == null) {
                    System.out.println("problem 2");
                    return;
                }

                mob.giveToMobile(myObj);
                update(vnum);
            }
        });

        // get highlighted item from Jlist, if non highlighted, do nothing,
        // else remove from mobile inventory and update jlist.
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MudObject myObj = (MudObject) items.getSelectedValue();
                if (myObj == null)
                    return;

                mob.takeFromMobile(myObj);
                update(vnum);
            }
        });

        // this.setBorder(new TitledBorder("Inventory Manager"));
        pane.setPreferredSize(pane.getPreferredSize());
    }

    private void update() {
        if (area.getObjectCount() > 0)
            setEnabled(true);
        else
            setEnabled(false);

        items.validate();
        list.validate();
        validate();
        repaint();
    }

    public void update(int v) {
        vnum = v;

        if (vnum == -1) {
            update();
            return;
        }

        mob = area.getMobile(vnum);

        if (mob == null) {
            System.out.println("Bad mob in inventory panel");
            return;
        }
        items.setListData(mob.getInventory().toArray(new MudObject[mob.getInventory().size()]));
        populate();
        if (mob.getShop() != null)
            isShop.setSelected(true);

        update();
    }

    private void populate() {
        MudObject temp;
        int wear = 0;

        list.removeAllItems();

        for (int loop = area.getLowVnum(); loop <= area.getHighVnum(); loop++) {
            temp = area.getObject(loop);

            if (temp == null)
                continue;

            wear = temp.getWearFlags();

            // unchoosable if no take flag.
            if ((wear & MudConstants.ITEM_TAKE) != MudConstants.ITEM_TAKE)
                continue;

            list.addItem(temp);
        }
    }

    public void setEnabled(boolean enable) {
        addButton.setEnabled(enable);
        removeButton.setEnabled(enable);
        clearButton.setEnabled(enable);
        list.setEnabled(enable);
        items.setEnabled(enable);

        if (vnum == -1 || area.getMobCount() == 0)
            return;

        mob = area.getMobile(vnum);

        if (mob == null) {
            System.out.println("Bad mob in inventory panel");
            return;
        }

        if (mob.getInventory().size() == 0) {
            clearButton.setEnabled(false);
            removeButton.setEnabled(false);
            items.removeAll(); // insurance.
        }

        if (items.getSelectedValue() == null) {
            removeButton.setEnabled(false);
        }

    }

    class ToggleSelectionModel extends DefaultListSelectionModel {
        boolean gestureStarted = false;

        public ToggleSelectionModel() {
            setSelectionMode(SINGLE_SELECTION);
        }

        public void setSelectionInterval(int index0, int index1) {
            // int oldIndex = getMinSelectionIndex();

            if (isSelectedIndex(index0) && !gestureStarted)
                super.removeSelectionInterval(index0, index1);
            else
                super.setSelectionInterval(index0, index1);

            gestureStarted = true;

            //int newIndex = getMinSelectionIndex();
            //     if (oldIndex != newIndex)
            update();
        }

        public void setValueIsAdjusting(boolean isAdjusting) {
            if (!isAdjusting)
                gestureStarted = false;
        }
    }
}