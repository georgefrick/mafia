package net.s5games.mafia.model;

import net.s5games.mafia.model.MobileProgram;

import javax.swing.*;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: tenchi
 * Date: Jul 5, 2008
 * Time: 4:08:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Area {
    // Accessors
    String getAreaName();

    String getBuilder();

    String getFileName();

    String getPathName();

    int getSecurity();

    int getLowVnum();

    int getHighVnum();

    int getRoomCount();

    int getMobCount();

    int getMprogCount();

    int getResetCount();

    int getObjectCount();

    int getFlags();

    boolean valid();

    boolean changed();

    Collection<MudReset> getResets();

    boolean isValidVnum(int vnum);

    int getFirstMprogVnum();

    int getFirstRoomVnum();

    int getFirstMobVnum();

    int getFirstObjectVnum();

    int maxMob(int vnum);

    Room getRoom(int vnum);

    MudObject getObject(int vnum);

    Mobile getMobile(int vnum);

    MobileProgram getMProgByVnum(int vnum);

    Collection<MobileProgram> getMobprogs(); 

    void setMobprogs(Collection<MobileProgram> mobprogs);

    void removeMobileProgram(MobileProgram dMProg);

    int getFreeMprogVnum();

    int getFreeRoomVnum();

    int getFreeMobileVnum();

    int getFreeObjectVnum();

    String getRatio();// Modifiers

    void setBuilder(String newbuilder);

    void setAreaName(String newname);

    void setFileName(String newname);

    void setPathName(String newpath);

    void setVnumRange(int newLowVnum, int newHighVnum);

    void setSecurity(int newSecurity);

    void setFlags(int newFlags);

    void insert(Object m);

    void reVnum(int newVnum);

    void deleteMprog(int vnum);

    void deleteObject(int vnum);

    void deleteMobile(int vnum);

    void deleteRoom(int vnum);

    void clear();

    JList getResetList();

    JComboBox getVnumCombo(String type);

    void transformResets();
}
