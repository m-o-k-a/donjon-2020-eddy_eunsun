package model.Dungeon;

import model.Room.Room;

import java.util.Random;

public interface DungeonGenerator {
    Room[][] generate();
    Integer[] getStartingPosition();
    void setStartingPosition();
    int getCellSize();
}
