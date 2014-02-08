// George Frick
// Area.java
// Area Editor Project, January, February, March 2002
//
// The purpose of this class is as the main data class for
// holding the information for an model. It holds the main
// model data: a vector of the rooms, objects and mobiles.
// It can have no model loaded, an empty model(new) or a loaded
// model. It does not have it's own file i/o as different
// model file formats need to be supported, it is instead filled
// by the different file format readers.

package net.s5games.mafia.model.impl;

import net.s5games.mafia.model.MobTrigger;
import net.s5games.mafia.model.MobileProgram;
import net.s5games.mafia.model.MudConstants;
import net.s5games.mafia.model.MudShopData;
import net.s5games.mafia.model.*;

import javax.swing.JComboBox;
import javax.swing.JList;
import java.util.Collection;
import java.util.Vector;

public class AreaImpl implements Area {

    private String fileName;
    private String pathName;
    private String areaName;
    private String builder;
    private int lowVnum;
    private int highVnum;
    private int security;
    private int flags;
    private boolean validArea;
    private boolean changed;

    private Collection<Room>          rooms;
    private Collection<MudObject>     objects;
    private Collection<Mobile>        mobs;
    private Collection<MudReset>      resets;
    private Collection<MobileProgram> mobprogs;

    private final static String nodata = "No Data Loaded.";

    // Creates an model file with given filename reference. Initiates data.
    public AreaImpl(String file) {
        this();
        fileName = file;
    }

    // Creates blank model file, no filename. Initiates data.
    public AreaImpl() {
        rooms = new Vector<Room>();
        objects = new Vector<MudObject>();
        mobs = new Vector<Mobile>();
        resets = new Vector<MudReset>();
        mobprogs = new Vector<MobileProgram>();
        noData();
    }

    // Files the model with blank data.
    private void noData() {
        lowVnum = 0;
        highVnum = 0;
        fileName = nodata;
        pathName = null;
        areaName = nodata;
        builder = nodata;
        validArea = false;
        flags = 0;
        security = 0;
        changed = false;
    }

    // Accessors
    public String getAreaName() {
        return areaName;
    }

    public String getBuilder() {
        return builder;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPathName() {
        return pathName;
    }

    public int getSecurity() {
        return security;
    }

    public int getLowVnum() {
        return lowVnum;
    }

    public int getHighVnum() {
        return highVnum;
    }

    public int getRoomCount() {
        return rooms.size();
    }

    public int getMobCount() {
        return mobs.size();
    }

    public int getMprogCount() {
        return mobprogs.size();
    }

    public int getResetCount() {
        return resets.size();
    }

    public int getObjectCount() {
        return objects.size();
    }

    public int getFlags() {
        return flags;
    }

    public boolean valid() {
        return validArea;
    }

    public boolean changed() {
        return changed;
    }

    public Collection<MudReset> getResets() {
        return resets;
    }

    public boolean isValidVnum(int vnum) {
        return !(vnum < lowVnum || vnum > highVnum);
    }

    /*
    *  First mprog in the model
    */
    public int getFirstMprogVnum() {
        if( mobprogs.isEmpty())
            return -1;

        return mobprogs.iterator().next().getVnum();
    }

    /*
    *  First room in the model
    */
    public int getFirstRoomVnum() {
        if( rooms.isEmpty())
            return -1;

        return rooms.iterator().next().getVnum();
    }

    /*
    *  First mobile in model.
    */
    public int getFirstMobVnum() {
        if( mobs.isEmpty())
            return -1;

        return mobs.iterator().next().getVnum();
    }

    /*
    *  First object in model.
    */
    public int getFirstObjectVnum() {
        if( objects.isEmpty())
            return -1;

        return objects.iterator().next().getVnum();
    }

    // call countMobile for every room and add.
    public int maxMob(int vnum) {
        int total = 0;

        if (vnum < getLowVnum() || vnum > getHighVnum())
            return 0;

        for( Room room : rooms) {
            total += room.countMobile(vnum);
        }

        return total;
    }

    /*
    * Get room of specified vnum
    */
    public Room getRoom(int vnum) {

        if (vnum < getLowVnum() || vnum > getHighVnum())
            return null;

        for( Room room : rooms) {
            if( room.getVnum() == vnum)
                return room;
        }

        return null;
    }

    /*
    * Get object of specified vnum
    */
    public MudObject getObject(int vnum) {

        if (vnum < getLowVnum() || vnum > getHighVnum())
            return null;

        for( MudObject object : objects) {
            if( object.getVnum() == vnum)
                return object;
        }

        return null;
    }

    /*
    * Get mobile of specified vnum
    */
    public Mobile getMobile(int vnum) {

        if (vnum < lowVnum || vnum > highVnum)
            return null;

        for( Mobile mob : mobs) {
            if( mob.getVnum() == vnum)
                return mob;
        }

        return null;
    }

    public MobileProgram getMProgByVnum(int vnum) {

        if (vnum < lowVnum || vnum > highVnum)
            return null;

        for( MobileProgram mprog : mobprogs) {
            if( mprog.getVnum() == vnum)
                return mprog;
        }

        return null;
    }

    public Collection<MobileProgram> getMobprogs() {
        return mobprogs;
    }

    public void setMobprogs(Collection<MobileProgram> mobprogs) {
        this.mobprogs = mobprogs;
    }

    public void removeMobileProgram(MobileProgram dMProg) {
        mobprogs.remove(dMProg);
    }

    /*
    * Get an available mob program vnum
    */
    public int getFreeMprogVnum() {
        if (mobprogs.isEmpty())
            return getLowVnum();
        else {
            int vnum;
            for (vnum = lowVnum; vnum <= highVnum; vnum++) {
                if (getMProgByVnum(vnum) == null)
                    return vnum;
            }
            return -1;
        }
    }

    /*
    * Get an available room vnum
    */
    public int getFreeRoomVnum() {
        if (rooms.isEmpty())
            return getLowVnum();
        else {
            int vnum;
            for (vnum = lowVnum; vnum <= highVnum; vnum++) {
                if (getRoom(vnum) == null)
                    return vnum;
            }
            return -1;
        }
    }

    /*
    * get an available mobile vnum
    */
    public int getFreeMobileVnum() {
        if (mobs.isEmpty())
            return getLowVnum();
        else {
            int vnum;
            for (vnum = lowVnum; vnum <= highVnum; vnum++) {
                if (getMobile(vnum) == null)
                    return vnum;
            }
            return -1;
        }

    }

    /*
    * get an available object vnum
    */
    public int getFreeObjectVnum() {
        if (objects.isEmpty())
            return getLowVnum();
        else {
            int vnum;
            for (vnum = lowVnum; vnum <= highVnum; vnum++) {
                if (getObject(vnum) == null)
                    return vnum;
            }
            return -1;
        }

    }

    /*
    * returns the object to mob to room ratio R:O:M
    */
    public String getRatio() {
        int mob = getMobCount();
        int room = getRoomCount();
        int obj = getObjectCount();

        for (int a = 99; a > 0; a--) {
            if (mob % a == 0 && room % a == 0 && obj % a == 0) {
                mob = mob / a;
                room = room / a;
                obj = obj / a;
                break;
            }
        }

        return Integer.toString(obj) + ":" + Integer.toString(mob) + ":" + Integer.toString(room);
    }

    // Modifiers
    public void setBuilder(String newbuilder) {
        builder = newbuilder;
    }

    public void setAreaName(String newname) {
        areaName = newname;
    }

    public void setFileName(String newname) {
        fileName = newname;
    }

    public void setPathName(String newpath) {
        pathName = newpath;
    }

    public void setVnumRange(int newLowVnum, int newHighVnum) {
        if (newHighVnum < newLowVnum) {
            setVnumRange(newHighVnum, newLowVnum);
            return;
        }

        lowVnum = newLowVnum;
        highVnum = newHighVnum;
        validArea = true;
    }

    public void setSecurity(int newSecurity) {
        if (newSecurity >= 1 && newSecurity <= 9)
            security = newSecurity;
    }

    public void setFlags(int newFlags) {
        flags = newFlags;
    }

    /*
    * Insert an object, it inserts the object into the proper
    * array based on its type.
    */
    public void insert(Object m) {
        if (m instanceof Room) {
            rooms.add((Room) m);
        } else if (m instanceof MudObject) {
            objects.add((MudObject) m);
        } else if (m instanceof Mobile) {
            mobs.add((Mobile) m);
        } else if (m instanceof MudReset) {
            resets.add((MudReset) m);
        } else if (m instanceof MudShopData) {
            MudShopData temp = (MudShopData) m;
            int kVnum = temp.getKeeper();
            Mobile mTemp = getMobile(kVnum);
            if (mTemp == null)
                return;

            mTemp.setShop(temp);
        } else if (m instanceof MobileProgram) {
            mobprogs.add((MobileProgram) m);
        } else {
            System.out.println("Unknown type inserted into model.");
        }
    }

    // revnum this model.
    public void reVnum(int newVnum) {
        int oldLow = getLowVnum();
        int diff = newVnum - oldLow;

        lowVnum = newVnum;
        highVnum = highVnum + diff;

        // Start by revnuming the rooms.
        // * Rooms vnum
        // * Rooms exits
        for( Room temp : rooms) {
            temp.setVnum(temp.getVnum() + diff);
            MudExit exit;
            for (int a = 0; a < MudConstants.MAX_EXITS; a++) {
                exit = temp.getExit(a);
                if (exit == null)
                    continue;

                exit.setToVnum(exit.getToVnum() + diff);
                if (exit.getKey() >= oldLow)
                    exit.setKey(exit.getKey() + diff);
            }
        }

        // Revnum every mobile.
        // Mobiles Vnum
        // Mobiles Group
        // equipment slots
        // Triggers
        // Shop
        for( Mobile temp : mobs) {
            temp.setVnum(temp.getVnum() + diff);
            if (temp.getGroup() != 0)
                temp.setGroup(temp.getGroup() + diff);

            for (int w = 0; w < 21; w++) {
                if (temp.getEquipment(w) != -1)
                    temp.equipMobile(w, temp.getEquipment(w) + diff);
            }
            for( MobTrigger trigger : temp.getTriggers()) {
                trigger.setVnum(trigger.getVnum() + diff);
            }
            if (temp.isShop())
                temp.getShop().setKeeper(temp.getShop().getKeeper() + diff);
            // mob programs attached to mobile need to be done here.
        }

        // revnum every object
        // objects vnum
        // portal vnum
        // container key
        for( MudObject temp : objects) {
            temp.setVnum(temp.getVnum() + diff);

            if (temp.getType() == MudConstants.ITEM_PORTAL)
                temp.setiValue(3, temp.getiValue(3) + diff);

            if (temp.getType() == MudConstants.ITEM_CONTAINER) {
                if (temp.getiValue(2) != 0)
                    temp.setiValue(2, temp.getiValue(2) + diff);
            }
        }

        // revnum every mprog
        // mprog's vnum
        // vnums in text of mprog(how the hell...)
        for( MobileProgram temp : mobprogs) {
            temp.setVnum(temp.getVnum() + diff);
            // have to scan the text of the mobprogram, looking for vnums in this
            // model range, and changing them to new vnum.
        }

        // change shops(done by mobile change)

    }

    public void deleteMprog(int vnum) {

        for( MobileProgram mprog : mobprogs) {
            if( mprog.getVnum() == vnum) {
                mobprogs.remove(mprog);
            }
        }

        for( Mobile mob : mobs) {
            mob.deleteTriggers(vnum);
        }
    }

    public void deleteObject(int vnum) {

        for( MudObject oTemp : objects) {
            if (oTemp.getVnum() == vnum) {
                // clear from vector
                objects.remove(oTemp);

                for( Mobile mTemp : mobs) {
                    mTemp.takeFromMobile(oTemp);
                    mTemp.unEquip(vnum);
                }

                for( MudObject inside : objects) {
                    if( inside.isContainer()) {
                        inside.removeObject(oTemp);
                        inside.setiValue(2,-1);
                    }
                }

                for( Room loc : rooms) {
                    loc.removeObject(oTemp);
                }

                return;
            }
        }
    }

    public void deleteMobile(int vnum) {
        for( Mobile mob : mobs) {
            if( mob.getVnum() == vnum) {
                mobs.remove(mob);
                for( Room room : rooms) {
                    room.removeMobile(mob);
                }
            }
        }
    }

    public void deleteRoom(int vnum) {
        MudExit exit;
        // clear from rooms.
        for( Room room : rooms) {
            if( room.getVnum() == vnum) {
                rooms.remove(room);
                for( Room room2 : rooms) {
                    for (int b = 0; b < MudExit.MAX_EXITS; b++) {
                        exit = room2.getExit(b);

                        if (exit != null && exit.getToVnum() == vnum)
                            room2.deleteExit(b);
                    }
                }
            }
        }
    }

    public void clear() {
        rooms.clear();
        objects.clear();
        mobs.clear();
        resets.clear();
        mobprogs.clear();
        noData();
    }

    public JList getResetList() {
        return new JList(resets.toArray(new MudReset[resets.size()]));
    }

    public JComboBox getVnumCombo(String type) {
        JComboBox temp;

        if (type.startsWith("room"))
            temp = new JComboBox((Vector<Room>)rooms);
        else if (type.startsWith("obj"))
            temp = new JComboBox((Vector<MudObject>)objects);
        else if (type.startsWith("mob"))
            temp = new JComboBox((Vector<Mobile>)mobs);
        else if (type.startsWith("reset"))
            temp = new JComboBox((Vector<MudReset>)resets);
        else if (type.startsWith("mprog"))
            temp = new JComboBox((Vector<MobileProgram>)mobprogs);
        else
            return null;

        return temp;
    }

    /*
    * This function takes the ROM style string resets
    * and parses them into better usable data.
    * This includes turning 'e' resets into equipment
    * flags on that mobiles vnum.
    * 11/28/2003, added code to filter bad vnums
    */
    public void transformResets() {
        String command;
        Mobile mob = null;

        if (getResetCount() == 0)
            return;

        try {
        for( MudReset temp : resets) {
                command = temp.getCommand();
                switch (command.charAt(0)) {
                    case 'm':
                    case 'M': {
                        int t = temp.getArg(1);
                        mob = getMobile(t);
                        int rNum = temp.getArg(3);
                        Room myRoom = getRoom(rNum);

                        if (mob == null || myRoom == null) {
                            //    if( mob == null )
                            //       EditorView.inform("Bad mobile reset, non-existant mobile: " + t);
                            //   else if( myRoom == null )
                            //      EditorView.inform("Bad room reset, non-existant room: " + rNum );
                            continue;
                        }

                        myRoom.addMobile(mob);
                        break;
                    }
                    case 'o':
                    case 'O': {
                        int t = temp.getArg(1);
                        MudObject myObj = getObject(t);
                        int rNum = temp.getArg(3);
                        Room myRoom = getRoom(rNum);

                        if (myObj == null || myRoom == null) {
                            //     if( myObj == null )
                            //       EditorView.inform("Bad object reset, non-existant object: " + t);
                            //    else if( myRoom == null )
                            //       EditorView.inform("Bad room reset, non-existant room: " + rNum );
                            continue;
                        }

                        myRoom.addObject(myObj);
                        break;
                    }
                    case 'e':
                    case 'E': {
                        // equip the mobile
                        if (mob == null) {
                            //  System.out.println("equip, with no mob.");
                            continue;
                        }
                        int t = temp.getArg(1);
                        int pos = temp.getArg(3);

                        if (t < getLowVnum() || t > getHighVnum()) {
                            //   EditorView.inform("Bad equip reset, obj: " + t);
                            continue;
                        }
                        mob.equipMobile(pos, t);
                        break;
                        // arg1 = vnum
                        // arg3 = position
                    }
                    case 'g':
                    case 'G': {
                        int t = temp.getArg(1);
                        MudObject myObj = getObject(t);

                        if (mob == null || myObj == null) {
                            //     if( mob == null )
                            //        EditorView.inform("give, with no mob.");
                            //     else if( myObj == null )
                            //        EditorView.inform("Bad give reset, non-existant obj: " + t );
                            continue;
                        }

                        mob.giveToMobile(myObj);
                        break;
                    }
                    case 'd':
                    case 'D': {
                        Room rTemp = getRoom(temp.getArg(1));

                        if (rTemp == null) {
                            //    System.out.println("Dumping non-existant door reset.(room)");
                            continue;
                        }
                        int dir = temp.getArg(2);
                        int lockType = temp.getArg(3);
                        MudExit tempExit = rTemp.getExit(dir);
                        if (tempExit == null) {
                            //      System.out.println("Dumping non-existant door reset.(exit)");
                            continue;
                        }
                        tempExit.setFlag(MudConstants.EXIT_ISDOOR);
                        tempExit.setFlag(MudConstants.EXIT_CLOSED);
                        if (lockType == 2)
                            tempExit.setFlag(MudConstants.EXIT_LOCKED);

                        Room rReverse;
                        rReverse = getRoom(tempExit.getToVnum());
                        if (rReverse == null)
                            continue;
                        int dir2 = MudExit.getReverseExit(dir);
                        MudExit tempExit2 = rReverse.getExit(dir2);
                        if (tempExit2 == null)
                            continue;
                        tempExit2.setFlag(MudConstants.EXIT_ISDOOR);
                        tempExit2.setFlag(MudConstants.EXIT_CLOSED);
                        if (lockType == 2)
                            tempExit2.setFlag(MudConstants.EXIT_LOCKED);

                        break;
                    }
                    case 'p':
                    case 'P': {
                        int oTemp = temp.getArg(1);
                        int oTemp2 = temp.getArg(3);
                        MudObject holder = getObject(oTemp2);
                        MudObject placer = getObject(oTemp);

                        if (holder == null || placer == null)
                            continue;

                        holder.addObject(placer);
                        break;
                    }
                    default: {
                        System.out.println("Nothing to do with reset:");
                        System.out.println(temp.toString());
                        break;
                    }
                }
            }
            // System.out.println(temp);
        }
        catch (Exception ext) {
            System.out.println("Exception transforming resets.");
        }
        resets.clear();
    }
}
// End Of File