package net.s5games.mafia.ui;

import net.s5games.mafia.model.MudConstants;
import net.s5games.mafia.model.Area;
import net.s5games.mafia.model.Mobile;
import net.s5games.mafia.model.MudObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
/*
 * George Frick
 * Custom component for a screen to equip a mobile.
 */

public class MobEqPanel extends JPanel {
    JComboBox[] wearSlots;
    Vector[] wearData;
    Area area;
    int vnum;
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

    public MobEqPanel(Area data) {
        area = data;
        MudObject temp;
        int wear = 0;
        int locate = 0;
        int insert = 0;

        this.setLayout(new GridLayout(22, 1));

        wearSlots = new JComboBox[21];
        wearData = new Vector[21];
        for (int a = 0; a < 21; a++) {
            wearData[a] = new Vector();
            wearData[a].add(new String("##### - Nothingness"));
        }

        labels();
        populate();
        create();

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

        for (int z = 0; z < 21; z++) {
            wearSlots[z].addActionListener(new wearSlotListener());
        }
        vnum = -1;
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

    private void create() {
        wearSlots[0] = new JComboBox(wearData[0]);
        wearSlots[6] = new JComboBox(wearData[6]);
        wearSlots[3] = new JComboBox(wearData[3]);
        wearSlots[4] = new JComboBox(wearData[4]);
        wearSlots[1] = new JComboBox(wearData[1]);
        wearSlots[2] = new JComboBox(wearData[2]);
        wearSlots[5] = new JComboBox(wearData[5]);
        wearSlots[7] = new JComboBox(wearData[7]);
        wearSlots[8] = new JComboBox(wearData[8]);
        wearSlots[9] = new JComboBox(wearData[9]);
        wearSlots[10] = new JComboBox(wearData[10]);
        wearSlots[11] = new JComboBox(wearData[11]);
        wearSlots[12] = new JComboBox(wearData[12]);
        wearSlots[13] = new JComboBox(wearData[13]);
        wearSlots[14] = new JComboBox(wearData[14]);
        wearSlots[15] = new JComboBox(wearData[15]);
        wearSlots[16] = new JComboBox(wearData[16]);
        wearSlots[17] = new JComboBox(wearData[17]);
        wearSlots[18] = new JComboBox(wearData[18]);
        wearSlots[19] = new JComboBox(wearData[19]);
        wearSlots[20] = new JComboBox(wearData[20]);
    }

    public void populate() {
        MudObject temp;
        int wear = 0;
        /*
      for( int a = 0; a < 21; a++ )
      {
         wearData[a].clear();
         wearData[a].add(new String("##### - Nothingness"));
      }
        */
        for (int loop = area.getLowVnum(); loop <= area.getHighVnum(); loop++) {
            temp = area.getObject(loop);
            if (temp == null)
                continue;

            wear = temp.getWearFlags();

            // unchoosable if no take flag.
            if ((wear & MudConstants.ITEM_TAKE) != MudConstants.ITEM_TAKE)
                continue;

            wear = (wear ^ MudConstants.ITEM_TAKE);

            if (temp.getType() == MudConstants.ITEM_LIGHT) {
                if (!wearData[0].contains(temp))
                    wearData[0].add(temp);
            }

            if (wear == 0) // wear can only be zero for light sources
                continue;

            switch (wear) {
                case MudConstants.ITEM_WEAR_FINGER: {
                    if (!wearData[1].contains(temp))
                        wearData[1].add(temp);
                    if (!wearData[2].contains(temp))
                        wearData[2].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_NECK: {
                    if (!wearData[3].contains(temp))
                        wearData[3].add(temp);
                    if (!wearData[4].contains(temp))
                        wearData[4].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_BODY: {
                    if (!wearData[5].contains(temp))
                        wearData[5].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_HEAD: {
                    if (!wearData[6].contains(temp))
                        wearData[6].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_LEGS: {
                    if (!wearData[7].contains(temp))
                        wearData[7].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_FEET: {
                    if (!wearData[8].contains(temp))
                        wearData[8].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_HANDS: {
                    if (!wearData[9].contains(temp))
                        wearData[9].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_ARMS: {
                    if (!wearData[10].contains(temp))
                        wearData[10].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_SHIELD: {
                    if (!wearData[11].contains(temp))
                        wearData[11].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_ABOUT: {
                    if (!wearData[12].contains(temp))
                        wearData[12].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_WAIST: {
                    if (!wearData[13].contains(temp))
                        wearData[13].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_WRIST: {
                    if (!wearData[14].contains(temp))
                        wearData[14].add(temp);
                    if (!wearData[15].contains(temp))
                        wearData[15].add(temp);
                    break;
                }
                case MudConstants.ITEM_WIELD: {
                    if (!wearData[16].contains(temp))
                        wearData[16].add(temp);
                    break;
                }
                case MudConstants.ITEM_HOLD: {
                    if (!wearData[17].contains(temp))
                        wearData[17].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_FLOAT: {
                    if (!wearData[18].contains(temp))
                        wearData[18].add(temp);
                    break;
                }
                case MudConstants.ITEM_WEAR_EARS: {
                    if (!wearData[19].contains(temp))
                        wearData[19].add(temp);
                    if (!wearData[20].contains(temp))
                        wearData[20].add(temp);
                    break;
                }
                default:
                    continue;
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
                    Mobile mob = area.getMobile(vnum);

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
        create();
    }

    public void update(int v) {
        System.out.println("update with " + Integer.toString(v));

        if (v == -1) {
            reset();
            return;
        }

        if (vnum == v)
            return;

        vnum = v;

        System.out.println("Populate");
        populate();
        System.out.println("cleareq");
        clearEq();
        //  System.out.println("seteq");
        //  setEq();
        //  System.out.println("repaint");
        //  repaint();
    }

    private void clearEq() {
        for (int a = 0; a < 21; a++) {
            wearSlots[a].setSelectedIndex(0);
            wearSlots[a].setSelectedItem(null);
            wearSlots[a].validate();
        }
        wearSlots[0].setSelectedIndex(0);
        System.out.println("Cleared eq");
    }

    /*
    * this function takes the eq equipped to mobile and
    * sets the combo boxes to that eq.
    */
    private void setEq() {
        boolean equip = false;

        if (vnum == -1)
            return;


        Mobile mob = area.getMobile(vnum);
        MudObject obj;
        int eqVnum;

        if (mob == null)
            return;

        for (int a = 0; a < 21; a++) {
            eqVnum = mob.getEquipment(a);

            if (eqVnum == -1)
                continue;
            else
                equip = true;

            obj = area.getObject(eqVnum);

            if (obj == null)
                continue;

            wearSlots[a].setSelectedItem(obj);
        }
        if (equip == false)
            System.out.println("No eq!");
    }

    private void addCombo(Component a, Component b) {

        //  b.invalidate();
        JPanel temp = new JPanel();
        temp.setLayout(new BorderLayout());
        temp.add(a, BorderLayout.WEST);
        temp.add(b, BorderLayout.EAST);
        //  temp.invalidate();
        this.add(temp);
    }
}