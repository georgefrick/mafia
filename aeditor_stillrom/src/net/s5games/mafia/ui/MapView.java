// George Frick
// MapView.java
// Area Editor Project, Spring 2002
//
// This class in an interactive map for editing an model. It attempts to
// represent an model visually and allow for a builder to edit an model
// by interacting with a graphical map.


package net.s5games.mafia.ui;

import net.s5games.mafia.model.MudConstants;
import net.s5games.mafia.ui.EditorView;
import net.s5games.mafia.ui.RoomView;
import net.s5games.mafia.model.Area;
import net.s5games.mafia.model.Room;
import net.s5games.mafia.model.MudExit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Vector;

public class MapView extends JPanel {
    /*
    * Data
    */
    private Area data;
    private Room start;
    protected Room temp;
    protected Room mouseRoom;
    protected Vector visited;
    protected EditorView myParent;
    protected Image up1, up2, down1, down2, mImage1, oImage1, mImage2, oImage2;

    /*
    * Zooming
    */
    protected int zoom;
    public static final int ZOOM_1X = 1;
    public final static int ZOOM_2X = 2;
    public final static int ZOOM_3X = 3;
    protected int rSize = 15, eSize = 7;  //NOTE: rSize must be ODD, esize too.
    protected int goSize = rSize + eSize;
    protected int longSize = goSize * 2;
    protected int shortSize = goSize;
    protected int maxX = 1000 - rSize - eSize;
    protected int maxY = 1000 - rSize - eSize;

    protected int sizeA = 15, linkA = 7;
    protected int sizeB = 9, linkB = 3;

    /*
    * Reference
    */
    protected int winWidth, winHeight;
    protected Color fadedCyan = new Color(204, 204, 204);
    protected Enumeration loop;

    /*
    * Customization
    * Setters/Accessors are at bottom of file.
    */
    protected Color currentRoomColor = fadedCyan;
    protected Color currentRoomBorder = Color.red;
    protected Color roomColor = Color.white;
    protected Color roomBorder = Color.black;
    protected Color exitColor = Color.blue;
    protected Color bgColor = Color.white;

    /*
    * Pop menu
    */
    JPopupMenu popup;
    JMenuItem m1;
    JMenuItem m2;
    JMenuItem mName;
    JMenu digMenu;
    JMenuItem digEast;
    JMenuItem digNorth;
    JMenuItem digSouth;
    JMenuItem digWest;

    /*
    * Create the map.
    * size default is a 1000 by 1000 pixel panel
    * zoom default is 2x.
    */
    public MapView(Area a, EditorView p) {
        super();
        data = a;
        myParent = p;
        visited = new Vector();
        // this.setSize(700,700);
        winWidth = 1000;
        winHeight = 1000;
        setPreferredSize(new Dimension(winWidth, winHeight));
        zoom = ZOOM_2X;

        popup = new JPopupMenu();
        mName = new JMenuItem("RoomName");
        mName.setEnabled(false);
        m1 = new JMenuItem("Jump-To");
        m1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mouseRoom != null)
                    myParent.update(mouseRoom.getVnum());
            }
        });
        m2 = new JMenuItem("Delete");
        m2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mouseRoom != null) {
                    RoomView mView = (RoomView) myParent;
                    mView.update(mouseRoom.getVnum());
                    mView.deleteRoom();
                }
            }
        });
        popup.add(mName);
        popup.addSeparator();
        popup.add(m1);
        popup.add(m2);
        digMenu = new JMenu("Dig New Room");
        digNorth = new JMenuItem("North");
        digNorth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mouseRoom != null) {
                    RoomView mView = (RoomView) myParent;
                    mView.update(mouseRoom.getVnum());
                    mView.digRoom(0);
                }
            }
        });
        digSouth = new JMenuItem("South");
        digSouth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mouseRoom != null) {
                    RoomView mView = (RoomView) myParent;
                    mView.update(mouseRoom.getVnum());
                    mView.digRoom(2);
                }
            }
        });
        digEast = new JMenuItem("East");
        digEast.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mouseRoom != null) {
                    RoomView mView = (RoomView) myParent;
                    mView.update(mouseRoom.getVnum());
                    mView.digRoom(1);
                }
            }
        });
        digWest = new JMenuItem("West");
        digWest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mouseRoom != null) {
                    RoomView mView = (RoomView) myParent;
                    mView.update(mouseRoom.getVnum());
                    mView.digRoom(3);
                }
            }
        });
        digMenu.add(digNorth);
        digMenu.add(digSouth);
        digMenu.add(digEast);
        digMenu.add(digWest);
        popup.add(digMenu);
        MouseListener popupListener = new PopupListener();
        this.addMouseListener(popupListener);
        this.addKeyListener(new MapKeyListener());
        up1 = getToolkit().getImage(getClass().getResource("up6.gif"));
        up2 = getToolkit().getImage(getClass().getResource("up12.gif"));
        down1 = getToolkit().getImage(getClass().getResource("down6.gif"));
        down2 = getToolkit().getImage(getClass().getResource("down12.gif"));
        mImage1 = getToolkit().getImage(getClass().getResource("mob6.gif"));
        mImage2 = getToolkit().getImage(getClass().getResource("mob12.gif"));
        oImage1 = getToolkit().getImage(getClass().getResource("obj6.gif"));
        oImage2 = getToolkit().getImage(getClass().getResource("obj12.gif"));

    }

    public int getShortSize() {
        return shortSize;
    }

    public void update(int vnum, int newZoom) {
        zoom = newZoom;
        start = data.getRoom(vnum);
        update();
    }

    public void update(int vnum) {
        start = data.getRoom(vnum);
        update();
    }

    public void update() {
        if (zoom == ZOOM_1X) {
            rSize = sizeB;
            eSize = linkB;
        } else if (zoom == ZOOM_3X) {
            rSize = sizeA * 2;
            eSize = linkA * 2;
        } else {
            rSize = sizeA;
            eSize = linkA;
        }

        goSize = rSize + eSize;
        longSize = goSize * 2;
        shortSize = goSize;

        if (data == null)
            return;

        if (start == null)
            start = data.getRoom(data.getFirstRoomVnum());
    }

    private void recurse2(Room t, int x, int y, Graphics g) {
        if (t == null || x < 0 || y < 0 || x >= maxX || y >= maxY)
            return;

        boolean sNorth = false, lNorth = false;

        Room north = t.getNorth();
        if (north != null && !visited.contains(north)) {
            if (!isAt(x, y - longSize) && !isAt(x, y - shortSize)) {
                drawRoomAt(north, x, y - longSize, g);
                visited.add(north);
                north.setXY(x, y - longSize);
                lNorth = true;
            } else if (!isAt(x, y - shortSize)) {
                drawRoomAt(north, x, y - shortSize, g);
                visited.add(north);
                north.setXY(x, y - shortSize);
                sNorth = true;
            } else
                t.getNorth().setXY(-1, -1);
        }

        boolean sEast = false, lEast = false;
        Room east = t.getEast();
        if (east != null && !visited.contains(east)) {
            if (!isAt(x + longSize, y) && !isAt(x + shortSize, y)) {
                drawRoomAt(east, x + longSize, y, g);
                visited.add(east);
                east.setXY(x + longSize, y);
                lEast = true;
            } else if (!isAt(x + shortSize, y)) {
                drawRoomAt(east, x + shortSize, y, g);
                visited.add(east);
                east.setXY(x + shortSize, y);
                sEast = true;
            } else
                t.getEast().setXY(-1, -1);
        }

        boolean sSouth = false, lSouth = false;
        Room south = t.getSouth();
        if (south != null && !visited.contains(south)) {
            if (!isAt(x, y + longSize) && !isAt(x, y + shortSize)) {
                drawRoomAt(south, x, y + longSize, g);
                visited.add(south);
                south.setXY(x, y + longSize);
                lSouth = true;
            } else if (!isAt(x, y + shortSize)) {
                drawRoomAt(south, x, y + shortSize, g);
                visited.add(south);
                south.setXY(x, y + shortSize);
                sSouth = true;
            } else
                t.getSouth().setXY(-1, -1);
        }

        boolean sWest = false, lWest = false;
        Room west = t.getWest();
        if (west != null && !visited.contains(west)) {
            if (!isAt(x - longSize, y) && !isAt(x - shortSize, y)) {
                drawRoomAt(west, x - longSize, y, g);
                visited.add(west);
                west.setXY(x - longSize, y);
                lWest = true;
            } else if (!isAt(x - shortSize, y)) {
                drawRoomAt(west, x - shortSize, y, g);
                visited.add(west);
                west.setXY(x - shortSize, y);
                sWest = true;
            } else
                t.getWest().setXY(-1, -1);
        }

        if (lNorth)
            recurse2(north, x, y - longSize, g);
        else if (sNorth)
            recurse2(north, x, y - shortSize, g);

        if (lEast)
            recurse2(east, x + longSize, y, g);
        else if (sEast)
            recurse2(east, x + shortSize, y, g);

        if (lSouth)
            recurse2(south, x, y + longSize, g);
        else if (sSouth)
            recurse2(south, x, y + shortSize, g);

        if (lWest)
            recurse2(west, x - longSize, y, g);
        else if (sWest)
            recurse2(west, x - shortSize, y, g);
    }

    private boolean isAt(int x, int y) {
        loop = visited.elements();
        while (loop.hasMoreElements()) {
            temp = (Room) loop.nextElement();
            if (temp.getX() == x && temp.getY() == y)
                return true;
        }
        return false;
    }

    /* old recurse function */
    private void recurse(Room t, int x, int y, Graphics g) {
        if (t == null || x < 0 || y < 0 || x >= maxX || y >= maxY)
            return;

        // FINALLY stop the double visits!
        if (visited.contains(t))
            return;

        loop = visited.elements();

        while (loop.hasMoreElements()) {
            temp = (Room) loop.nextElement();
            if (temp.getX() == x && temp.getY() == y)
                return;
        }

        drawRoomAt(t, x, y, g);
        t.setXY(x, y);
        visited.add(t);

        if (t.getNorth() != null && y > 0)
            recurse(t.getNorth(), x, y - goSize, g);
        if (t.getSouth() != null && y < maxY)
            recurse(t.getSouth(), x, y + goSize, g);
        if (t.getWest() != null && x > 0)
            recurse(t.getWest(), x - goSize, y, g);
        if (t.getEast() != null && x < maxX)
            recurse(t.getEast(), x + goSize, y, g);
    }

    public void paintComponent(Graphics g) {
        /*
        * clear the window so we can start drawing
        */
        g.setColor(Color.white);
        g.fillRect(0, 0, winWidth, winHeight);

        /*
        * setup mapping by starting at the start room, which is the room currently
        * being viewed. We have to return if there are no rooms in the model or
        * we're looking at a bad room for some reason.
        * ..assign the graphics pallete so we don't have to pass it through
        * recurssion.
        */
        if (data == null)
            return;

        if (start == null) {
            if (data.getFirstRoomVnum() == -1)
                return;

            start = data.getRoom(data.getFirstRoomVnum());
        }

        int currX = maxX / 2;
        int currY = maxY / 2;

        /*
        * Enter recurssion, with the middle pixel of the panel and the data
        * pointed at the starting room
        */
        visited.clear();
        visited.add(start);
        start.setXY(currX, currY);
        drawRoomAt(start, currX, currY, g);
        recurse2(start, currX, currY, g);

        loop = visited.elements();

        while (loop.hasMoreElements()) {
            temp = (Room) loop.nextElement();
            Room a = temp.getNorth();
            if (a != null && a.getX() != -1 && a.getY() != -1 && visited.contains(a)) {
                // g.setColor(Color.green);
                g.setColor(getExitColor(temp, 0));
                g.drawLine(temp.getX(), temp.getY() - (rSize / 2), a.getX(), a.getY() + (rSize / 2));
                //System.out.println("hello?-" + temp.getX() + "-" + temp.getY()
                //    + "-" + a.getX() + "-" + a.getY()  );
            }
            Room b = temp.getSouth();
            if (b != null && b.getX() != -1 && b.getY() != -1 && visited.contains(b)) {
                g.setColor(getExitColor(temp, 2));
                g.drawLine(temp.getX(), temp.getY() + (rSize / 2), b.getX(), b.getY() - (rSize / 2));
                //System.out.println("hello?-" + temp.getX() + "-" + temp.getY()
                //    + "-" + a.getX() + "-" + a.getY()  );
            }
            Room c = temp.getWest();
            if (c != null && c.getX() != -1 && c.getY() != -1 && visited.contains(c)) {
                g.setColor(getExitColor(temp, 3));
                g.drawLine(temp.getX() - (rSize / 2), temp.getY(), c.getX() + (rSize / 2), c.getY());
                //System.out.println("hello?-" + temp.getX() + "-" + temp.getY()
                //    + "-" + a.getX() + "-" + a.getY()  );
            }
            Room d = temp.getEast();
            if (d != null && d.getX() != -1 && d.getY() != -1 && visited.contains(d)) {
                g.setColor(getExitColor(temp, 1));
                g.drawLine(temp.getX() + (rSize / 2), temp.getY(), d.getX() - (rSize / 2), d.getY());
                //System.out.println("hello?-" + temp.getX() + "-" + temp.getY()
                //    + "-" + a.getX() + "-" + a.getY()  );
            }
            //temp.setXY(-1,-1);
        }

    }

    private Color getExitColor(Room t, int dir) {
        MudExit temp = t.getExit(dir);
        if (temp.isSet(MudConstants.EXIT_WALL))
            return Color.black;
        else if (temp.isSet(MudConstants.EXIT_LOCKED))
            return Color.red;
        else if (temp.isSet(MudConstants.EXIT_ISDOOR))
            return Color.blue;
        else
            return Color.green;
    }

    /*
    * give the CENTER of a room and the room information, will draw that room.
    */
    private void drawRoomAt(Room r, int x, int y, Graphics g) {
        int myX, myY;

        // Get starting point for room.
        myX = x - (rSize / 2);
        myY = y - (rSize / 2);

        if (r == start) {
            g.setColor(currentRoomColor);
            g.fillRect(myX, myY, rSize + 1, rSize + 1);
        }

        g.setColor(Color.black);
        g.drawRect(myX, myY, rSize, rSize);

        if (zoom == ZOOM_3X) {
            if (r.getUp() != null)
                g.drawImage(up2, myX + rSize - 12, myY + 1, null);
            if (r.getDown() != null)
                g.drawImage(down2, myX + 1, myY + rSize - 12, null);
            if (r.getMobiles().size() != 0)
                g.drawImage(mImage2, myX + 1, myY + 1, null);
            if (r.getObjects().size() != 0)
                g.drawImage(oImage2, myX + rSize - 12, myY + rSize - 12, null);
        } else if (zoom == ZOOM_2X) {
            if (r.getUp() != null)
                g.drawImage(up1, myX + rSize - 6, myY + 1, null);
            if (r.getDown() != null)
                g.drawImage(down1, myX + 1, myY + rSize - 6, null);
            if (r.getMobiles().size() != 0)
                g.drawImage(mImage1, myX + 1, myY + 1, null);
            if (r.getObjects().size() != 0)
                g.drawImage(oImage1, myX + rSize - 6, myY + rSize - 6, null);
        } else {
            if (r.getMobiles().size() != 0)
                g.fillOval(myX + 2, myY + 5, 3, 3);
            if (r.getObjects().size() != 0)
                g.fillOval(myX + 4, myY + 5, 3, 3);
        }
        // draw options.
    }

    class PopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            requestFocus();
            if (e.isPopupTrigger()) {
                loop = visited.elements();

                while (loop.hasMoreElements()) {
                    temp = (Room) loop.nextElement();
                    // System.out.println("Room at: " + temp.getX() + " / " + temp.getY() + ".");
                    if ((e.getX() >= temp.getX() - (rSize / 2))
                            && (e.getX() <= temp.getX() + (rSize / 2))
                            && (e.getY() >= temp.getY() - (rSize / 2))
                            && (e.getY() <= temp.getY() + (rSize / 2))) {
                        if (temp.getNorth() != null)
                            digNorth.setEnabled(false);
                        else
                            digNorth.setEnabled(true);

                        if (temp.getSouth() != null)
                            digSouth.setEnabled(false);
                        else
                            digSouth.setEnabled(true);

                        if (temp.getEast() != null)
                            digEast.setEnabled(false);
                        else
                            digEast.setEnabled(true);

                        if (temp.getWest() != null)
                            digWest.setEnabled(false);
                        else
                            digWest.setEnabled(true);

                        mName.setText(temp.toString());
                        mouseRoom = temp;
                        popup.show(e.getComponent(), e.getX(), e.getY());
                        break;
                    }
                }
            }
        }
    }

    /*
    * Start of customization functions
    */
    public Color getCurrentRoomColor() {
        return currentRoomColor;
    }

    public Color getCurrentRoomBorder() {
        return currentRoomBorder;
    }

    public Color getRoomColor() {
        return roomColor;
    }

    public Color getRoomBorder() {
        return roomBorder;
    }

    public Color getExitColor() {
        return exitColor;
    }

    public Color getBackgroundColor() {
        return bgColor;
    }

    public void setCurrentRoomColor(Color nColor) {
        currentRoomColor = nColor;
    }

    public void setCurrentRoomBorder(Color nColor) {
        currentRoomBorder = nColor;
    }

    public void setRoomColor(Color nColor) {
        roomColor = nColor;
    }

    public void setRoomBorder(Color nColor) {
        roomBorder = nColor;
    }

    public void setExitColor(Color nColor) {
        exitColor = nColor;
    }

    public void setBackgroundColor(Color nColor) {
        bgColor = nColor;
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

        }

        public void keyHandle(KeyEvent e) {
            int keyCode = e.getKeyCode();
            RoomView m = (RoomView) myParent;
            switch (keyCode) {
                case KeyEvent.VK_KP_DOWN:
                case KeyEvent.VK_DOWN: {
                    m.moveView(2);
                    return;
                }
                case KeyEvent.VK_KP_UP:
                case KeyEvent.VK_UP: {
                    m.moveView(0);
                    return;
                }
                case KeyEvent.VK_KP_LEFT:
                case KeyEvent.VK_LEFT: {
                    m.moveView(3);
                    return;
                }
                case KeyEvent.VK_KP_RIGHT:
                case KeyEvent.VK_RIGHT: {
                    m.moveView(1);
                    return;
                }
                default:
                    return;
            }
        }
    }
}