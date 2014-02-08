// George Frick
// MobView.java
// Area Editor Project, Spring 2002
package net.s5games.mafia.ui;

import net.s5games.mafia.beans.Armor;
import net.s5games.mafia.beans.Dice;
import net.s5games.mafia.model.*;
import net.s5games.mafia.ui.armor.ArmorField;
import net.s5games.mafia.ui.armor.ArmorForm;
import net.s5games.mafia.ui.dice.DiceField;
import net.s5games.mafia.ui.dice.DiceForm;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * The mob view collects together all of the fields and handles the form actions.sss
 */
public class MobView extends EditorView implements ActionListener, ArmorForm, DiceForm {
    private RaceTable theRaces;
    private JTextArea desc;
    private JScrollPane descHolder;
    private JTextField fields[];
    private JButton newMobButton;
    private JButton deleteMobButton;
    private JButton nextButton;
    private JButton previousButton;
    private JButton triggerButton;
    private JButton duplicateButton;
    private JButton shopButton;
    private JTextField levelField;
    private JTextField densityField;
    private JComboBox alignmentBox;
    private JComboBox sexBox;
    private JComboBox raceBox;
    private FlagChoice actFlags;
    private JTextField groupField;
    private JTextField hitRollField;
    private JComboBox damTypeBox;
    private JTabbedPane tabPane;
    private DiceField hitDice;
    private DiceField damageDice;
    private DiceField manaDice;
    private ArmorField armorField;
    private FlagChoice affectChoice;
    private SizeChooser sizeChoice;
    private JTextField mobMaterial;
    private JTextField mobWealth;
    private JTextField mobDeathCry;
    private JComboBox defaultPos;
    private JComboBox startPos;
    private JComboBox vnumBox;
    private IRVChoice irvPanel;
    private FlagChoice partChoice, formChoice, offenseChoice;
    private JPanel mainPanel;
    private MobEqPanel2 eqPanel;
    private MobInPanel inventoryPanel;
    private TitledBorder vnumBorder;


    public MobView(Area ar) {
        super(ar);

        theRaces = RaceTable.Instance();
        theRaces.loadRaceTable("race.txt");

        tabPane = new JTabbedPane();
        fields = new JTextField[4]; // [vnum], short, short, long, desc.

        constraint.insets = new Insets(0, 0, 0, 0);

        // Name
        fields[1] = new JTextField(20);
        JPanel f1panel = new JPanel();
        f1panel.setLayout(new GridLayout(1, 1));
        f1panel.setBorder(new TitledBorder("Name"));
        f1panel.add(fields[1]);

        // Short
        fields[2] = new JTextField(29);
        JPanel f2panel = new JPanel();
        f2panel.setLayout(new GridLayout(1, 1));
        f2panel.setBorder(new TitledBorder("Short Description"));
        f2panel.add(fields[2]);

        // Long
        fields[3] = new JTextField(50);
        JPanel f3panel = new JPanel();
        f3panel.setLayout(new GridLayout(1, 1));
        f3panel.setBorder(new TitledBorder("Long Description"));
        f3panel.add(fields[3]);

        fields[1].addFocusListener(new StringFieldFocusListener());
        fields[2].addFocusListener(new StringFieldFocusListener());
        fields[3].addFocusListener(new StringFieldFocusListener());

        // Vnum box
        vnumBox = data.getVnumCombo("mob");
        vnumBorder = new TitledBorder("Vnum");
        vnumBox.setBorder(vnumBorder);

        newMobButton = new JButton("New");
        deleteMobButton = new JButton("Delete");
        nextButton = new JButton("Next");
        previousButton = new JButton("Back");
        triggerButton = new JButton("Triggers");
        duplicateButton = new JButton("Duplicate");
        shopButton = new JButton("Shop");

        levelField = new JMudNumberField(8);
        JPanel levelPanel = new JPanel();
        levelPanel.setLayout(new GridLayout(1, 1));
        levelPanel.setBorder(new TitledBorder("Level"));
        levelPanel.add(levelField);
        levelField.addFocusListener(new StringFieldFocusListener());
        levelField.setToolTipText("The mobiles level, same as a players.");

        densityField = new JTextField(8);
        JPanel densityPanel = new JPanel();
        densityPanel.setLayout(new GridLayout(1, 1));
        densityPanel.setBorder(new TitledBorder("Density"));
        densityPanel.add(densityField);
        densityField.setEditable(false);
        densityField.setToolTipText("Displays how many of this mob are reset in the model.");

        sexBox = new JComboBox(MudConstants.sexArray);
        sexBox.setBorder(new TitledBorder("Sex"));

        alignmentBox = new JComboBox(MudConstants.alignmentArray);
        alignmentBox.setBorder(new TitledBorder("Alignment"));
        alignmentBox.setToolTipText("Selecting the mobiles role, from satanic to angelic");

        raceBox = theRaces.getRaceComboBox();
        raceBox.setBorder(new TitledBorder("Race"));

        groupField = new JMudNumberField(8);
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new GridLayout(1, 1));
        groupPanel.setBorder(new TitledBorder("Group"));
        groupPanel.add(groupField);
        groupField.addFocusListener(new StringFieldFocusListener());

        hitRollField = new JMudNumberField(8);
        JPanel hitRollPanel = new JPanel();
        hitRollPanel.setLayout(new GridLayout(1, 1));
        hitRollPanel.setBorder(new TitledBorder("HitRoll"));
        hitRollPanel.add(hitRollField);
        hitRollField.addFocusListener(new StringFieldFocusListener());

        damTypeBox = theDamTypes.getDamTypeComboBox();
        damTypeBox.setBorder(new TitledBorder("DamType"));

        actFlags = new FlagChoice(null, MudConstants.actFlagNames,
                MudConstants.actFlags, MudConstants.NUM_ACT_FLAGS,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Mobile temp = data.getMobile(vnum);
                        if (temp == null)
                            return;

                        temp.setActFlags(actFlags.getFlags());
                        updateIRV();
                    }
                }, 7);

        hitDice = new DiceField("Hit", this);
        damageDice = new DiceField("Damage", this);
        manaDice = new DiceField("Mana", this);
        armorField = new ArmorField(this);
        armorField.addFocusListener(new StringFieldFocusListener());

        affectChoice = new FlagChoice(null, MudConstants.affectFlagNames,
                MudConstants.affectFlags, MudConstants.NUM_AFFECTS,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Mobile temp = data.getMobile(vnum);
                        if (temp == null)
                            return;

                        temp.setAffectedBy(affectChoice.getFlags());
                        updateIRV();
                    }
                }, 6);

        irvPanel = new IRVChoice("Immune/Resist/Vulnerable", MudConstants.IRVNames,
                MudConstants.IRVFlags, MudConstants.NUM_IRV,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Mobile temp = data.getMobile(vnum);
                        if (temp == null)
                            return;

                        //    if( temp.getImmunityFlags() != irvPanel.getFlags(0) )
                        temp.setImmunityFlags(irvPanel.getFlags(0));
                        //     else if( temp.getResistanceFlags() != irvPanel.getFlags(1) )
                        temp.setResistanceFlags(irvPanel.getFlags(1));
                        //     else if( temp.getVulnerableFlags() != irvPanel.getFlags(2) )
                        temp.setVulnerabilityFlags(irvPanel.getFlags(2));

                        updateIRV();
                    }
                }, 6);

        partChoice = new FlagChoice(null, MudConstants.partNames,
                MudConstants.partFlags, MudConstants.NUM_PARTS,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Mobile temp = data.getMobile(vnum);
                        if (temp == null)
                            return;

                        temp.setParts(partChoice.getFlags());
                        updateIRV();
                    }
                }, 8);

        formChoice = new FlagChoice(null, MudConstants.formNames,
                MudConstants.formFlags, MudConstants.NUM_FORMS,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Mobile temp = data.getMobile(vnum);
                        if (temp == null)
                            return;

                        temp.setForm(formChoice.getFlags());
                        updateIRV();
                    }
                }, 7);

        offenseChoice = new FlagChoice(null, MudConstants.offenseNames,
                MudConstants.offenseFlags, MudConstants.NUM_OFFENSE,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Mobile temp = data.getMobile(vnum);
                        if (temp == null)
                            return;

                        temp.setOffensiveFlags(offenseChoice.getFlags());
                        updateIRV();
                    }
                }, 6);

        desc = new JTextArea(5, 20);
        desc.addFocusListener(new StringFieldFocusListener());
        descHolder = new JScrollPane(desc);
        descHolder.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        descHolder.setPreferredSize(desc.getPreferredSize());

        sizeChoice = new SizeChooser();

        mobMaterial = new JMudTextField(8);
        JPanel materialPanel = new JPanel();
        materialPanel.setLayout(new GridLayout(1, 1));
        materialPanel.setBorder(new TitledBorder("Material"));
        materialPanel.add(mobMaterial);
        mobMaterial.addFocusListener(new StringFieldFocusListener());

        mobWealth = new JMudNumberField(8);
        JPanel wealthPanel = new JPanel();
        wealthPanel.setLayout(new GridLayout(1, 1));
        wealthPanel.setBorder(new TitledBorder("Wealth"));
        wealthPanel.add(mobWealth);
        mobWealth.addFocusListener(new StringFieldFocusListener());

        mobDeathCry = new JMudTextField(20);
        JPanel deathCryPanel = new JPanel();
        deathCryPanel.setLayout(new GridLayout(1, 1));
        deathCryPanel.setBorder(new TitledBorder("Death Cry"));
        deathCryPanel.add(mobDeathCry);
        mobDeathCry.addFocusListener(new StringFieldFocusListener());

        defaultPos = new JComboBox(MudConstants.positionNames);
        defaultPos.setBorder(new TitledBorder("Default Pos."));

        startPos = new JComboBox(MudConstants.positionNames);
        startPos.setBorder(new TitledBorder("Start. Pos."));

        inventoryPanel = new MobInPanel(data, this);

        /*******************************************************
         *
         * Layout.
         *
         *******************************************************/
        this.setLayout(new BorderLayout());
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        // start adding components

        // ROW 1 start. X = 0, Y = 0
        JPanel row1 = new JPanel();
        //row1.setLayout(new GridLayout(1,6));
        row1.setLayout(new SpringLayout());
        row1.add(raceBox);
        row1.add(sexBox);
        row1.add(startPos);
        row1.add(defaultPos);
        row1.add(alignmentBox);
        row1.add(sizeChoice);
        row1.add(damTypeBox);
        SpringUtilities.makeCompactGrid(row1, 1,
                row1.getComponentCount(),
                1, 1, 1, 5);
        insertAt(0, 0, 2, 1, row1, true, true);

        // Row 2.
        insertAt(0, 1, 1, 1, f1panel, true);
        insertAt(1, 1, 1, 1, f2panel, true);

        // Row 3
        insertAt(0, 2, 2, 1, f3panel, true);

        // row 4
        insertAt(0, 3, 2, 1, deathCryPanel, true);

        // row 5
        JPanel lh = new JPanel();
        lh.setLayout(new GridLayout(1, 5));
        lh.add(levelPanel);
        lh.add(hitRollPanel);
        lh.add(groupPanel);
        lh.add(wealthPanel);
        lh.add(materialPanel);
        //lh.add(damTypeBox);
        insertAt(0, 4, 2, 1, lh, true);

        // Browswer
        JPanel sPanel = new JPanel();
        sPanel.setLayout(new GridLayout(1, 2));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new SpringLayout());
        //buttonPanel.setLayout(new GridLayout(1,5));
        buttonPanel.setBorder(new TitledBorder("Browser"));
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(newMobButton);
        buttonPanel.add(deleteMobButton);
        buttonPanel.add(triggerButton);
        buttonPanel.add(duplicateButton);
        buttonPanel.add(shopButton);
        SpringUtilities.makeCompactGrid(buttonPanel, 1,
                buttonPanel.getComponentCount(),
                1, 1, 1, 1);
        sPanel.add(vnumBox);
        sPanel.add(buttonPanel);

        insertAt(0, 5, 2, 2, inventoryPanel, true);

        JPanel comPanel = new JPanel();
        //comPanel.setLayout(new SpringLayout());
        comPanel.setLayout(new GridLayout(2, 2));
        comPanel.add(hitDice);
        comPanel.add(damageDice);
        comPanel.add(manaDice);
        comPanel.add(armorField);
        //   SpringUtilities.makeCompactGrid(comPanel, 1,
        //                              comPanel.getComponentCount(),
        //                             1, 1, 1, 1);
        JPanel actoffPanel = new JPanel();
        actoffPanel.setLayout(new GridLayout(2, 1));
        actoffPanel.add(actFlags);
        actoffPanel.add(offenseChoice);
        tabPane.addTab("Act & Offense", null, actoffPanel, "mobile act and offense flags");
        tabPane.addTab("Dice & Armor", null, comPanel, "mobile hit/mana dice");
        tabPane.addTab("Affect Flags", null, affectChoice, "Mobile Affected By Flags");
        tabPane.addTab("Description", null, descHolder, "Mobile description");
        tabPane.addTab("Immune/Resist/Vulnerable", null, irvPanel, "Mobiles immune/resist/vulnerable flags");
        //  tabPane.addTab("Immunity",null,immChoice,"Mobile immunity");
        //  tabPane.addTab("Resistance",null,resChoice,"Mobile resistance");
        //  tabPane.addTab("Vulnerability",null,vulnChoice,"Mobile vulnerability");
        JPanel partformPanel = new JPanel();
        partformPanel.setLayout(new GridLayout(2, 1));
        partformPanel.add(partChoice);
        partformPanel.add(formChoice);
        tabPane.addTab("Parts", null, partformPanel, "Mobile parts");
        //  tabPane.addTab("Form",null,formChoice,"Mobile Form");
        //    tabPane.addTab("Offensive",null,offenseChoice,"Mobile offensive flags");

        eqPanel = new MobEqPanel2(data);
        eqPanel.setBorder(new TitledBorder("Equipment:"));
        JPanel holdeq = new JPanel();
        holdeq.setLayout(new FlowLayout());
        holdeq.add(eqPanel);
        this.add(holdeq, BorderLayout.WEST);
        this.add(sPanel, BorderLayout.NORTH);
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BorderLayout());
        subPanel.add(mainPanel, BorderLayout.NORTH);
        subPanel.add(tabPane, BorderLayout.SOUTH);
        this.add(subPanel, BorderLayout.CENTER);

        addListeners();
        vnum = -1;
        eqPanel.reset();
        update();
    }

    public void insertAt(int x, int y, int width, int height, Component i) {
        constraint.gridx = x;
        constraint.gridy = y;
        constraint.gridwidth = width;
        constraint.gridheight = height;
        layout.setConstraints(i, constraint);
        mainPanel.add(i);
    }

    public void update() {
        if (data.getMobCount() == 0) {
            // set defaults.
            groupField.setText("0");
            levelField.setText("0");
            densityField.setText("0");
            hitRollField.setText("0");
            mobWealth.setText("0");
            mobMaterial.setText("nothing");
            fields[1].setText("no mob loaded");
            fields[2].setText("no mob loaded");
            fields[3].setText("no mob loaded");
            desc.setText("no mob loaded");
            mobDeathCry.setText("no mob loaded");
            // theDice.update();
            actFlags.setFlags(0);
            affectChoice.setFlags(0);
            offenseChoice.setFlags(0);
            partChoice.setFlags(0);
            formChoice.setFlags(0);
            irvPanel.setFlags(0, 0);
            irvPanel.setFlags(0, 1);
            irvPanel.setFlags(0, 2);
            shopButton.setEnabled(false);
            setEnabled(false);
        } else {
            if (vnum == -1 || vnum < data.getLowVnum() || vnum > data.getHighVnum())
                vnum = data.getFirstMobVnum();

            Mobile temp = (Mobile) vnumBox.getSelectedItem();
            if (temp == null || temp.getVnum() != vnum)
                temp = data.getMobile(vnum);

            setEnabled(true);
            sexBox.setSelectedIndex(temp.getSex());
            startPos.setSelectedIndex(MudConstants.arrayLookupPos(temp.getStartPosition()));
            defaultPos.setSelectedIndex(MudConstants.arrayLookupPos(temp.getDefaultPosition()));

            sizeChoice.setCurrentSize(temp.getSize());
            alignmentBox.setSelectedIndex(MudConstants.arrayAlignmentLookup(temp.getAlignment()));

            fields[1].setText(temp.getName());
            fields[2].setText(temp.getShortDesc());
            fields[3].setText(temp.getLongDesc());
            mobDeathCry.setText(temp.getDeathCry());
            levelField.setText(Integer.toString(temp.getLevel()));
            densityField.setText(Integer.toString(temp.getMax()));
            hitRollField.setText(Integer.toString(temp.getHitRoll()));
            groupField.setText(Integer.toString(temp.getGroup()));
            mobWealth.setText(Integer.toString(temp.getWealth()));
            // 4damtype
            damTypeBox.setSelectedItem(temp.getDamageType());
            mobMaterial.setText(temp.getMaterial());
            raceBox.setSelectedItem(temp.getRace());
            desc.setText(temp.getDescription());
            updateIRV();

            for (Armor at : Armor.values()) {
                armorField.setValue(at, temp.getAC(at));
            }

            hitDice.setValue(temp.getHitDice());
            damageDice.setValue(temp.getDamageDice());
            manaDice.setValue(temp.getManaDice());

            if (temp.getShop() != null)
                shopButton.setEnabled(true);
            else
                shopButton.setEnabled(false);

            if (data.getMprogCount() > 0)
                triggerButton.setEnabled(true);
            else
                triggerButton.setEnabled(false);

            if (temp.getShop() != null)
                vnumBorder.setTitle("Vnum - ShopKeeper ");
            else
                vnumBorder.setTitle("Vnum");

        }
        vnumBox.setSelectedItem(data.getMobile(vnum));
        eqPanel.update();
        inventoryPanel.update(vnum);
        this.validate();
        eqPanel.validate();
        repaint();
    }

    public void setEnabled(boolean value) {
        groupField.setEnabled(value);
        levelField.setEnabled(value);
        densityField.setEnabled(value);
        hitRollField.setEnabled(value);
        mobWealth.setEnabled(value);
        mobMaterial.setEnabled(value);
        fields[1].setEnabled(value);
        fields[2].setEnabled(value);
        fields[3].setEnabled(value);
        desc.setEnabled(value);
        mobDeathCry.setEnabled(value);
        hitDice.setEnabled(value);
        damageDice.setEnabled(value);
        manaDice.setEnabled(value);
        actFlags.setEnabled(value);
        affectChoice.setEnabled(value);
        offenseChoice.setEnabled(value);
        partChoice.setEnabled(value);
        formChoice.setEnabled(value);
        //immChoice.setEnabled(value);
        //resChoice.setEnabled(value);
        //vulnChoice.setEnabled(value);
        irvPanel.setEnabled(value);
        triggerButton.setEnabled(value);
        sexBox.setEnabled(value);
        startPos.setEnabled(value);
        defaultPos.setEnabled(value);
        sizeChoice.setEnabled(value);
        alignmentBox.setEnabled(value);
        raceBox.setEnabled(value);
        deleteMobButton.setEnabled(value);
        nextButton.setEnabled(value);
        previousButton.setEnabled(value);
        damTypeBox.setEnabled(value);
        for (int a = 0; a < 6; a++) {
            tabPane.setEnabledAt(a, value);
        }
        vnumBox.setEnabled(value);
        eqPanel.setEnabled(value);
        inventoryPanel.setEnabled(value);
        armorField.setEnabled(value);
    }

    private void updateIRV() {
        Mobile temp = (Mobile) vnumBox.getSelectedItem();
        if (temp == null || temp.getVnum() != vnum)
            temp = data.getMobile(vnum);

        actFlags.setFlags(temp.getActFlags());
        affectChoice.setFlags(temp.getAffectedBy());
        offenseChoice.setFlags(temp.getOffensiveFlags());
        partChoice.setFlags(temp.getParts());
        formChoice.setFlags(temp.getForm());
        irvPanel.setFlags(temp.getImmunityFlags(), 0);
        irvPanel.setFlags(temp.getResistanceFlags(), 1);
        irvPanel.setFlags(temp.getVulnerabilityFlags(), 2);
    }

    private void addListeners() {
        sexBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Mobile temp = data.getMobile(vnum);
                if (temp == null)
                    return;

                temp.setSex(sexBox.getSelectedIndex());
                update();
            }
        });
        alignmentBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Mobile temp = data.getMobile(vnum);
                if (temp == null)
                    return;

                temp.setAlignment(MudConstants.alignmentLookup(alignmentBox.getSelectedIndex()));
                update();
            }
        });


        raceBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Mobile temp = data.getMobile(vnum);
                if (temp == null)
                    return;

                if (temp.getRace() != (Race) raceBox.getSelectedItem()) {
                    temp.setRace((Race) raceBox.getSelectedItem());
                }

                updateIRV();
            }
        });

        shopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = -1;
                int errornum = -1;
                Mobile mob = data.getMobile(vnum);
                if (mob == null)
                    return;

                Object[] options = {"CLOSE"};
                do {
                    errornum = -1;
                    choice = JOptionPane.showOptionDialog(null, mob.getShop().getPanel(), "Shop Data for: " + mob.getShortDesc(),
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);

                } while (errornum == 1);
            }
        });

        duplicateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int newVnum = data.getFreeMobileVnum();
                if (newVnum == -1) {
                    inform("You are out of vnums!");
                    return;
                }
                Mobile temp = new Mobile(newVnum, data, vnum);
                data.insert(temp);
                vnum = newVnum;
                update();
            }
        });

        triggerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TriggerPanel mp = new TriggerPanel(vnum, data);
                int choice = -1;
                int errornum = -1;
                Mobile mob = data.getMobile(vnum);
                if (mob == null)
                    return;

                Object[] options = {"CLOSE"};
                do {
                    errornum = -1;
                    choice = JOptionPane.showOptionDialog(null, mp, "Mprog Triggers for: " + mob.getShortDesc(),
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);

                } while (errornum == 1);

            }

        });

        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (vnum <= data.getFirstMobVnum())
                    return;

                int temp = vnum - 1;
                while (data.getMobile(temp) == null && temp >= data.getFirstMobVnum())
                    temp--;

                if (temp >= data.getFirstMobVnum())
                    vnum = temp;

                update();

            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (vnum >= data.getHighVnum())
                    return;

                int temp = vnum + 1;
                while (data.getMobile(temp) == null && temp <= data.getHighVnum())
                    temp++;

                if (temp <= data.getHighVnum())
                    vnum = temp;

                update();
            }
        });

        newMobButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the lowest available mob vnum
                int vnum = data.getFreeMobileVnum();
                if (vnum == -1) {
                    inform("You are out of vnums!");
                    return;
                }
                Mobile temp = new Mobile(vnum, data);
                temp.setRace(theRaces.defaultRace());
                data.insert(temp);
                update(vnum);
            }
        });

        deleteMobButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!confirm("Please confirm Mob deletion. All resets will be lost."))
                    return;

                data.deleteMobile(vnum);
                vnum = -1;
                update();
            }
        });

        sizeChoice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Mobile temp = data.getMobile(vnum);
                if (temp == null)
                    return;

                temp.setSize(sizeChoice.getSelectedSize());
            }
        });
        damTypeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Mobile temp = data.getMobile(vnum);
                if (temp == null)
                    return;

                temp.setDamageType((String) damTypeBox.getSelectedItem());
                updateIRV();
            }
        });
        vnumBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Mobile temp = (Mobile) vnumBox.getSelectedItem();
                if (temp == null)
                    return;

                vnum = temp.getVnum();
                update();
            }
        });
        startPos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Mobile temp = data.getMobile(vnum);
                if (temp == null)
                    return;

                temp.setStartPosition(MudConstants.positionFlags[startPos.getSelectedIndex()]);
            }
        });
        defaultPos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Mobile temp = data.getMobile(vnum);
                if (temp == null)
                    return;

                temp.setDefaultPosition(MudConstants.positionFlags[defaultPos.getSelectedIndex()]);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
    }

    /**
     * The armorfield determined there was a change, so this is called.
     *
     * @param type  The ArmorType that changed.
     * @param value The new value
     */
    public void setArmor(Armor type, int value) {
        Mobile mob = (Mobile) vnumBox.getSelectedItem();
        if (mob == null) {
            return;
        }
        mob.setAC(type, value);
    }

    /**
     * A dice field determined there was a change, so this is called.
     *
     * @param name alias of dice field that changed
     * @param dice new dice value.
     */
    public void setDice(String name, Dice dice) {
        Mobile mob = (Mobile) vnumBox.getSelectedItem();
        if (mob == null) {
            return;
        }
        if (name.equalsIgnoreCase("hit")) {
            mob.setHitDice(dice);
        } else if (name.equalsIgnoreCase("mana")) {
            mob.setManaDice(dice);
        } else if (name.equalsIgnoreCase("damage")) {
            mob.setDamageDice(dice);
        }
    }

    class DeathCryPanel extends JPanel {
        Mobile mob;
        String dCry;
        JTextField field;

        public DeathCryPanel() {
            mob = data.getMobile(vnum);
            dCry = mob.getDeathCry();

            field = new JTextField(40);
            field.setBorder(new TitledBorder("Mobile DeathCry:"));

            this.add(field);
            if (dCry != null)
                field.setText(dCry);
        }

        public String getDeathCry() {
            return field.getText();
        }
    }

    class StringFieldFocusListener implements FocusListener {
        public void focusGained(FocusEvent e) {
        }

        public void focusLost(FocusEvent e) {
            Mobile mob = data.getMobile(vnum);
            if (mob == null)
                return;

            String newS, oldS;
            int oldVal, newVal;

            if (e.getSource() instanceof JTextArea) {
                oldS = mob.getDescription();
                newS = desc.getText();
                if (newS.equals("")) {
                    update();
                    return;
                }
                if (newS.equalsIgnoreCase(oldS))
                    return;
                else
                    mob.setDescription(newS);

                return;
            }

            JTextField field = (JTextField) e.getSource();

            newS = field.getText();
            if (newS.equals("")) {
                update();
                return;
            }

            if (field == fields[1]) {
                oldS = mob.getName();
                if (newS.equalsIgnoreCase(oldS))
                    return;
                else
                    mob.setName(newS);
            } else if (field == fields[2]) {
                oldS = mob.getShortDesc();
                if (newS.equalsIgnoreCase(oldS))
                    return;
                else
                    mob.setShortName(newS);

                vnumBox.updateUI();
            } else if (field == fields[3]) {
                oldS = mob.getLongDesc();
                if (newS.equalsIgnoreCase(oldS))
                    return;
                else
                    mob.setLongName(newS);
            } else if (field == mobMaterial) {
                oldS = mob.getMaterial();
                if (newS.equalsIgnoreCase(oldS))
                    return;
                else
                    mob.setMaterial(newS); // one argument this.
            } else if (field == mobDeathCry) {
                oldS = mob.getDeathCry();
                if (newS.equalsIgnoreCase(oldS))
                    return;
                else
                    mob.setDeathCry(newS); // one argument this.
            } else {
                try {
                    newVal = Integer.parseInt(newS);
                    if (field == mobWealth) {
                        oldVal = mob.getWealth();

                        if (newVal == oldVal)
                            return;
                        else
                            mob.setWealth(newVal);
                    } else if (field == groupField) {
                        oldVal = mob.getGroup();

                        if (newVal == oldVal)
                            return;
                        else
                            mob.setGroup(newVal);
                    } else if (field == levelField) {
                        oldVal = mob.getLevel();

                        if (newVal == oldVal)
                            return;
                        else
                            mob.setLevel(newVal);
                    } else if (field == hitRollField) {
                        oldVal = mob.getHitRoll();

                        if (newVal == oldVal)
                            return;
                        else
                            mob.setHitRoll(newVal);
                    } else
                        return;
                }
                catch (Exception evt1) {
                    update();
                    return;
                }
            }  // else
            update();
            return;
        }     // focus lost
    }        // class

    class MobEqPanel2 extends JPanel {
        JComboBox[] wearSlots;
        JLabel wearLightLabel;
        JLabel wearHeadLabel;
        JLabel wearNeckLabel1;
        JLabel wearNeckLabel2;
        JLabel wearRingLabel1;
        JLabel wearRingLabel2;
        JLabel wearTorsoLabel;
        JLabel wearLegsLabel;
        JLabel wearFeetLabel;
        JLabel wearHandsLabel;
        JLabel wearArmsLabel;
        JLabel wearShieldLabel;
        JLabel wearBodyLabel;
        JLabel wearWaistLabel;
        JLabel wearWristLabel1;
        JLabel wearWristLabel2;
        JLabel wearWieldLabel;
        JLabel wearHeldLabel;
        JLabel wearSurroundLabel;
        JLabel wearEarLabel1;
        JLabel wearEarLabel2;
        wearSlotListener sListener;

        public MobEqPanel2(Area data) {
            int wear = 0;
            int locate = 0;
            int insert = 0;

            this.setLayout(new GridLayout(22, 1));

            wearSlots = new JComboBox[21];
            for (int a = 0; a < 21; a++) {
                wearSlots[a] = new JComboBox();
                wearSlots[a].addItem("##### - Nothingness");
                wearSlots[a].setPreferredSize(wearSlots[a].getPreferredSize());
            }

            labels();
            populate();

            addCombo(wearLightLabel, wearSlots[0]);
            addCombo(wearHeadLabel, wearSlots[6]);
            addCombo(wearNeckLabel1, wearSlots[3]);
            addCombo(wearNeckLabel2, wearSlots[4]);
            addCombo(wearRingLabel1, wearSlots[1]);
            addCombo(wearRingLabel2, wearSlots[2]);
            addCombo(wearTorsoLabel, wearSlots[5]);
            addCombo(wearLegsLabel, wearSlots[7]);
            addCombo(wearFeetLabel, wearSlots[8]);
            addCombo(wearHandsLabel, wearSlots[9]);
            addCombo(wearArmsLabel, wearSlots[10]);
            addCombo(wearShieldLabel, wearSlots[11]);
            addCombo(wearBodyLabel, wearSlots[12]);
            addCombo(wearWaistLabel, wearSlots[13]);
            addCombo(wearWristLabel1, wearSlots[14]);
            addCombo(wearWristLabel2, wearSlots[15]);
            addCombo(wearWieldLabel, wearSlots[16]);
            addCombo(wearHeldLabel, wearSlots[17]);
            addCombo(wearSurroundLabel, wearSlots[18]);
            addCombo(wearEarLabel1, wearSlots[19]);
            addCombo(wearEarLabel2, wearSlots[20]);

            sListener = new wearSlotListener();
            for (int z = 0; z < 21; z++)
                wearSlots[z].addActionListener(sListener);
        }

        public void setEnabled(boolean value) {
            for (int z = 0; z < 21; z++)
                wearSlots[z].setEnabled(value);
        }

        private void labels() {
            wearLightLabel = new JLabel("<Light>");
            wearHeadLabel = new JLabel("<Head>");
            wearNeckLabel1 = new JLabel("<Neck1>");
            wearNeckLabel2 = new JLabel("<Neck2>");
            wearRingLabel1 = new JLabel("<Finger1>");
            wearRingLabel2 = new JLabel("<Finger2>");
            wearTorsoLabel = new JLabel("<Torso>");
            wearLegsLabel = new JLabel("<Legs>");
            wearFeetLabel = new JLabel("<Feet>");
            wearHandsLabel = new JLabel("<Hands>");
            wearArmsLabel = new JLabel("<Arms>");
            wearShieldLabel = new JLabel("<Shield>");
            wearBodyLabel = new JLabel("<Body>");
            wearWaistLabel = new JLabel("<Waist>");
            wearWristLabel1 = new JLabel("<Wrist1>");
            wearWristLabel2 = new JLabel("<Wrist2>");
            wearWieldLabel = new JLabel("<Wielded>");
            wearHeldLabel = new JLabel("<Held>");
            wearSurroundLabel = new JLabel("<Surround>");
            wearEarLabel1 = new JLabel("<Ear1>");
            wearEarLabel2 = new JLabel("<Ear2>");
        }

        public void populate() {
            MudObject temp;
            int wear = 0;

            for (int a = 0; a < 21; a++) {
                wearSlots[a].removeAllItems();
                wearSlots[a].addItem("##### - Nothingness");
            }

            for (int loop = data.getLowVnum(); loop <= data.getHighVnum(); loop++) {
                temp = data.getObject(loop);
                if (temp == null)
                    continue;

                wear = temp.getWearFlags();

                // unchoosable if no take flag.
                if ((wear & MudConstants.ITEM_TAKE) != MudConstants.ITEM_TAKE)
                    continue;

                wear = (wear ^ MudConstants.ITEM_TAKE);

                if (temp.getType() == MudConstants.ITEM_LIGHT)
                    wearSlots[0].addItem(temp);

                if (wear == 0) // wear can only be zero for light sources
                    continue;

                switch (wear) {
                    case MudConstants.ITEM_WEAR_FINGER: {
                        wearSlots[1].addItem(temp);
                        wearSlots[2].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_NECK: {
                        wearSlots[3].addItem(temp);
                        wearSlots[4].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_BODY: {
                        wearSlots[5].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_HEAD: {
                        wearSlots[6].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_LEGS: {
                        wearSlots[7].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_FEET: {
                        wearSlots[8].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_HANDS: {
                        wearSlots[9].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_ARMS: {
                        wearSlots[10].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_SHIELD: {
                        wearSlots[11].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_ABOUT: {
                        wearSlots[12].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_WAIST: {
                        wearSlots[13].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_WRIST: {
                        wearSlots[14].addItem(temp);
                        wearSlots[15].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WIELD: {
                        wearSlots[16].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_HOLD: {
                        wearSlots[17].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_FLOAT: {
                        wearSlots[18].addItem(temp);
                        break;
                    }
                    case MudConstants.ITEM_WEAR_EARS: {
                        wearSlots[19].addItem(temp);
                        wearSlots[20].addItem(temp);
                        break;
                    }
                    default:
                }
            }  // end for.
        }

        class wearSlotListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox) e.getSource();

                MudObject obj = null;

                if (box.getSelectedItem() instanceof MudObject)
                    obj = (MudObject) box.getSelectedItem();

                for (int loop = 0; loop < 21; loop++) {
                    if (box == wearSlots[loop]) {
                        Mobile mob = data.getMobile(vnum);

                        if (mob == null)
                            return;

                        if (obj != null)
                            mob.equipMobile(loop, obj.getVnum());
                        else
                            mob.equipMobile(loop, -1);
                    }
                }
            }
        }

        public void reset() {
            populate();
        }

        public void update() {
            for (int a = 0; a < 21; a++)
                wearSlots[a].removeActionListener(sListener);
            populate();
            clearEq();
            setEq();
            for (int a = 0; a < 21; a++)
                wearSlots[a].addActionListener(sListener);
        }

        private void clearEq() {
            for (int a = 0; a < 21; a++)
                wearSlots[a].setSelectedIndex(0);
        }

        /*
        * this function takes the eq equipped to mobile and
        * sets the combo boxes to that eq.
        */
        private void setEq() {
            boolean equip = false;

            if (vnum == -1)
                return;

            Mobile mob = data.getMobile(vnum);
            MudObject obj;
            int eqVnum;

            if (mob == null) {
                System.out.println("null mob, returning");
                return;
            }

            for (int a = 0; a < 21; a++) {
                eqVnum = mob.getEquipment(a);

                if (eqVnum == -1)
                    continue;
                else
                    equip = true;

                obj = data.getObject(eqVnum);

                if (obj == null)
                    continue;

                wearSlots[a].setSelectedItem(obj);
            }
        }

        private void addCombo(Component a, Component b) {
            JPanel temp = new JPanel();
            temp.setLayout(new BorderLayout());
            temp.add(a, BorderLayout.WEST);
            temp.add(b, BorderLayout.EAST);
            this.add(temp);
        }
    }
}