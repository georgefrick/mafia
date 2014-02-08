// George Frick
// OverView.java
// Area Editor Project, Spring 2002
package net.s5games.mafia.ui;

import net.s5games.mafia.model.Mobile;
import net.s5games.mafia.model.MudObject;
import net.s5games.mafia.model.MudReset;
import net.s5games.mafia.model.Room;
import net.s5games.mafia.model.impl.AreaImpl;
import net.s5games.mafia.ui.EditorView;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ResetView extends EditorView {
    private JTabbedPane tabPane;
    private RoomWindow roomList;
    private ResetWindow resetList;
    private ResetCellRenderer render;

    public ResetView(AreaImpl ar) {
        super(ar);
        render = new ResetCellRenderer();
        roomList = new RoomWindow();
        resetList = new ResetWindow();
        tabPane = new JTabbedPane(JTabbedPane.TOP);
        tabPane.addTab("Resets By Room", null, roomList, "View resets by room");
        tabPane.addTab("Full Reset List", null, resetList, "View resets by reset list");
        tabPane.setBorder(new TitledBorder("Use the tabs to select the way you would like to view resets for editing"));
        this.setLayout(new BorderLayout());
        // insertAt(0,1,1,1,tabPane, true);
        this.add(tabPane, BorderLayout.CENTER);
        tabPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                update();
            }
        });

    }

    public void update() {
        roomList.update();
        resetList.update();
    }

    public void update(int v) {
        vnum = v;
        update();
    }

    class MyCellRenderer extends JLabel implements ListCellRenderer {
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean callHasFocus) {

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
                setForeground(Color.red);
                setBackground(Color.gray);
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            return this;
        }
    }

    class ResetWindow extends JComponent {
        BorderLayout layout;
        JList myResets;
        JScrollPane myPane;

        public ResetWindow() {
            layout = new BorderLayout();
            this.setLayout(layout);
            myResets = new JList(data.getResets().toArray(new MudReset[data.getResets().size()]));
            myResets.setCellRenderer(render);
            myPane = new JScrollPane(myResets);
            this.add(myPane, BorderLayout.CENTER);
            myPane.setBorder(new TitledBorder("Viewing all resets. No edit."));
        }

        public void update() {
                myResets.setListData(data.getResets().toArray(new MudReset[data.getResets().size()]));
        }
    }

    class RoomWindow extends JComponent {
        JComboBox editList;
        BorderLayout layout;
        JList myResets;
        JScrollPane myPane;
        ResetEditPane editPane;

        public RoomWindow() {
            layout = new BorderLayout();
            this.setLayout(layout);
            editList = data.getVnumCombo("room");
            editList.setBorder(new TitledBorder("Choose a room from the list to edit its resets."));
            editPane = new ResetEditPane();
            this.add(editList, BorderLayout.NORTH);
            myResets = new JList(data.getResets().toArray(new MudReset[data.getResets().size()]));
            myResets.setCellRenderer(render);
            myPane = new JScrollPane(myResets);
            this.add(myPane, BorderLayout.CENTER);
            this.add(editPane, BorderLayout.SOUTH);
        }

        public void update() {          
                editList.setSelectedIndex(0);
                myResets.setListData(data.getResets().toArray(new MudReset[data.getResets().size()]));
        }
    }

    class ResetCellRenderer extends JLabel implements ListCellRenderer {

        public ResetCellRenderer() {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            MudReset tmp = (MudReset) value;
            char rCommand = tmp.getCommand().charAt(0);
            String goText;

            switch (rCommand) {
                case 'M': {
                    Mobile mob = data.getMobile(tmp.getArg(1));
                    Room room = data.getRoom(tmp.getArg(3));
                    goText = "Mobile: (" + mob + ") to room (" + room + ")-> Max in room: " + tmp.getArg(4) + " Max in model: " + tmp.getArg(2) + " EQUIPPED: [NO]";
                    break;
                }
                case 'O': {
                    MudObject obj = data.getObject(tmp.getArg(1));
                    Room room = data.getRoom(tmp.getArg(3));
                    goText = "Object: (" + obj + ") to room (" + room + ")";
                    break;
                }
                case 'P': {
                    MudObject obj = data.getObject(tmp.getArg(1));
                    MudObject toObj = data.getObject(tmp.getArg(3));
                    goText = "Object: (" + obj + ") in/on object (" + toObj + ")";
                    break;
                }
                case 'G': {
                    MudObject obj = data.getObject(tmp.getArg(1));
                    //Mobile mob = data.getMobile(tmp.getArg(3));
                    goText = "Object: (" + obj + ") to mobile (???) in inventory";
                    break;
                }
                case 'E': {
                    MudObject obj = data.getObject(tmp.getArg(1));
                    goText = "Object: (" + obj + ") equipped to mobile (???) on (???)";
                    break;
                }
                case 'D': {
                    goText = "DOOR: ???";
                    break;
                }
                case 'R': {
                    goText = "R does what??";
                    break;
                }
                default: {
                    goText = "Invalid reset. Please delete me.";
                    break;
                }
            }

            setText(goText); // tah dah!

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            return this;
        }
    }

    class ResetEditPane extends JPanel {
        JButton newReset, deleteReset;
        JTextField arg1, arg2, arg3, arg4;
        JComboBox command;
        String[] commands = {"M:obile to room", "O:bject to room", "E:quip Object",
                "G:ive object", "P:ut Object", "D:oor"};
        int commandCount = 6;
        GridBagConstraints constraint;
        GridBagLayout layout;

        public ResetEditPane() {
            layout = new GridBagLayout();
            constraint = new GridBagConstraints();
            newReset = new JButton("New");
            deleteReset = new JButton("Delete");
            arg1 = new JTextField(7);
            arg1.setBorder(new TitledBorder("arg1"));
            arg2 = new JTextField(7);
            arg2.setBorder(new TitledBorder("arg2"));
            arg3 = new JTextField(7);
            arg3.setBorder(new TitledBorder("arg3"));
            arg4 = new JTextField(7);
            arg4.setBorder(new TitledBorder("arg4"));
            command = new JComboBox(commands);
            command.setBorder(new TitledBorder("Command"));

            this.setLayout(layout);

            constraint.gridx = 0;
            constraint.gridy = 0;
            layout.setConstraints(command, constraint);
            this.add(command);

            constraint.gridx = 1;
            constraint.gridy = 0;
            layout.setConstraints(arg1, constraint);
            this.add(arg1);
            constraint.gridx = 2;
            constraint.gridy = 0;
            layout.setConstraints(arg2, constraint);
            this.add(arg2);
            constraint.gridx = 3;
            constraint.gridy = 0;
            layout.setConstraints(arg3, constraint);
            this.add(arg3);
            constraint.gridx = 4;
            constraint.gridy = 0;
            layout.setConstraints(arg4, constraint);
            this.add(arg4);

            constraint.gridx = 0;
            constraint.gridy = 1;
            layout.setConstraints(newReset, constraint);
            this.add(newReset);
            constraint.gridx = 1;
            constraint.gridy = 1;
            layout.setConstraints(deleteReset, constraint);
            this.add(deleteReset);
            this.setBorder(new TitledBorder("Reset Navigation"));
        }
    }

}