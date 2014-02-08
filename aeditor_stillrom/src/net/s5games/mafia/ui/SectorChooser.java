// George Frick
// SectorChooser.java
// Area Editor Project, Spring 2002
package net.s5games.mafia.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class SectorChooser extends JComboBox {
    private final static String[] sectors =
            {"Inside", "City", "Field", "Forest", "Hills", "Mountain",
                    "Swim", "No swim", "Unused", "Air", "Desert", "Jungle",
                    "Underwater", "Marsh", "Swamp", "Tundra", "Rainforest", "Cavern"};

    public static final int NUM_SECTORS = 18; // 18 sectors

    public SectorChooser() {
        super(sectors);
        setBorder(new TitledBorder("Sector"));
        setMaximumRowCount(10);
        setEditable(false);
    }

    public String currentSector() {
        String s = (String) getSelectedItem();
        return s;
    }

    public int getSector() {
        for (int a = 0; a < NUM_SECTORS; a++)
            if (sectors[a].equals((String) getSelectedItem()))
                return a;

        return -1;
    }

    public void setCurrentSector(String s) {
        setSelectedItem(s);
    }

    public void setCurrentSector(int s) {
        setSelectedItem(sectors[s]);
    }
}