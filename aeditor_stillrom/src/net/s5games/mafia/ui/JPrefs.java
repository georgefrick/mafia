package net.s5games.mafia.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

public class JPrefs {
    private static JPrefs prefs;
    private static boolean loaded = false;

    protected File inputFile;
    protected FileInputStream inStream;
    protected DataInputStream dataStream;
    protected BufferedInputStream inbuf1;
    protected BufferedReader buf2 = null;
    protected FileOutputStream outStream;
    protected BufferedWriter outbuf;
    protected JPanel sPanel;
    Vector data;

    private JPrefs() {
        data = new Vector();
        sPanel = new JPanel();
        loadPreferences();
    }

    public static synchronized JPrefs getPrefs() {
        if (prefs == null) {
            prefs = new JPrefs();
            loaded = true;
        }

        return prefs;
    }

    public JPanel getPanel() {
        return sPanel;
    }

    public void addPreference(DataItem temp) {
        addPreference(temp.getId(), temp.getValue(), temp.getType());
    }

    public void addPreference(String i, String v, int t) {
        DataItem temp = new DataItem(i, v, t);
        if (temp.getType() == 0) {
            JCheckBox tbox = new JCheckBox(temp.getId());
            if (temp.getValue().equals("1"))
                tbox.setSelected(true);
            else
                tbox.setSelected(false);

            sPanel.add(tbox);
            tbox.addActionListener(new JPrefListener());
        } else {
            inform("Bad preference: " + temp);
            return;
        }
        System.out.println("added " + temp);
        data.add(temp);
    }

    public void removePreference(String i) {
        return;
    }

    public String getPreference(String i) {
        Enumeration e;
        DataItem temp;

        for (e = data.elements(); e.hasMoreElements();) {
            temp = (DataItem) e.nextElement();
            if (temp.getId().equals(i))
                return temp.getValue();
        }

        return "1";
    }

    // this will add to any preferences that are there.
    public void loadPreferences() {
        System.out.println("Loading preferences");
        PrefProgress p = new PrefProgress();
        p.loop();
        try {
            inputFile = new File("setting.txt");
            if (inputFile == null)
                return;

            inStream = new FileInputStream(inputFile);
            buf2 = new BufferedReader(new InputStreamReader(inStream));
            String temp = getLine();
            while (temp != null) {
                System.out.println("read: " + temp);
                DataItem it = new DataItem(temp);
                addPreference(it);
                temp = getLine();
            }
            buf2.close();
        }
        catch (Exception e) {
            inform("Error loading preferences");
        }
        p.stop();
        return;
    }

    public void writePreferences() {
        File ofile = new File("setting.txt");
        try {
            outStream = new FileOutputStream(inputFile);
            outbuf = new BufferedWriter(new OutputStreamWriter(outStream));
            Enumeration e;
            DataItem temp;

            for (e = data.elements(); e.hasMoreElements();) {
                temp = (DataItem) e.nextElement();
                writeLine(temp.getId() + "," + temp.getValue() + "," + temp.getType());
                //writeLine( temp.toString() );
            }
            outbuf.flush();
            outbuf.close();
        }
        catch (Exception e) {
        }

    }

    private String getLine() {
        if (buf2 == null)
            return null;

        try {
            return buf2.readLine();
        }
        catch (Exception e) {
            return null;
        }
    }

    private void writeLine(String s) {
        try {
            outbuf.write(s, 0, s.length());
        }
        catch (Exception e) {
            System.out.println("Error writing to file!!!");
        }
    }

    public void dumpToPrompt() {
        Enumeration e;
        DataItem temp;

        for (e = data.elements(); e.hasMoreElements();) {
            temp = (DataItem) e.nextElement();
            System.out.println(temp);
        }
    }

    protected static void inform(String msg) {
        JOptionPane.showMessageDialog(null, msg, msg, JOptionPane.WARNING_MESSAGE);
    }

    private class PrefProgress {
        JWindow window;
        JProgressBar pBar;
        JPanel holdPanel;

        PrefProgress() {
            pBar = new JProgressBar();
            pBar.setIndeterminate(true);

            holdPanel = new JPanel();
            holdPanel.setLayout(new BorderLayout());
            holdPanel.add(pBar, BorderLayout.SOUTH);
            holdPanel.add(new JLabel("......Loading Preferences......"),
                    BorderLayout.NORTH);

            window = new JWindow();
            window.getContentPane().add(holdPanel, BorderLayout.CENTER);
        }

        void loop() {
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension second = holdPanel.getPreferredSize();
            window.setSize(second);
            int w = ((int) screen.getWidth() / 2) - ((int) second.getWidth() / 2);
            int h = ((int) screen.getHeight() / 2) - ((int) second.getHeight() / 2);
            window.setLocation(w, h);
            window.setVisible(true);
        }

        void stop() {
            window.setVisible(false);
            window = null;
            System.gc();
        }
    }

    private class DataItem {
        String id, value;
        int type;

        DataItem(String parse) {
            System.out.println("creating");
            int temp = parse.indexOf(',');
            id = parse.substring(0, temp);
            int temp2 = parse.indexOf(',', temp + 1);
            value = parse.substring(temp + 1, temp2);
            type = Integer.parseInt(parse.substring(temp2 + 1));
            System.out.println("Created: " + toString());
            // parse string and assign.
        }

        DataItem(String a, String b) {
            id = a;
            value = b;
            type = 0;
        }

        DataItem(String a, String b, int t) {
            id = a;
            value = b;
            type = t;
        }

        String getValue() {
            return value;
        }

        String getId() {
            return id;
        }

        int getType() {
            return type;
        }

        void setValue(String nVal) {
            value = nVal;
        }

        public String toString() {
            return id + " " + value + " " + Integer.toString(type);
        }
    }

    class JPrefListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Enumeration en;
            DataItem temp;

            if (e.getSource() instanceof JCheckBox) {
                JCheckBox me = (JCheckBox) e.getSource();

                for (en = data.elements(); en.hasMoreElements();) {
                    temp = (DataItem) en.nextElement();
                    if (me.getText().equals(temp.getId())) {
                        if (me.isSelected())
                            temp.setValue("1");
                        else
                            temp.setValue("0");
                    }
                }

            }
        }
    }
}