// George Frick
// ObjectView.java
// Area Editor Project, Spring 2002

package net.s5games.mafia.ui;

import net.s5games.mafia.ui.FlagChoice;
import net.s5games.mafia.ui.ObjectTypePanel;
import net.s5games.mafia.ui.JMudNumberField;
import net.s5games.mafia.ui.JMudTextField;
import net.s5games.mafia.model.MudConstants;
import net.s5games.mafia.model.ObjectAffect;
import net.s5games.mafia.model.Area;
import net.s5games.mafia.model.MudObject;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ObjectView extends EditorView implements ActionListener {
    protected JComboBox vnumBox;
    protected JButton newObjectButton;
    protected JButton deleteObjectButton;
    protected JButton nextButton;
    protected JButton previousButton;
    protected JButton extraButton;
    protected JButton duplicateButton;
    protected JTextField nameField;
    protected JTextField shortField;
    protected JTextField longField;
    protected JTextField levelField, conditionField, weightField, costField;
    protected JTextField materialField;
    protected FlagChoice wearFlags, extraFlags;
    protected ObjectTypePanel typePanel;
    protected JAffectPanel affPanel;
    protected JPanel mainPanel;
    protected ObjInPanel inventoryPanel;

    public ObjectView(Area ar) {
        super(ar);

        vnumBox = data.getVnumCombo("obj");
        vnumBox.setBorder(new TitledBorder("Vnum"));

        newObjectButton = new JButton("New");
        deleteObjectButton = new JButton("Delete");
        nextButton = new JButton("Next");
        previousButton = new JButton("Back");
        extraButton = new JButton("Extras");
        duplicateButton = new JButton("Duplicate");

        nameField = new JTextField(20);
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(1, 1));
        namePanel.setBorder(new TitledBorder("Name"));
        namePanel.add(nameField);

        shortField = new JTextField(29);
        JPanel shortPanel = new JPanel();
        shortPanel.setLayout(new GridLayout(1, 1));
        shortPanel.setBorder(new TitledBorder("Short Description"));
        shortPanel.add(shortField);

        longField = new JTextField(40);
        JPanel longPanel = new JPanel();
        longPanel.setLayout(new GridLayout(1, 1));
        longPanel.setBorder(new TitledBorder("Long Description"));
        longPanel.add(longField);

        levelField = new JMudNumberField(8);
        JPanel levelPanel = new JPanel();
        levelPanel.setLayout(new GridLayout(1, 1));
        levelPanel.setBorder(new TitledBorder("Level"));
        levelPanel.add(levelField);

        conditionField = new JMudNumberField(8);
        JPanel conditionPanel = new JPanel();
        conditionPanel.setLayout(new GridLayout(1, 1));
        conditionPanel.setBorder(new TitledBorder("Condition"));
        conditionPanel.add(conditionField);

        weightField = new JMudNumberField(8);
        JPanel weightPanel = new JPanel();
        weightPanel.setLayout(new GridLayout(1, 1));
        weightPanel.setBorder(new TitledBorder("Weight"));
        weightPanel.add(weightField);

        // Create the cost field
        costField = new JMudNumberField(8);
        JPanel costPanel = new JPanel();
        costPanel.setLayout(new GridLayout(1, 1));
        costPanel.setBorder(new TitledBorder("Cost"));
        costPanel.add(costField);

        materialField = new JMudTextField(8);
        JPanel materialPanel = new JPanel();
        materialPanel.setLayout(new GridLayout(1, 1));
        materialPanel.setBorder(new TitledBorder("Material"));
        materialPanel.add(materialField);

        wearFlags = new FlagChoice("Wear Flags", MudConstants.wearNames,
                MudConstants.wearFlags, MudConstants.NUM_WEAR, this, 2);

        extraFlags = new FlagChoice("Extra Flags", MudConstants.extraNames,
                MudConstants.extraFlags, MudConstants.NUM_EXTRA, this, 3);

        typePanel = new ObjectTypePanel(data, theDamTypes, this);
        affPanel = new JAffectPanel();

        inventoryPanel = new ObjInPanel();

        /*******************************************************
         *
         * Begin Layout.
         *
         *******************************************************/

        // this.setLayout(new BorderLayout());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        constraint.insets = new Insets(1, 1, 1, 1);

        // Browser + vnum box
        // sPanel.setLayout(new GridLayout(1,2));
        Box bPanel = Box.createHorizontalBox();
        Box buttonPanel = Box.createHorizontalBox();
        // JPanel buttonPanel = new JPanel();
        // buttonPanel.setLayout(new GridLayout(1,5));
        //buttonPanel.setLayout(new SpringLayout());
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(newObjectButton);
        buttonPanel.add(deleteObjectButton);
        buttonPanel.add(duplicateButton);
        //SpringUtilities.makeCompactGrid(buttonPanel, 1,
        //                          buttonPanel.getComponentCount(),
        //                          1, 1, 1, 1);
        buttonPanel.setBorder(new TitledBorder("Browser"));
        bPanel.add(vnumBox);
        bPanel.add(buttonPanel);

        // this.add(sPanel,BorderLayout.NORTH);
        this.add(bPanel);

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(1, 5));
        //   fieldPanel.add(extraButton);
        //  fieldPanel.add(affectsButton);
        fieldPanel.add(levelPanel);
        fieldPanel.add(conditionPanel);
        fieldPanel.add(weightPanel);
        fieldPanel.add(costPanel);
        fieldPanel.add(materialPanel);

        JPanel row1 = new JPanel();
        row1.setLayout(new GridLayout(1, 2));
        row1.add(namePanel);
        row1.add(shortPanel);
        insertAt(0, 0, 2, 1, row1, true);
        //insertAt(1,1,1,1,shortPanel,true);

        JPanel row2 = new JPanel();
        row2.setLayout(new GridLayout(1, 2));
        row2.add(longPanel);
        row2.add(fieldPanel);
        insertAt(0, 1, 2, 1, row2, true);
        //insertAt(0,2,2,1,longPanel,true);
        //insertAt(0,3,2,1,fieldPanel,true);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(1, 2));
        leftPanel.add(typePanel);
        leftPanel.add(wearFlags);
        // insertAt(0,4,1,1,leftPanel,true);

        JPanel flagPanel = new JPanel();
        flagPanel.setLayout(new GridLayout(1, 3));
        flagPanel.add(leftPanel);
        flagPanel.add(extraFlags);
        flagPanel.add(affPanel);
        insertAt(0, 4, 2, 1, flagPanel, true);

        // this.add(mainPanel,BorderLayout.CENTER);
        // this.add(inventoryPanel,BorderLayout.SOUTH);
        this.add(mainPanel);
        this.add(inventoryPanel);
        addListenStuff();
    }

    public void update() {
        if (data.getObjectCount() == 0) {
            nameField.setText("no object");
            shortField.setText("no object");
            longField.setText("no object");
            levelField.setText("1");
            conditionField.setText("100");
            weightField.setText("1");
            costField.setText("1");
            materialField.setText("Nothing");
            wearFlags.setFlags(0);
            extraFlags.setFlags(0);
            typePanel.update(-1);
            setEnabled(false);
        } else {
            if (vnum == -1 || vnum < data.getLowVnum() || vnum > data.getHighVnum())
                vnum = data.getFirstObjectVnum();

            MudObject temp = (MudObject) vnumBox.getSelectedItem();
            if (temp == null || temp.getVnum() != vnum)
                temp = data.getObject(vnum);

            nameField.setText(temp.getName());
            shortField.setText(temp.getShortDesc());
            longField.setText(temp.getLongDesc());
            levelField.setText(Integer.toString(temp.getLevel()));
            conditionField.setText(Integer.toString(temp.getCondition()));
            weightField.setText(Integer.toString(temp.getWeight()));
            costField.setText(Integer.toString(temp.getCost()));
            materialField.setText(temp.getMaterial());
            wearFlags.setFlags(temp.getWearFlags());
            extraFlags.setFlags(temp.getExtraFlags());
            typePanel.update(vnum);
            affPanel.update(vnum);
            setEnabled(true);
            if (!temp.isContainer())
                inventoryPanel.setEnabled(false);
            else
                inventoryPanel.setEnabled(true);

        }
        vnumBox.setSelectedItem(data.getObject(vnum));
        inventoryPanel.update(vnum);
    }

    public void setEnabled(boolean value) {
        nameField.setEnabled(value);
        shortField.setEnabled(value);
        longField.setEnabled(value);
        levelField.setEnabled(value);
        conditionField.setEnabled(value);
        weightField.setEnabled(value);
        costField.setEnabled(value);
        materialField.setEnabled(value);
        wearFlags.setEnabled(value);
        extraFlags.setEnabled(value);
        typePanel.setEnabled(value);
        inventoryPanel.setEnabled(value);
        vnumBox.setEnabled(value);
        deleteObjectButton.setEnabled(value);
        nextButton.setEnabled(value);
        previousButton.setEnabled(value);
        affPanel.setEnabled(value);
    }

    public void insertAt(int x, int y, int width, int height, Component i) {
        constraint.gridx = x;
        constraint.gridy = y;
        constraint.gridwidth = width;
        constraint.gridheight = height;
        layout.setConstraints(i, constraint);
        mainPanel.add(i);
    }

    class JAffectPanel extends JPanel {
        JButton addButton, removeButton;
        JList affectList;
        JComboBox affectType;
        JTextField affectValue;
        JPanel fieldPanel;

        public JAffectPanel() {
            super(true);
            // affectList = new JList();
            addButton = new JButton("Add/Update");
            removeButton = new JButton("Remove");

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 2));
            buttonPanel.add(addButton);
            buttonPanel.add(removeButton);
            setLayout(new BorderLayout());
            add(buttonPanel, BorderLayout.SOUTH);
            affectType = new JComboBox(ObjectAffect.affectNames);
            affectType.setBorder(new TitledBorder("Type"));
            affectValue = new JTextField(10);
            JPanel affectPanel = new JPanel();
            affectPanel.setLayout(new GridLayout(1, 1));
            affectPanel.setBorder(new TitledBorder("Value"));
            affectPanel.add(affectValue);
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(1, 2));
            fieldPanel.add(affectType);
            fieldPanel.add(affectPanel);
            add(fieldPanel, BorderLayout.NORTH);
//         affectList = new JList(ObjectAffect.affectNames);
            affectList = new JList();
            JScrollPane hPane = new JScrollPane(affectList);
            hPane.setBorder(new TitledBorder("Affects on Object"));
            add(hPane, BorderLayout.CENTER);
            affectList.setEnabled(false);
            addButton.setEnabled(false);
            removeButton.setEnabled(false);
            // setLayout(new BorderLayout());
            //  add(fieldPanel,BorderLayout.NORTH);
            // this.add(affectList,BorderLayout.CENTER);
            //add(buttonPanel,BorderLayout.SOUTH);
            setBorder(new TitledBorder("Affects Management"));

            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ObjectAffect temp;
                    MudObject oTemp = data.getObject(vnum);

                    if (oTemp == null)
                        return;

                    int oType = ObjectAffect.affectFlags[affectType.getSelectedIndex()];
                    int oValue;
                    try {
                        oValue = Integer.parseInt(affectValue.getText());
                    }
                    catch (Exception eeee) {
                        update(vnum);
                        return;
                    }

                    if (affectList.getSelectedValue() != null) {
                        temp = (ObjectAffect) affectList.getSelectedValue();
                        temp.setValue(oValue);
                        temp.setType(oType);
                    } else {
                        temp = new ObjectAffect(oType, oValue);
                        oTemp.addAffect(temp);
                    }
                    update(vnum);
                }
            });

            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ObjectAffect temp = (ObjectAffect) affectList.getSelectedValue();

                    MudObject oTemp = data.getObject(vnum);

                    if (temp == null || oTemp == null)
                        return;

                    oTemp.removeAffect(temp);
                    update(vnum);
                }
            });

            affectList.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    ObjectAffect temp = (ObjectAffect) affectList.getSelectedValue();

                    if (temp == null)
                        return;

                    affectValue.setText(Integer.toString(temp.getValue()));
                    affectType.setSelectedIndex(ObjectAffect.flagToIndex(temp.getType()));
                }
            });

        }

        public void setEnabled(boolean value) {
            addButton.setEnabled(value);
            removeButton.setEnabled(value);
            affectList.setEnabled(value);
            affectValue.setEnabled(value);
            affectType.setEnabled(value);
        }

        public void update(int vnum) {
            if (data.getObjectCount() == 0 || vnum == -1) {
                setEnabled(false);
                return;
            }

            MudObject temp = data.getObject(vnum);

            affectList.setListData(temp.getAffects().toArray(new ObjectAffect[temp.getAffects().size()]));
            affectType.setSelectedIndex(0);
            affectValue.setText("");
        }
    }

    public void actionPerformed(ActionEvent e) {
        MudObject temp = data.getObject(vnum);
        if (temp == null)
            return;
        // set flags.
        temp.setWearFlags(wearFlags.getFlags());
        temp.setExtraFlags(extraFlags.getFlags());
        update();
    }

    private void addListenStuff() {
        nameField.addFocusListener(new MudObjectFocusListener());
        shortField.addFocusListener(new MudObjectFocusListener());
        longField.addFocusListener(new MudObjectFocusListener());
        levelField.addFocusListener(new MudObjectFocusListener());
        conditionField.addFocusListener(new MudObjectFocusListener());
        weightField.addFocusListener(new MudObjectFocusListener());
        costField.addFocusListener(new MudObjectFocusListener());
        materialField.addFocusListener(new MudObjectFocusListener());

        vnumBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MudObject temp = (MudObject) vnumBox.getSelectedItem();
                if (temp == null)
                    return;

                vnum = temp.getVnum();
                update(vnum);
            }
        });

        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (vnum <= data.getFirstObjectVnum())
                    return;

                int temp = vnum - 1;
                while (data.getObject(temp) == null && temp >= data.getFirstObjectVnum())
                    temp--;

                if (temp >= data.getFirstObjectVnum())
                    vnum = temp;

                update(vnum);

            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (vnum >= data.getHighVnum())
                    return;

                int temp = vnum + 1;
                while (data.getObject(temp) == null && temp <= data.getHighVnum())
                    temp++;

                if (temp <= data.getHighVnum())
                    vnum = temp;

                update(vnum);
            }
        });

        duplicateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                * Get the lowest available mob vnum
                */
                int newVnum = data.getFreeObjectVnum();
                if (newVnum == -1) {
                    inform("You are out of vnums!");
                    return;
                }
                MudObject temp = new MudObject(newVnum, data, vnum);
                data.insert(temp);
                update(newVnum);
            }
        });

        newObjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                * Get the lowest available mob vnum
                */
                int vnum = data.getFreeObjectVnum();
                if (vnum == -1) {
                    inform("You are out of vnums!");
                    return;
                }
                MudObject temp = new MudObject(vnum, data);
                data.insert(temp);
                update(vnum);
            }
        });

        deleteObjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!confirm("Please confirm Object deletion. All resets will be lost."))
                    return;

                data.deleteObject(vnum);
                vnum = -1;
                update();
            }
        });

    }

    class ObjInPanel extends JPanel {
        JList items;
        JComboBox list;
        JScrollPane pane;
        JButton addButton, removeButton;
        JButton clearButton;
        int myVnum;

        public ObjInPanel() {
            this.setLayout(new BorderLayout());

            myVnum = -1;
            items = new JList();
            items.setBorder(new TitledBorder("Object Inventory"));
            items.setSelectionModel(new ToggleSelectionModel());
            list = new JComboBox();
            list.setBorder(new TitledBorder("Object to add"));
            pane = new JScrollPane(items);

            addButton = new JButton("Add Object");
            removeButton = new JButton("Remove Object");
            clearButton = new JButton("Clear Objects");
            JPanel panel1 = new JPanel();
            panel1.setLayout(new GridLayout(2, 2));
            panel1.add(addButton);
            panel1.add(clearButton);
            panel1.add(removeButton);

            this.add(pane, BorderLayout.CENTER);

            JPanel panel2 = new JPanel();
            panel2.setLayout(new GridLayout(2, 1));
            panel2.add(list);
            // panel2.add(clearButton);
            panel2.add(panel1);
            this.add(panel2, BorderLayout.EAST);

            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    MudObject obj = data.getObject(vnum);
                    MudObject myObj = (MudObject) list.getSelectedItem();

                    if (obj == null || myObj == null) {
                        System.out.println("problem" + Integer.toString(vnum));
                        return;
                    }

                    // a few checks.
                    if (obj.isFull()) {
                        inform("Cannot add object, container is full.");
                        return;
                    }

                    if (myObj.isContainer()) {
                        inform("Cannot add object, can't put containers in containers.");
                        return;
                    }

                    obj.addObject(myObj);
                    items.setListData(obj.getInventory().toArray(new MudObject[obj.getInventory().size()]));
                    update();
                }
            });

            clearButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    MudObject obj = data.getObject(vnum);
                    if (obj == null)
                        return;
                    obj.getInventory().clear();
                    items.setListData(obj.getInventory().toArray(new MudObject[obj.getInventory().size()]));
                    update();
                }
            });
            // get highlighted item from Jlist, if non highlighted, do nothing,
            // else remove from mobile inventory and update jlist.
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    MudObject myObj = (MudObject) items.getSelectedValue();
                    MudObject obj = data.getObject(vnum);
                    if (obj == null || myObj == null)
                        return;

                    obj.removeObject(myObj);
                    items.setListData(obj.getInventory().toArray(new MudObject[obj.getInventory().size()]));
                    update();
                }
            });

            this.setBorder(new TitledBorder("Inventory"));
            pane.setPreferredSize(pane.getPreferredSize());
        }

        public void setEnabled(boolean enable) {
            addButton.setEnabled(enable);
            removeButton.setEnabled(enable);
            clearButton.setEnabled(enable);
            items.setEnabled(enable);
            list.setEnabled(enable);

            if (vnum == -1 || data.getObjectCount() == 0)
                return;

            MudObject obj = data.getObject(vnum);

            if (obj == null) {
                System.out.println("Bad obj in inventory panel");
                return;
            }

            if (obj.getInventory().size() == 0) {
                clearButton.setEnabled(false);
                removeButton.setEnabled(false);
            }

            if (items.getSelectedValue() == null) {
                removeButton.setEnabled(false);
            }
        }

        public void update() {
            if (data.getObjectCount() > 0)
                setEnabled(true);
            else
                setEnabled(false);
        }

        public void update(int v) {
            if (myVnum == v)
                return;

            myVnum = v;
            if (myVnum == -1)
                return;

            MudObject obj = data.getObject(myVnum);

            if (obj == null) {
                System.out.println("Bad obj in inventory panel");
                return;
            }

            if (!obj.isContainer())
                this.setEnabled(false);
            else
                this.setEnabled(true);

            populate();
            items.setListData(obj.getInventory().toArray(new MudObject[obj.getInventory().size()]));
        }

        private void populate() {
            MudObject temp;
            int wear;

            list.removeAllItems();

            for (int loop = data.getLowVnum(); loop <= data.getHighVnum(); loop++) {
                temp = data.getObject(loop);

                if (temp == null)
                    continue;

                wear = temp.getWearFlags();

                // unchoosable if no take flag.
                if ((wear & MudConstants.ITEM_TAKE) != MudConstants.ITEM_TAKE)
                    continue;

                list.addItem(temp);
            }
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

    class MudObjectFocusListener implements FocusListener {
        public void focusGained(FocusEvent e) {
        }

        public void focusLost(FocusEvent e) {
            MudObject obj = data.getObject(vnum);
            if (obj == null)
                return;

            String oldS, newS;
            int oldVal, newVal;

            JTextField field = (JTextField) e.getSource();

            newS = field.getText();
            if (newS.equals("")) {
                update();
                return;
            }

            if (field == nameField) {
                oldS = obj.getName();

                if (newS.equals(oldS))
                    return;
                else
                    obj.setName(newS);

                vnumBox.updateUI();
            } else if (field == shortField) {
                oldS = obj.getShortDesc();

                if (newS.equals(oldS))
                    return;
                else
                    obj.setShortName(newS);
            } else if (field == longField) {
                oldS = obj.getLongDesc();

                if (newS.equals(oldS))
                    return;
                else
                    obj.setLongName(newS);
            } else if (field == materialField) {
                oldS = obj.getMaterial();

                if (newS.equals(oldS))
                    return;
                else
                    obj.setMaterial(newS);
            } else {
                try {
                    newVal = Integer.parseInt(newS);
                }
                catch (Exception evt2) {
                    newVal = 0;
                }
                    if (newVal <= 0) {
                        update();
                        return;
                    }
                    if (field == levelField) {
                        oldVal = obj.getLevel();

                        if (oldVal == newVal)
                            return;
                        else
                            obj.setLevel(newVal);
                    } else if (field == conditionField) {
                        oldVal = obj.getCondition();

                        if (oldVal == newVal)
                            return;
                        else
                            obj.setCondition(newVal);
                    } else if (field == costField) {
                        oldVal = obj.getCost();

                        if (oldVal == newVal)
                            return;
                        else
                            obj.setCost(newVal);
                    } else if (field == weightField) {
                        oldVal = obj.getWeight();

                        if (oldVal == newVal)
                            return;
                        else
                            obj.setWeight(newVal);
                    }

            }
            update();
        }  // end of focus lost.

    } // end focus class
}