package model.Dungeon;

import model.Room.*;

import java.util.Random;

public class NeverEndingDungeonGenerator implements DungeonGenerator {

    private Random random = new Random();
    private Room[][] dungeon;
    private Integer[] startingPosition;
    private int cellSize;

    @Override
    public Room[][] generate(int cellSize) {
        dungeon = new Room[cellSize][cellSize];
        this.cellSize = cellSize;
        for(int width = 0; width<cellSize; width++) {
            for(int height = 0; height<cellSize; height++) {
                if(dungeon[width][height] != null) continue;
                if(width == 0 || height == 0 || width == cellSize-1 || height == cellSize-1) {
                    dungeon[width][height] = new Wall();
                } else {
                    boolean[] doors = {isWall(9), isWall(9),isWall(9), isWall(9)};
                    dungeon[width][height] = new Chamber();
                    addWalls(dungeon, width, height, doors);
                }
            }
        }
        setStartingPosition();
        return dungeon;
    }

    @Override
    public Integer[] getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition() {
        Integer[] position = new Integer[2];
        int x; int y;
        do {
            x = random.nextInt(cellSize); y = random.nextInt(cellSize);
        } while(dungeon[x][y] != null && (dungeon[x][y] instanceof Wall));
        startingPosition[0] = x; startingPosition[1] = y;
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
