package model.Dungeon;

import model.Room.Room;

import java.util.Random;

public interface DungeonGenerator {
    Room[][] generate(int cellSize);
    Integer[] getStartingPosition();
    void setStartingPosition();
}
