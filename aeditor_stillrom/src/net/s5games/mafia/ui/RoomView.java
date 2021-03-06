/*
 * George Frick
 * RoomView.java
 * Area Editor Project, Spring 2002
 *
 * This class displays the rooms, including a navigator, and map.
 * It allows viewing, changing, creation, and deletion of rooms and exits.
 */
package net.s5games.mafia.ui;

import net.s5games.mafia.model.MudConstants;
import net.s5games.mafia.model.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class RoomView extends EditorView implements ActionListener {
    private JTextField[] fields;
    private JTextArea desc;
    private SectorChooser sectorchoice;
    private FlagChoice flagchoice;
    private MapView themap;
    private JButton deleteRoomButton;
    private JButton backRoomButton;
    private JButton nextRoomButton;
    private JButton doorsRoomButton;
    private ExitPanel exitpanel;
    private JButton containerButton;
    private JButton extraDescButton;
    private JRadioButton z1;
    private JRadioButton z2;
    private JRadioButton z3;
    private JScrollPane mapPort;
    private JComboBox vnumBox;
    private JCheckBox keyDig;
    private JCheckBox keyLink;

    public RoomView(Area ar) {
        super(ar);
        ClassLoader loader = ClassLoader.getSystemClassLoader();

        vnumBox = data.getVnumCombo("room");
        //JLabel tlabel = new JLabel("##### The great halls of the great dwarven king of alexandria");
        //vnumBox.setPreferredSize(tlabel.getPreferredSize());
        vnumBox.setBorder(new TitledBorder("Vnum"));

        containerButton = new JButton("Put things in this Room");
        containerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RoomInPanel mp = new RoomInPanel(vnum, data);
                int choice = -1;
                int errornum = -1;
                Room room = data.getRoom(vnum);
                if (room == null)
                    return;

                Object[] options = {"CLOSE"};
                do {
                    errornum = -1;
                    choice = JOptionPane.showOptionDialog(null, mp, "Room Resets: " + room.toString(),
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);

                } while (errornum == 1);

            }
        });

        extraDescButton = new JButton("Set Extra Descriptions");
        extraDescButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ExtraDescPanel mp = new ExtraDescPanel(vnum, data);
                int choice = -1;
                int errornum = -1;
                Room room = data.getRoom(vnum);
                if (room == null)
                    return;

                Object[] options = {"CLOSE"};
                do {
                    errornum = -1;
                    choice = JOptionPane.showOptionDialog(null, mp, "Extra Descriptions for: " + room.toString(),
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);

                } while (errornum == 1);

            }
        });

        sectorchoice = new SectorChooser();
        sectorchoice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (vnum != -1) {
                    Room temp = data.getRoom(vnum);
                    temp.setSector(sectorchoice.getSector());
                }
                update();
            }
        });

        flagchoice = new FlagChoice("Room Flags", MudConstants.roomFlags,
                MudConstants.roomFlagData, MudConstants.roomFlagCount, this);

        // implement listeners...
        fields = new JTextField[4];
        constraint.insets = new Insets(3, 3, 3, 3);

        fields[1] = new JTextField(25);
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(1, 1));
        namePanel.add(fields[1]);
        namePanel.setBorder(new TitledBorder("Name"));
        namePanel.setPreferredSize(fields[1].getPreferredSize());

        fields[2] = new JMudNumberField(6);
        fields[3] = new JMudNumberField(6);
        JPanel rPanel = new JPanel();
        rPanel.setLayout(new GridLayout(1, 2));
        rPanel.add(fields[2]);
        rPanel.add(fields[3]);
        rPanel.setBorder(new TitledBorder("Hit/Mana Regen"));

        desc = new JTextArea(10, 40);
        desc.setLineWrap(true);
        desc.setColumns(38);
        JScrollPane descHolder = new JScrollPane(desc);
        descHolder.setBorder(new TitledBorder("Description"));

        fields[1].addFocusListener(new StringFieldFocusListener());
        fields[2].addFocusListener(new StringFieldFocusListener());
        fields[3].addFocusListener(new StringFieldFocusListener());
        desc.addFocusListener(new StringFieldFocusListener());

        themap = new MapView(data, this);
        JPanel mapHolder = new JPanel();
        mapHolder.setLayout(new BorderLayout());

        mapPort = new JScrollPane(themap);
        mapHolder.add(mapPort, BorderLayout.CENTER);
        JPanel zPanel = new JPanel();
        zPanel.setLayout(new GridLayout(1, 6));
        ButtonGroup bGroup = new ButtonGroup();
        z1 = new JRadioButton("1X");
        z2 = new JRadioButton("2X", true);
        z3 = new JRadioButton("3X");
        keyDig = new JCheckBox("KeyDig");
        keyLink = new JCheckBox("KeyLink");
        zoomListener zListen = new zoomListener();
        z1.addActionListener(zListen);
        z2.addActionListener(zListen);
        z3.addActionListener(zListen);
        bGroup.add(z1);
        bGroup.add(z2);
        bGroup.add(z3);
        zPanel.add(new JLabel(new ImageIcon(loader.getResource("magnify.gif"))));
        zPanel.add(z1);
        zPanel.add(z2);
        zPanel.add(z3);
        zPanel.add(keyDig);
        zPanel.add(keyLink);
        mapHolder.add(zPanel, BorderLayout.NORTH);
        mapHolder.setBorder(new TitledBorder("Map"));
        mapHolder.setPreferredSize(new Dimension(375, 500));

        JButton newRoomButton = new JButton("New");
        deleteRoomButton = new JButton("Delete");
        backRoomButton = new JButton("Back");
        nextRoomButton = new JButton("Next");
        doorsRoomButton = new JButton("Doors");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));
        buttonPanel.add(backRoomButton);
        buttonPanel.add(nextRoomButton);
        buttonPanel.add(newRoomButton);
        buttonPanel.add(deleteRoomButton);
        buttonPanel.add(doorsRoomButton);
        buttonPanel.setBorder(new TitledBorder("Browser"));

        doorsRoomButton.addActionListener(new doorListener(data));
        newRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the lowest available room vnum
                int vnum = data.getFreeRoomVnum();
                if (vnum == -1) {
                    inform("You are out of vnums!");
                    return;
                }
                Room temp = new Room(vnum, data);
                data.insert(temp);
                update(vnum);
            }
        });

        deleteRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRoom();
            }
        });

        backRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (vnum <= data.getFirstRoomVnum())
                    return;

                int temp = vnum - 1;
                while (data.getRoom(temp) == null && temp >= data.getFirstRoomVnum())
                    temp--;

                if (temp >= data.getFirstRoomVnum())
                    vnum = temp;

                update();

            }
        });

        nextRoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (vnum >= data.getHighVnum())
                    return;

                int temp = vnum + 1;
                while (data.getRoom(temp) == null && temp <= data.getHighVnum())
                    temp++;

                if (temp <= data.getHighVnum())
                    vnum = temp;

                update();
            }
        });

        vnumBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Room temp = (Room) vnumBox.getSelectedItem();
                if (temp == null)
                    return;

                vnum = temp.getVnum();
                update();
            }
        });

        JPanel bHolder = new JPanel();
        bHolder.setLayout(new GridLayout(1, 2));
        bHolder.add(extraDescButton);
        bHolder.add(containerButton);
        bHolder.setBorder(new TitledBorder("Content Manager"));
        JPanel topRight = new JPanel();
        topRight.setLayout(new GridLayout(2, 1));
        topRight.add(buttonPanel);
        topRight.add(bHolder);

        exitpanel = new ExitPanel(this, data);

        setLayout(new GridLayout(1, 2));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(topRight, BorderLayout.NORTH);
        rightPanel.add(mapHolder, BorderLayout.CENTER);

        JPanel leftBottomPanel = new JPanel();
        leftBottomPanel.setLayout(new BorderLayout());
        leftBottomPanel.add(flagchoice, BorderLayout.NORTH);
        leftBottomPanel.add(exitpanel, BorderLayout.CENTER);

        JPanel leftTopSplitPanel = new JPanel();
        leftTopSplitPanel.setLayout(new GridLayout(1, 2));
        leftTopSplitPanel.add(sectorchoice);
        leftTopSplitPanel.add(rPanel);

        JPanel leftTopTopPanel = new JPanel();
        leftTopTopPanel.setLayout(new GridLayout(3, 1));
        leftTopTopPanel.add(vnumBox);
        leftTopTopPanel.add(namePanel);
        leftTopTopPanel.add(leftTopSplitPanel);

        JPanel leftTopPanel = new JPanel();
        leftTopPanel.setLayout(new GridLayout(2, 1));
        leftTopPanel.add(leftTopTopPanel);
        leftTopPanel.add(descHolder);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1));
        leftPanel.add(leftTopPanel);
        leftPanel.add(leftBottomPanel);


        add(leftPanel);
        add(rightPanel);

        /*
topRow.add(namePanel,BorderLayout.CENTER);
JPanel r2Panel = new JPanel();
r2Panel.setLayout(new GridLayout(1,2) );
r2Panel.add(rPanel);
r2Panel.add(sectorchoice);
topRow.add(r2Panel,BorderLayout.EAST);

JPanel ePanel1 = new JPanel();
ePanel1.setLayout(new BorderLayout());
ePanel1.add(exitpanel,BorderLayout.CENTER);
ePanel1.add(buttonPanel,BorderLayout.SOUTH);
ePanel1.setBorder(new TitledBorder("Exit Manager/Navigation"));
JPanel lPanel1 = new JPanel();
lPanel1.setLayout(new GridLayout(3,1));
lPanel1.add(descHolder);
lPanel1.add(flagchoice);
lPanel1.add(ePanel1);
insertAt(0,1,1,1,lPanel1,true,true);
insertAt(0,0,1,1,vnumBox,true,true);
insertAt(1,0,1,1,topRow,true,true);

JPanel bHolder = new JPanel();
bHolder.setLayout(new GridLayout(1,2));
bHolder.add(extraDescButton);
bHolder.add(containerButton);
JPanel b2Panel = new JPanel();
b2Panel.setLayout(new BorderLayout());
b2Panel.add(mapHolder,BorderLayout.CENTER);
b2Panel.add(bHolder,BorderLayout.NORTH);

insertAt(1,1,1,1,b2Panel,true,true);
        */
        update();
    }

    public void update(int newVnum) {
        vnum = newVnum;
        update();
    }

    public void update() {
        if (data.getRoomCount() == 0) {
            fields[1].setText("No Room Loaded.");
            desc.setText("No Room Loaded." +
                    sectorchoice.currentSector());
            fields[2].setText("100");
            fields[3].setText("100");
            exitpanel.reset();
            setEnabled(false);
        } else {
            if (vnum == -1 || vnum < data.getLowVnum() || vnum > data.getHighVnum())
                vnum = data.getFirstRoomVnum();
            Room temp = data.getRoom(vnum);
            fields[1].setText(temp.getName());
            desc.setText(temp.getDescription());
            fields[2].setText(Integer.toString(temp.getHeal())); // heal
            fields[3].setText(Integer.toString(temp.getMana())); // mana
            sectorchoice.setCurrentSector(temp.getSector());
            flagchoice.setFlags(temp.getFlags());
            exitpanel.setVnum(vnum);
            themap.update(vnum);
            themap.repaint();
            JScrollBar barA = mapPort.getVerticalScrollBar();
            barA.setValue(barA.getMaximum() / 3);
            barA = mapPort.getHorizontalScrollBar();
            barA.setValue(barA.getMaximum() / 3);
            setEnabled(true);
        }
        vnumBox.setSelectedItem(data.getRoom(vnum));
    }

    public void setEnabled(boolean value) {
        fields[1].setEnabled(value);
        fields[2].setEnabled(value);
        fields[3].setEnabled(value);
        desc.setEnabled(value);
        sectorchoice.setEnabled(value);
        flagchoice.setEnabled(value);
        exitpanel.setEnabled(value);
        vnumBox.setEnabled(value);
        extraDescButton.setEnabled(value);
        containerButton.setEnabled(value);
        z1.setEnabled(value);
        z2.setEnabled(value);
        z3.setEnabled(value);
        keyDig.setEnabled(value);
        keyLink.setEnabled(value);
        deleteRoomButton.setEnabled(value);
        backRoomButton.setEnabled(value);
        nextRoomButton.setEnabled(value);
        doorsRoomButton.setEnabled(value);
    }

    public void deleteRoom() {
        int uVnum = -1;
        Room tRoom = data.getRoom(vnum);

        if (!confirm("Please confirm room deletion. All exits will be lost."))
            return;

        if (tRoom.getNorth() != null)
            uVnum = tRoom.getNorth().getVnum();
        else if (tRoom.getSouth() != null)
            uVnum = tRoom.getSouth().getVnum();
        else if (tRoom.getWest() != null)
            uVnum = tRoom.getWest().getVnum();
        else if (tRoom.getEast() != null)
            uVnum = tRoom.getEast().getVnum();

        data.deleteRoom(vnum);
        vnum = uVnum;
        update();
    }

    public void moveView(int dir) {
        Room temp;
        temp = data.getRoom(vnum);
        MudExit texit;

        if (temp == null) {
            inform("Exit leads out of model!");
        } else if (temp.getExit(dir) == null) {
            JPanel temp2 = new JPanel();
            GridBagConstraints constraint;
            GridBagLayout layout;
            constraint = new GridBagConstraints();
            constraint.insets = new Insets(3, 3, 3, 3);
            layout = new GridBagLayout();
            temp2.setLayout(layout);
            constraint.gridy = 0;
            constraint.gridx = 0;
            constraint.gridwidth = 2;

            JLabel msg = new JLabel("<HTML><BODY><BOLD><HR>There is no room in that direction!</BOLD><BR><HR></BODY></HTML>");
            layout.setConstraints(msg, constraint);
            temp2.add(msg);

            JRadioButton linkRadio = new JRadioButton("Link to ->");
            JComboBox linkBox = data.getVnumCombo("rooms");
            JRadioButton digRadio = new JRadioButton("Dig a new room");
            digRadio.setMnemonic(KeyEvent.VK_D);
            linkRadio.setMnemonic(KeyEvent.VK_L);
            ButtonGroup group = new ButtonGroup();
            group.add(linkRadio);
            group.add(digRadio);

            constraint.gridy = 1;
            constraint.gridx = 0;
            constraint.gridwidth = 1;
            layout.setConstraints(linkRadio, constraint);
            temp2.add(linkRadio);

            constraint.gridy = 2;
            constraint.gridx = 0;
            layout.setConstraints(digRadio, constraint);
            temp2.add(digRadio);

            constraint.gridy = 1;
            constraint.gridx = 1;
            layout.setConstraints(linkBox, constraint);
            temp2.add(linkBox);

            if (keyLink.isSelected()) {
                int myX, myY;
                myX = temp.getX();
                myY = temp.getY();
                if (dir == 0)  // north
                    myY -= themap.getShortSize();
                else if (dir == 1)  // east
                    myX += themap.getShortSize();
                else if (dir == 2)  // south
                    myY += themap.getShortSize();
                else if (dir == 3)  // west
                    myX -= themap.getShortSize();
                else
                    return;

                // go through the rooms.
                Room mRoom;
                for (int a = data.getLowVnum(); a <= data.getHighVnum(); a++) {
                    mRoom = data.getRoom(a);
                    if (mRoom == null)
                        continue;
                    if (mRoom.getX() == myX && mRoom.getY() == myY) {
                        // link the two rooms.
                        if (mRoom.getExit(MudExit.getReverseExit(dir)) != null) {
                            System.out.println("other side has link");
                            return;
                        }

                        MudExit newExit = new MudExit(mRoom.getVnum(), temp);
                        temp.setExit(newExit, dir);
                        newExit = new MudExit(temp.getVnum(), mRoom);
                        mRoom.setExit(newExit, MudExit.getReverseExit(dir));
                        update();
                        return;
                    }
                }
                if (dir == 0)  // north
                    myY -= themap.getShortSize();
                else if (dir == 1)  // east
                    myX += themap.getShortSize();
                else if (dir == 2)  // south
                    myY += themap.getShortSize();
                else if (dir == 3)  // west
                    myX -= themap.getShortSize();

                for (int a = data.getLowVnum(); a <= data.getHighVnum(); a++) {
                    mRoom = data.getRoom(a);
                    if (mRoom == null)
                        continue;

                    if (mRoom.getX() == myX && mRoom.getY() == myY) {
                        // link the two rooms.
                        if (mRoom.getExit(MudExit.getReverseExit(dir)) != null) {
                            System.out.println("other side has link");
                            return;
                        }

                        MudExit newExit = new MudExit(mRoom.getVnum(), temp);
                        temp.setExit(newExit, dir);

                        newExit = new MudExit(temp.getVnum(), mRoom);
                        mRoom.setExit(newExit, MudExit.getReverseExit(dir));
                        update();
                        return;
                    }
                }
                // go through the rooms.
            } else if (!keyDig.isSelected() && !keyLink.isSelected()) {
                int ans = JOptionPane.showConfirmDialog(null,
                        temp2, "No exit in given direction...", JOptionPane.OK_CANCEL_OPTION);

                if (ans == JOptionPane.CANCEL_OPTION)
                    return;
            }

            if (!keyDig.isSelected() && linkRadio.isSelected()) {
                Room t2 = (Room) linkBox.getSelectedItem();
                // we need to check if other side already has an exit
                if (t2.getExit(MudExit.getReverseExit(dir)) != null &&
                        t2.getExit(MudExit.getReverseExit(dir)).getToVnum() != vnum) {
                    inform("Other side has exit, cannot link.");
                    return;
                }
                MudExit exit1 = new MudExit(t2.getVnum(), data.getRoom(vnum));
                MudExit exit2 = new MudExit(vnum, t2);
                data.getRoom(vnum).setExit(exit1, dir);
                t2.setExit(exit2, MudExit.getReverseExit(dir));
                moveView(dir);
            } else if (keyDig.isSelected() || digRadio.isSelected()) {
                int newVnum = data.getFreeRoomVnum();
                if (newVnum == -1) {
                    inform("You are out of vnums!");
                    return;
                }
                Room t2 = new Room(newVnum, data);
                data.insert(t2);
                MudExit exit1 = new MudExit(t2.getVnum(), data.getRoom(vnum));
                MudExit exit2 = new MudExit(vnum, t2);
                data.getRoom(vnum).setExit(exit1, dir);
                t2.setExit(exit2, MudExit.getReverseExit(dir));
                update();
                moveView(dir);
            }

        } else {
            texit = temp.getExit(dir);
            vnum = texit.getToVnum();
            update();
        }
    }

    public void digRoom(int dir) {
        int newVnum = data.getFreeRoomVnum();
        if (newVnum == -1) {
            inform("You are out of vnums!");
            return;
        }
        Room t2 = new Room(newVnum, data);
        data.insert(t2);
        MudExit exit1 = new MudExit(t2.getVnum(), data.getRoom(vnum));
        MudExit exit2 = new MudExit(vnum, t2);
        data.getRoom(vnum).setExit(exit1, dir);
        t2.setExit(exit2, MudExit.getReverseExit(dir));
        update();
        moveView(dir);
    }

    class MapKeyListener implements KeyListener {
        /**
         * Handle the key typed event from the text field.
         */
        public void keyTyped(KeyEvent e) {
            keyHandle(e);
        }

        /**
         * Handle the key pressed event from the text field.
         */
        public void keyPressed(KeyEvent e) {
            keyHandle(e);
        }

        /**
         * Handle the key released event from the text field.
         */
        public void keyReleased(KeyEvent e) {
            keyHandle(e);
        }

        public void keyHandle(KeyEvent e) {
            System.out.println("hello");
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_KP_DOWN:
                case KeyEvent.VK_DOWN: {
                    System.out.println("Down");
                    return;
                }
                default: {
                    System.out.println("blah");
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        Room temp = data.getRoom(vnum);
        if (temp == null || flagchoice == null)
            return;
        temp.setFlags(flagchoice.getFlags());
        update();
    }


    class zoomListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            JRadioButton b = (JRadioButton) evt.getSource();

            if (b == z1)
                themap.update(vnum, MapView.ZOOM_1X);
            else if (b == z2)
                themap.update(vnum, MapView.ZOOM_2X);
            else if (b == z3)
                themap.update(vnum, MapView.ZOOM_3X);
            themap.repaint();
        }
    }

    class StringFieldFocusListener implements FocusListener {
        public void focusGained(FocusEvent e) {
        }

        public void focusLost(FocusEvent e) {
            Room room = data.getRoom(vnum);

            if (room == null)
                return;

            String newS, oldS;
            int oldVal, newVal;

            if (e.getSource() instanceof JTextArea) {
                oldS = room.getDescription();
                newS = desc.getText();
                if (newS.equals("")) {
                    update();
                    return;
                }
                if (newS.equalsIgnoreCase(oldS))
                    return;
                else
                    room.setDescription(newS);

                return;
            }

            JTextField field = (JTextField) e.getSource();

            newS = field.getText();
            if (newS.equals("")) {
                update();
                return;
            }

            if (field == fields[1]) {
                oldS = room.getName();
                if (newS.equalsIgnoreCase(oldS))
                    return;
                else
                    room.setName(newS);
            } else {
                try {
                    newVal = Integer.parseInt(newS);

                    if (field == fields[2]) {
                        oldVal = room.getHeal();

                        if (newVal == oldVal)
                            return;
                        else
                            room.setHeal(newVal);
                    } else if (field == fields[3]) {
                        oldVal = room.getMana();

                        if (newVal == oldVal)
                            return;
                        else
                            room.setMana(newVal);
                    } else
                        return;
                }
                catch (Exception evt1) {
                    update();
                    return;
                }
            }
            update();
        }
    }        // end of class

    /*
    * Listens to the door button, popping up an edit windor for exits.
    * this window has a radio button set for available directions,
    * and displays the selected directions exit data, with a checkable
    * flag set.
    */
    class doorListener implements ActionListener {
        JFrame myparent = null;
        private GridBagConstraints constraint;
        private GridBagLayout layout;
        JPanel panel;
        /*
        * Components for layout
        */
        private JComboBox keySelection;
        private JRadioButton[] exitSelection;
        private ButtonGroup group;
        private FlagChoice exitFlags;
        private boolean keys;
        String nokey;
        keySelectionListener ksListener;

        public doorListener(Area area) {
            super();
            data = area;
            panel = new JPanel();
            nokey = "No Key";
            ksListener = new keySelectionListener();
            exitSelection = new JRadioButton[MudExit.MAX_EXITS];
            exitSelection[0] = new JRadioButton("North");
            exitSelection[1] = new JRadioButton("East");
            exitSelection[2] = new JRadioButton("South");
            exitSelection[3] = new JRadioButton("West");
            exitSelection[4] = new JRadioButton("Up");
            exitSelection[5] = new JRadioButton("Down");
            group = new ButtonGroup();
            for (int a = 0; a < 6; a++)
                group.add(exitSelection[a]);
            layout = new GridBagLayout();
            constraint = new GridBagConstraints();
            panel.setLayout(layout);
            constraint.insets = new Insets(3, 3, 3, 3);
            constraint.gridwidth = 6;
            constraint.gridheight = 1;
            constraint.gridx = 0;
            JLabel l = new JLabel("This is the door editor, you may choose a direction and then ");
            JLabel l2 = new JLabel("setup the doors/flags in that direction.");
            constraint.gridy = 0;
            layout.setConstraints(l, constraint);
            panel.add(l);
            constraint.gridy = 1;
            layout.setConstraints(l2, constraint);
            panel.add(l2);
            constraint.gridwidth = 1;
            // add radio group.
            constraint.gridy = 2;
            for (int a = 0; a < 6; a++) {
                constraint.gridx = a;
                layout.setConstraints(exitSelection[a], constraint);
                panel.add(exitSelection[a]);
                exitSelection[a].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        update();
                    }
                });
            }

            keySelection = new JComboBox();
            keySelection.setBorder(new TitledBorder("Key:"));

            constraint.gridy = 3;
            constraint.gridwidth = 5;
            constraint.gridx = 0;
            layout.setConstraints(keySelection, constraint);
            panel.add(keySelection);

            exitFlags = new FlagChoice("Exit Flags", MudConstants.exitNames,
                    MudConstants.exitFlags, MudConstants.NUM_EXIT_FLAGS,
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            Room temp = data.getRoom(vnum);
                            if (temp == null)
                                return;
                            MudExit exit = temp.getExit(selectedD());
                            if (exit == null)
                                return;

                            exit.setFlags(exitFlags.getFlags());

                            Room temp2 = data.getRoom(exit.getToVnum());
                            MudExit exit2 = temp2.getExit(MudExit.getReverseExit(selectedD()));
                            if (exit2 == null)
                                return;
                            exit2.setFlags(exitFlags.getFlags());
                            update();
                        }
                    });

            constraint.gridy = 4;
            constraint.gridwidth = 6;
            constraint.gridx = 0;
            layout.setConstraints(exitFlags, constraint);
            panel.add(exitFlags);
            exitSelection[0].setSelected(true);
            keySelection.addActionListener(ksListener);
            update();
        }

        class keySelectionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (keySelection.getSelectedItem() instanceof String) {
                    Room temp = data.getRoom(vnum);
                    if (temp == null)
                        return;
                    MudExit exit = temp.getExit(selectedD());
                    if (exit == null)
                        return;
                    exit.setKey(0);

                    return;
                }

                MudObject key = (MudObject) keySelection.getSelectedItem();
                if (key == null)
                    return;
                Room temp = data.getRoom(vnum);
                if (temp == null)
                    return;
                MudExit exit = temp.getExit(selectedD());
                if (exit == null)
                    return;
                exit.setKey(key.getVnum());
                System.out.println("Selected a key!");
            }
        }

        private int selectedD() {
            for (int a = 0; a < MudExit.MAX_EXITS; a++)
                if (exitSelection[a].isSelected())
                    return a;

            return -1;
        }

        private int reverseSelected() {
            int d = selectedD();
            switch (d) {
                case 0:
                    return 2;
                case 1:
                    return 3;
                case 2:
                    return 0;
                case 3:
                    return 1;
                case 4:
                    return 5;
                case 5:
                    return 4;
            }

            return -1;
        }

        public void update() {
            keySelection.removeActionListener(ksListener);
            if (vnum == -1)
                return;

            // set it up!
            Room temp = data.getRoom(vnum);
            MudExit exit;
            int selection = -1;
            for (int a = 0; a < MudExit.MAX_EXITS; a++) {
                exit = temp.getExit(a);
                if (exit == null)
                    exitSelection[a].setEnabled(false);
                else
                    exitSelection[a].setEnabled(true);


                if (exitSelection[a].isSelected()) {
                    if (exit != null) {
                        selection = a;
                        keySelection.setEnabled(true);
                        exitFlags.setEnabled(true);
                    } else {
                        keySelection.setEnabled(false);
                        exitFlags.setEnabled(false);
                    }
                }
            }

            if (selection == -1)
                return;

            keySelection.removeAllItems();
            keys = false;
            for (int loop = data.getLowVnum(); loop <= data.getHighVnum(); loop++) {
                MudObject tempo = data.getObject(loop);
                if (tempo == null)
                    continue;
                if (tempo.getType() == MudConstants.ITEM_ROOM_KEY) {
                    keySelection.addItem(tempo);
                    keys = true;
                }
            }

            if (!keys) {
                exitFlags.disableFlag(MudConstants.EXIT_LOCKED);
                keySelection.setEnabled(false);
            } else {
                exitFlags.enableFlag(MudConstants.EXIT_LOCKED);
            }

            exit = temp.getExit(selection);
            exitFlags.setFlags(exit.getFlags());
            keySelection.addItem(nokey);
            keySelection.setSelectedItem(nokey);
            if (data.getObject(exit.getKey()) != null)
                keySelection.setSelectedItem(data.getObject(exit.getKey()));

            keySelection.addActionListener(ksListener);
        }

        public void actionPerformed(ActionEvent e) {
            int choice = -1;
            int errornum = -1;
            Object[] options = {"CLOSE"};
            do {
                errornum = -1;
                update();
                choice = JOptionPane.showOptionDialog(myparent, panel, "Setting Exit Flag/Doors",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);

                /*
                * Here, if locked flag is set, a valid key must be selected.
                */
                MudExit temp;
                for (int a = 0; a < MudExit.MAX_EXITS; a++) {
                    temp = data.getRoom(vnum).getExit(a);
                    if (temp == null || temp.validLock())
                        continue;
                    else {
                        if (temp.getKey() != 0) {
                            inform("No lock set, key setting discarded.");
                            temp.setKey(0);
                            continue;
                        }
                        inform("You must select a key.");
                        errornum = 1;
                    }
                }
                // check for valid setup..
            } while (errornum == 1);
        }
    }
}