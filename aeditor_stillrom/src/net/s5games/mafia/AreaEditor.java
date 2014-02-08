// George Frick
// AreaEditor.java
// Area Editor Project, spring 2002
//
// This is the main class of the Area editor, it's purpose being to set
// up the main gui and start the components up. The program is event
// based.

package net.s5games.mafia;

import net.s5games.mafia.model.*;
import net.s5games.mafia.model.impl.AreaImpl;
import net.s5games.mafia.ui.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;

public class AreaEditor extends JFrame {

    // URL url1 = loader.getResource("");
    // ImageIcon icon = new ImageIcon(url1);

    /* resources */
    ClassLoader loader;
    URL b1, b2, b3, mega;
    ImageIcon bullet1, bullet2, bullet3, megadance;

    /* Screen Data */
    Dimension screen;

    /* Layout */
    GridBagLayout myLayout;
    GridBagConstraints constraint;

    /* Tabbed Pane */
    JTabbedPane tabbed;
    OverView myOverView;
    RoomView myRoomView;
    MobView myMobView;
    ObjectView myObjectView;
    ResetView myResetView;
    MobProgramView myMprogView;
    final static int TAB_COUNT = 5;
    /* Tabbed Pane END */

    /* The model */
    Area theArea;
    int areaType; // Rom/Cynthe/Animud

    /* FILE MENU */
    JMenuBar topBar;
    JMenu fileMenu;        // File:
    JMenuItem fileNew;     // File->new
    JMenuItem fileOpen;    // File->open
    JMenuItem fileClose;   // File->close
    JMenuItem fileSave;    // File->save
    JMenuItem fileSaveAs;  // File->save as
    JMenuItem fileQuit;    // File->quit
    JMenu recentFileMenu;  // File->Recent:
    JMenuItem[] recentFileItems;
    JMenu aboutMenu;
    JMenuItem aboutItem1;
    JFileChooser fileChooser;

    /* FILE MENU END */
    AreaEditor parent;

    /* preferences and data */
    String[] recentFiles;
    boolean openFile;
    boolean saved;

    public AreaEditor(String title) {
        /************************************************************
         *                                                          *
         *	Initialize					                          *
         *										                  *
         ************************************************************/
        super(title);

        loader = ClassLoader.getSystemClassLoader();
        b1 = loader.getResource("bullet.gif");
        b2 = loader.getResource("bullet2.gif");
        b3 = loader.getResource("bullet3.gif");
        mega = loader.getResource("megadance.gif");
        bullet1 = new ImageIcon(b1);
        bullet2 = new ImageIcon(b2);
        bullet3 = new ImageIcon(b3);
        megadance = new ImageIcon(mega);
        recentFiles = new String[5];
        openFile = false;
        saved = false;

        parent = this;
        theArea = new AreaImpl();
        areaType = MudConstants.NO_AREA;
        myOverView = new OverView(theArea);
        myRoomView = new RoomView(theArea);
        myObjectView = new ObjectView(theArea);
        myMobView = new MobView(theArea);
        myMprogView = new MobProgramView(theArea);
        // myResetView = new ResetView(theArea);
        myLayout = new GridBagLayout();
        constraint = new GridBagConstraints();
        Container mp = this.getContentPane();
        mp.setLayout(myLayout);

        /************************************************************
         *                                                          *
         *	Create Contents				                          *
         *                                                          *
         ************************************************************/
        addFileMenu();
        tabbed = new JTabbedPane(JTabbedPane.TOP);

        /************************************************************
         *                                                          *
         *	These are the tabs for the tabbed pane                *
         *										                  *
         ************************************************************/
        tabbed.addTab("OverView", null, myOverView, "Overview of model stats.");
        tabbed.addTab("Rooms", null, myRoomView, "Room Editor.");
        tabbed.addTab("Mobs", null, myMobView, "Mob Editor.");
        tabbed.addTab("Objects", null, myObjectView, "Object Editor.");
        tabbed.addTab("Mprogs", null, myMprogView, "Mob Program Editor.");
        // tabbed.addTab("Resets",null,myResetView,"Area resets");

        for (int a = 0; a < TAB_COUNT; a++) {
            tabbed.setEnabledAt(a, false);
        }
        tabbed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                JTabbedPane pane = (JTabbedPane) evt.getSource();
                Component temp = pane.getComponentAt(pane.getSelectedIndex());
                ((EditorView) temp).update();
                /*if( temp instanceof OverView )
               {
                  OverView t2 = (OverView)temp;
                  System.out.println("changed tab, updating");
                  t2.update();
               } */
            }
        });

        tabbed.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
            }
        });

        /************************************************************
         *                                                          *
         *	Add everything to window		                      *
         *										                  *
         ************************************************************/
        setJMenuBar(topBar);
        constraint.gridy = 1;
        constraint.gridx = 0;
        constraint.fill = GridBagConstraints.BOTH;
        constraint.weighty = 1;
        constraint.weightx = 1;
        myLayout.setConstraints(tabbed, constraint);
        mp.add(tabbed);

        /************************************************************
         *                                                          *
         *	Create/Setup the window			                      *
         *										                  *
         ************************************************************/
        addWindowListener(new WindowEventHandler());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen.setSize(screen.getWidth(), (double) (screen.getHeight() - 20));
        setSize(screen);
        fileClose.setEnabled(false);
        fileSave.setEnabled(false);
        fileSaveAs.setEnabled(false);
        // pack();
        this.setDefaultLookAndFeelDecorated(true);
        validate();
        show();

        // maximize frame      10/29/03
        int fState = this.getExtendedState();
        fState |= Frame.MAXIMIZED_BOTH;
        this.setExtendedState(fState);

        // kill resize
        this.setResizable(false);

        // set icon
        Image fIcon = Toolkit.getDefaultToolkit().getImage(b1);
        this.setIconImage(fIcon);

        requestFocus();
        readPreferences();

        // Load races.
        RaceTable.Instance().loadRaceTable("race.txt");
    }

    /*
    * Function to update data in each tab.
    */
    public void update() {
        myOverView.update();
        myRoomView.update();
        myObjectView.update();
        myMobView.update();
        myMprogView.update();
    }

    /*
    * Turns the tabs on(requires new or open action)
    */
    private void enableTabs() {
        for (int loop = 0; loop < TAB_COUNT; loop++)
            tabbed.setEnabledAt(loop, true);
    }

    private void leaveEditor() {
        /*
        * We should have a 'saved' flag and check it
        * asking the user if they want to save first...
        */
        JPrefs pref = JPrefs.getPrefs();
        pref.writePreferences();
        writePreferences();
        System.exit(0);
    }

    private void readPreferences() {

        System.out.print("Loading preferences...");
        JPrefs pref = JPrefs.getPrefs();
        //pref.addPreference("Suppress Exit Error","0");
        try {
            int count;
            String temp;
            RomLoader myLoader = new RomLoader("prefs.txt");
            if (!myLoader.isOpen()) {
                System.out.println("No preferences file, creating as needed.");
                return;
            }

            temp = myLoader.getLine();
            count = Integer.parseInt(temp);

            for (int a = 0; a < count; a++)
                addRecentFile(myLoader.getLine());

            System.out.println("...Complete.");
        }
        catch (Exception exc) {
            System.out.println("...Aborted, error in preference file.");
        }
    }

    private void addRecentFile(String toAdd) {
        // have to change so pushes files out the back...
        File temp = new File(toAdd);

        if (!temp.canRead()) {
            System.out.println("\nFile does not exist: " + toAdd);
            return;
        }

        for (int a = 0; a < 6; a++) {
            if (recentFiles[a] == null) {
                recentFiles[a] = toAdd;
                recentFileItems[a] = new JMenuItem(new File(toAdd).getName());
                recentFileMenu.add(recentFileItems[a]);
                recentFileItems[a].addActionListener(new recentFilesListener());
                return;
            } else if (recentFiles[a].equals(toAdd))
                return;
        }
        // if we're here, we need to push one out.
        recentFileMenu.removeAll();
        for (int a = 0; a < 4; a++) {
            recentFiles[a] = recentFiles[a + 1];
            recentFileItems[a] = recentFileItems[a + 1];
            recentFileMenu.add(recentFileItems[a]);
        }
        recentFiles[4] = toAdd;
        recentFileItems[4] = new JMenuItem(new File(toAdd).getName());
        recentFileMenu.add(recentFileItems[4]);
        recentFileItems[4].addActionListener(new recentFilesListener());
    }

    private void writePreferences() {
        RomWriter writer = new RomWriter("prefs.txt");
        int count = 0;

        if (!writer.isOpen())
            return;

        for (int a = 0; a < 5; a++)
            if (recentFiles[a] != null)
                count++;
            else
                break;

        writer.romWrite(Integer.toString(count) + "\n");
        for (int a = 0; a < count; a++)
            writer.romWrite(recentFiles[a] + "\n");

        writer.finish();
    }

    /*
    * File->exit
    */
    class quitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            leaveEditor();
        }
    }

    /*
    *  About->about
    */
    class aboutListener implements ActionListener {
        JFrame myparent;

        public aboutListener(JFrame p) {
            super();
            myparent = p;
        }

        public void actionPerformed(ActionEvent e) {
            JPanel temp = new JPanel();
            temp.setLayout(new BorderLayout());
            JLabel msg = new JLabel("<HTML><BODY><BOLD><HR>Thank you for using the MAFIA model editor!</BOLD><BR> This editor was designed and created by <A HREF=mailto:'tenchi@s5games.net'>George Frick</A><P>GUI design by George Frick and Scott Emerson of <A HREF='http://www.s5games.net'>CaffeineGamez</A></P><HR></BODY></HTML>");
            temp.add(msg, BorderLayout.NORTH);
            JLabel msg2 = new JLabel("<HTML><BODY><BOLD>Special thanks to all beta testers and players of Animud.</BOLD></BODY></HTML>");
            temp.add(msg2, BorderLayout.CENTER);
            temp.add(new JLabel(megadance), BorderLayout.SOUTH);

            JOptionPane.showMessageDialog(myparent, temp, "About the MAFIA editor",
                    JOptionPane.PLAIN_MESSAGE);
            System.out.println("About...");
        }
    }

    /*
    * File->quit, or [X] button
    */
    class WindowEventHandler extends WindowAdapter {
        public void windowClosing(WindowEvent evt) {
            leaveEditor();
        }
    }

    /*
    * File->open
    */
    class openListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            JMenuItem m = (JMenuItem) a.getSource();
            if (m == fileOpen) {
                fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new MudFileFilter());
                fileChooser.setFileView(new MudFileView());
                int selected = fileChooser.showOpenDialog(parent.getContentPane());

                if (selected == JFileChooser.APPROVE_OPTION) {
                    openFile(fileChooser.getSelectedFile());
                    fileSave.setEnabled(true);
                    fileSaveAs.setEnabled(true);
                } else if (selected == JFileChooser.CANCEL_OPTION) {
                    System.out.println("Canceled Load");
                } else
                    return;
            }
        }
    }

    /*
    *  File->save as
    */
    class saveAsListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            JMenuItem m = (JMenuItem) a.getSource();
            if (m == fileSaveAs && openFile) {
                fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new MudFileFilter());
                fileChooser.setFileView(new MudFileView());
                try {
                    File ft = new File(new File(theArea.getFileName()).getCanonicalPath());
                    fileChooser.setSelectedFile(ft);
                }
                catch (Exception fError) {
                }

                int selected = fileChooser.showSaveDialog(parent.getContentPane());

                if (selected == JFileChooser.APPROVE_OPTION) {
                    RomWriter writer = new RomWriter(fileChooser.getSelectedFile());
                    writer.writeArea(theArea);
                    theArea.setPathName(fileChooser.getSelectedFile().getPath());
                    fileSave.setEnabled(true);
                    addRecentFile(fileChooser.getSelectedFile().getAbsolutePath());
                } else if (selected == JFileChooser.CANCEL_OPTION) {
                    System.out.println("Canceled Save");
                } else
                    return;
            }
        }
    }

    /*
    * File->save
    */
    class saveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String fileName1 = theArea.getPathName();
            if (fileName1 == null) {
                fileSave.setEnabled(false);
                return;
            }
            RomWriter writer = new RomWriter(fileName1);
            writer.writeArea(theArea);
        }
    }

    private void openFile(File toOpen) {
        //theArea = null;
        //openFile = false;
        //update();
        theArea.clear();
        RomLoader loader = new RomLoader(toOpen);
        theArea = loader.readArea(theArea);
        theArea.transformResets();
        update();
        enableTabs();
        openFile = true;
        addRecentFile(toOpen.getAbsolutePath());
        fileSave.setEnabled(true);
        fileSaveAs.setEnabled(true);
        fileClose.setEnabled(true);
    }

    /*
    * File->Recent files -> "some file"
    */
    class recentFilesListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JMenuItem temp = (JMenuItem) e.getSource();
            for (int a = 0; a < 5; a++) {
                if (temp == recentFileItems[a]) {
                    openFile(new File(recentFiles[a]));
                }
            }
        }
    }

    /*
    * File->Close
    */
    class closeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            closeArea();
            tabbed.setSelectedIndex(0);
        }
    }

    private void closeArea() {
        theArea.clear();
        update();
        for (int a = 0; a < 5; a++) {
            tabbed.setEnabledAt(a, false);
        }
        fileClose.setEnabled(false);
        fileSave.setEnabled(false);
        fileSaveAs.setEnabled(false);
    }

    /*
    * File->New
    */
    class newListener implements ActionListener {
        JLabel[] labels;
        JTextField[] fields;
        private GridBagConstraints constraint;
        private GridBagLayout layout;
        JPanel temp;

        public newListener(JFrame p) {
            super();
            labels = new JLabel[6];
            fields = new JTextField[6];
            for (int a = 0; a < 6; a++) {
                fields[a] = new JTextField(20);
            }
            temp = new JPanel();
            layout = new GridBagLayout();
            constraint = new GridBagConstraints();
            temp.setSize(new Dimension(300, 200));
            temp.setPreferredSize(new Dimension(500, 300));
            temp.setLayout(layout);
            constraint.insets = new Insets(3, 3, 3, 3);
            constraint.gridwidth = 2;
            constraint.gridheight = 1;
            constraint.gridy = 0;
            constraint.gridx = 0;
            JLabel l = new JLabel(
                    "Creating a new model requires a basic amount of information, ");
            JLabel l2 = new JLabel(
                    "You can retrieve this information from your head builder or ");
            JLabel l3 = new JLabel(
                    "implementor, so ask them about this part if it's confusing.");
            layout.setConstraints(l, constraint);
            temp.add(l);
            constraint.gridy = 1;
            layout.setConstraints(l2, constraint);
            temp.add(l2);
            constraint.gridy = 2;
            layout.setConstraints(l3, constraint);
            temp.add(l3);
            constraint.gridwidth = 1;
            constraint.gridheight = 1;
            labels[0] = new JLabel("File Name : ", JLabel.RIGHT);
            labels[1] = new JLabel("Area Name :", JLabel.RIGHT);
            labels[2] = new JLabel("Builder   :", JLabel.RIGHT);
            labels[3] = new JLabel("Security  :", JLabel.RIGHT);
            labels[4] = new JLabel("Lower Vnum:", JLabel.RIGHT);
            labels[5] = new JLabel("High Vnum :", JLabel.RIGHT);
            for (int a = 0; a < 6; a++) {
                constraint.gridx = 0;
                constraint.gridy = a + 3;
                layout.setConstraints(labels[a], constraint);
                temp.add(labels[a]);
                constraint.gridx = 1;
                layout.setConstraints(fields[a], constraint);
                temp.add(fields[a]);
            }
        }

        public void actionPerformed(ActionEvent e) {
            int choice = -1;
            int errornum = -1;
            Object[] options = {"OK", "CANCEL"};
            do {
                errornum = -1;
                choice = JOptionPane.showOptionDialog(null, temp, "Creating a new model",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
                if (choice == 0) {
                    // for now, simple check...
                    for (int a = 0; a < 6; a++) {
                        if (fields[a].getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "-All- fields must be entered!", "Couldn't Create New Area!", JOptionPane.ERROR_MESSAGE);
                            System.out.println("Couldn't create new!");
                            errornum = 1;
                            break;
                        }
                    }
                    // Make sure got good input!
                } else {
                    // choice == 1
                    // they cancelled, do nothing.
                    System.out.println("Cancelled new.");
                    return;
                }
            } while (choice == 0 &&
                    (errornum == 1 || myOverView.checkBasicData(fields) == false));

            closeArea();
            theArea.setFileName(fields[0].getText());
            theArea.setAreaName(fields[1].getText());
            theArea.setBuilder(fields[2].getText());
            theArea.setSecurity(Integer.parseInt(fields[3].getText()));
            theArea.setVnumRange(Integer.parseInt(fields[4].getText()), Integer.parseInt(fields[5].getText()));
            enableTabs();
            openFile = true;
            saved = false;
            update();
            fileClose.setEnabled(true);
            fileSave.setEnabled(false);
            fileSaveAs.setEnabled(true);
        }
    }

    class DumpResetListener implements ActionListener {

        public DumpResetListener(JFrame parent) {
            super();
        }

        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Resets dumped to file: resets.dmp", "Dumped resets to file",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    /*
    *  This function adds the file menu and sets it up.
    * It also adds the listeners.
    */
    private void addFileMenu() {
        // Create a File menu and add it to the program.
        topBar = new JMenuBar();
        topBar.setBorder(new BevelBorder(BevelBorder.RAISED));

        // File Menu
        fileMenu = new JMenu("File", true);
        fileMenu.setIcon(bullet1);
        fileMenu.setMnemonic('F');

        // File-> New, Open, Convert, Quit
        // New
        fileNew = new JMenuItem("New");
        fileNew.setMnemonic('N');
        fileNew.setIcon(bullet2);
        fileNew.addActionListener(new newListener(this));
        // Open
        fileOpen = new JMenuItem("Open");
        fileOpen.setMnemonic('O');
        fileOpen.setIcon(bullet2);
        fileOpen.addActionListener(new openListener());
        // Close
        fileClose = new JMenuItem("Close");
        fileClose.setMnemonic('C');
        fileClose.setIcon(bullet2);
        fileClose.addActionListener(new closeListener());
        // Quit
        fileQuit = new JMenuItem("Quit");
        fileQuit.setMnemonic('Q');
        fileQuit.setIcon(bullet2);
        fileQuit.addActionListener(new quitListener());
        // Save
        fileSave = new JMenuItem("Save");
        fileSave.setMnemonic('S');
        fileSave.setIcon(bullet2);
        fileSave.addActionListener(new saveListener());
        // Save as
        fileSaveAs = new JMenuItem("Save As...");
        fileSaveAs.setMnemonic('X');
        fileSaveAs.setIcon(bullet2);
        fileSaveAs.addActionListener(new saveAsListener());
        // recent files.
        recentFileMenu = new JMenu("Open Recent", true);
        recentFileMenu.setIcon(bullet2);
        recentFileItems = new JMenuItem[5];

        // Add them all to the File menu
        fileMenu.add(fileNew);
        fileMenu.add(fileOpen);
        fileMenu.add(fileClose);
        fileMenu.add(fileSave);
        fileMenu.add(fileSaveAs);
        fileMenu.add(fileQuit);
        fileMenu.addSeparator();
        fileMenu.add(recentFileMenu);
        // readPreferences();

        // About Menu
        aboutMenu = new JMenu("About");
        aboutMenu.setIcon(bullet3);
        aboutMenu.setMnemonic('A');

        // About-> about
        // About
        aboutItem1 = new JMenuItem("About");
        aboutItem1.setIcon(bullet2);
        aboutItem1.addActionListener(new aboutListener(this));

        // Add them all to the About Menu
        aboutMenu.add(aboutItem1);

        // Add everything to layout
        topBar.add(fileMenu);
        topBar.add(aboutMenu);
    }

    /*
    * Main function
    *  // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
    */
    public static void main(String[] args) {
        AreaEditor ed = new AreaEditor("Animud/Rom/Cynthe Area Editor");
    }
}