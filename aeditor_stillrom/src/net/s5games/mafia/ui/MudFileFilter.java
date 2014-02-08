// George Frick
// MudFileFilter.java
// Area Editor Project, Spring 2002

package net.s5games.mafia.ui;

import java.io.File;

public class MudFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;

        String fname = f.getName();
        int periodIndex = fname.lastIndexOf('.');

        boolean accepted = false;

        if (periodIndex > 0 && periodIndex < fname.length() - 1) {
            String ext = fname.substring(periodIndex + 1).toLowerCase();
            if (ext.equals("are"))
                accepted = true;
        }

        return accepted;
    }

    public String getDescription() {
        return "Area File (.are)";
    }
}