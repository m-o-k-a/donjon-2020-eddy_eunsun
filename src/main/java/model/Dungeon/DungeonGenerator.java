package model.Dungeon;

import model.Room.Chamber;
import model.Room.Room;
import model.Room.Wall;

import java.util.Random;

public class DungeonGenerator {

    private Random random = new Random();

    public Room[][] generate(int roomSize) {
        Room[][] dungeon = new Room[roomSize][roomSize];
        for(int width = 0; width<roomSize; width++) {
            for(int height = 0; height<roomSize; height++) {
                if(dungeon[width][height] != null) continue;
                if(width == 0 || height == 0 || width == roomSize-1 || height == roomSize-1) {
                    dungeon[width][height] = new Wall();
                } else {
                    boolean[] doors = {isWall(9), isWall(9),isWall(9), isWall(9)};
                    dungeon[width][height] = new Chamber(doors, false);
                    addWalls(dungeon, width, height, doors);
                }
            }
        }
        return dungeon;
    }

    private boolean isWall(int chance) {
        int value = random.nextInt(101);
        return value%chance != 0;
    }

    private void addWalls(Room[][] dungeon, int width, int height, boolean[] doors) {
        //LEFT UP RIGHT DOWN
        if(!doors[0]) { dungeon[width-1][height] = new Wall(); }
        if(!doors[1]) { dungeon[width][height-1] = new Wall(); }
        if(!doors[2]) { dungeon[width+1][height] = new Wall(); }
        if(!doors[3]) { dungeon[width][height+1] = new Wall(); }

    }
}
