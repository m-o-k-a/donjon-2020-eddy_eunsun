package model.Dungeon;

import model.Room.Room;

public class Dungeon {

    private Room[][] dungeon;
    private boolean isExited;

    public Dungeon(Room[][] dungeon) {
        this.dungeon = dungeon;
        isExited = false;
    }

    public Room getRoom(int x, int y) {
        return dungeon[x][y];
    }

    public void setIsExited(boolean value) { isExited = value;}
    public boolean isExited() { return isExited; }
}

