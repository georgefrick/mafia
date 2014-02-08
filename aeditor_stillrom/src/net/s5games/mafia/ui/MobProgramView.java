// George Frick
// OverView.java
// Area Editor Project, Spring 2002
package net.s5games.mafia.ui;

import net.s5games.mafia.model.MobileProgram;
import net.s5games.mafia.model.Area;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MobProgramView extends EditorView {
    protected JComboBox vnumBox;
    protected JTextArea progArea;
    protected JButton newButton;
    protected JButton deleteButton;

    public MobProgramView(Area ar) {
        super(ar);

        vnumBox = data.getVnumCombo("mprog");

        setLayout(new BorderLayout());
        add(vnumBox, BorderLayout.NORTH);

        progArea = new JTextArea();
        progArea.setLineWrap(true);
        progArea.getDocument().addDocumentListener(new listenerB());
        JScrollPane progPane = new JScrollPane(progArea);
        add(progPane, BorderLayout.CENTER);

        newButton = new JButton("Create New");
        deleteButton = new JButton("Delete Selected");
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new GridLayout(1, 2));
        bPanel.add(newButton);
        bPanel.add(deleteButton);

        add(bPanel, BorderLayout.SOUTH);
        addListeners();
        update();
    }

    public void update() {
        if (data.getMprogCount() == 0) {
            progArea.setText("No program");
            progArea.setEnabled(false);
            vnumBox.setEnabled(false);
            deleteButton.setEnabled(false);
        } else {
            if (vnum == -1 || vnum < data.getLowVnum() || vnum > data.getHighVnum())
                vnum = data.getFirstMprogVnum();

            MobileProgram temp = data.getMProgByVnum(vnum);

            if (temp == null)
                return;

            progArea.setEnabled(true);
            progArea.setText(temp.getProgram());
            vnumBox.setSelectedItem(temp);
            vnumBox.setEnabled(true);
            deleteButton.setEnabled(true);
        }
    }

    public void update(int v) {
        vnum = v;
        update();
    }

    public void addListeners() {
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                * Get the lowest available vnum
                */
                int mVnum = data.getFreeMprogVnum();
                System.out.println("1");
                if (mVnum == -1) {
                    inform("You are out of vnums!");
                    return;
                }

                MobileProgram mprog;
                mprog = new MobileProgram(mVnum, "Editor Code Here");
                data.insert(mprog);
                update(mVnum);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!confirm("Please confirm mobile program deletion. All triggers will be lost."))
                    return;

                data.deleteMprog(vnum);
                vnum = -1;
                update();
            }
        });

        vnumBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MobileProgram temp = (MobileProgram) vnumBox.getSelectedItem();
                if (temp == null)
                    return;

                vnum = temp.getVnum();
                update();
            }
        });

    }

    class listenerB implements DocumentListener {
        public void insertUpdate(DocumentEvent event) {
            go();
        }

        public void removeUpdate(DocumentEvent event) {
            go();
        }

        public void changedUpdate(DocumentEvent event) {
            go();
        }

        void go() {
            if (vnum != -1) {
                MobileProgram temp = data.getMProgByVnum(vnum);
                if (temp == null)
                    return;

                temp.setProgram(progArea.getText());
                vnumBox.updateUI();
            }
        }
    }

}