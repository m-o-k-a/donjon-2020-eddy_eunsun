package model.Dungeon;

import model.Room.Room;

public class Dungeon {

    Room[][] dungeon;

    public Dungeon(Room[][] dungeon) {
        this.dungeon = dungeon;
    }

    public Room getRoom(int x, int y) {
        return dungeon[x][y];
    }
}

