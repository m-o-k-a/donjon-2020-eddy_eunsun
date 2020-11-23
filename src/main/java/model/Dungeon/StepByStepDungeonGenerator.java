package model.Dungeon;

import model.Room.Chamber;
import model.Room.ExitRoom;
import model.Room.Room;
import model.Room.Wall;

import java.util.Random;

public class StepByStepDungeonGenerator implements DungeonGenerator {

    private Random random = new Random();
    private Room[][] dungeon;
    private Integer[] startingPosition;
    private int cellSize;

    @Override
    public Room[][] generate(int cellSize) {
        dungeon = new Room[cellSize][cellSize];
        this.cellSize = cellSize;
        setStartingPosition();
        stepUntil(cellSize*4);
        randomGenerator();
        return dungeon;
    }


    @Override
    public Integer[] getStartingPosition() {
        return startingPosition;
    }

    @Override
    public void setStartingPosition() {
        startingPosition = new Integer[2];
        startingPosition[0] = random.nextInt(cellSize - ((cellSize / 5) * 2)) + (cellSize / 5);
        startingPosition[1] = random.nextInt(cellSize - ((cellSize / 5) * 2)) + (cellSize / 5);
    }

    private void stepUntil(int maxStep) {
        int x = startingPosition[0];
        int y = startingPosition[1];
        int step = 0;
        do {
            System.out.println(x+" "+y);
            dungeon[x][y] = new Chamber();
            int tempx; int tempy;
            do {
                tempx = x + (-1) + random.nextInt(3);
                tempy = y + (-1) + random.nextInt(3);
            } while (tempx == startingPosition[0] && tempy == startingPosition[1]);
            x = tempx; y = tempy;
            step++;
        } while (step < maxStep && (x < cellSize - 1 || x > 0) && (y < cellSize - 1 || y > 0));
        dungeon[x][y] = new ExitRoom();
        System.out.println("STARTING: "+startingPosition[0]+" "+startingPosition[1]+" EXIT: "+x+" "+y);
    }

    private void randomGenerator() {
        for (int width = 0; width < cellSize; width++) {
            for (int height = 0; height < cellSize; height++) {
                if (dungeon[width][height] != null) continue;
                if (width == 0 || height == 0 || width == cellSize - 1 || height == cellSize - 1) {
                    dungeon[width][height] = new Wall();
                } else {
                    boolean[] doors = {isWall(9), isWall(9), isWall(9), isWall(9)};
                    dungeon[width][height] = new Chamber();
                    addWalls(dungeon, width, height, doors);
                }
            }
        }
    }

    private boolean isWall(int chance) {
        int value = random.nextInt(101);
        return value % chance != 0;
    }

    private void addWalls(Room[][] dungeon, int width, int height, boolean[] doors) {
        //LEFT UP RIGHT DOWN
        if (!doors[0] && !(dungeon[width-1][height] instanceof ExitRoom) && !(dungeon[width-1][height] instanceof Chamber)) {
            dungeon[width - 1][height] = new Wall();
        }
        if (!doors[1] && !(dungeon[width][height-1] instanceof ExitRoom)  && !(dungeon[width][height-1] instanceof Chamber)) {
            dungeon[width][height - 1] = new Wall();
        }
        if (!doors[2] && !(dungeon[width+1][height] instanceof ExitRoom)  && !(dungeon[width+1][height] instanceof Chamber)) {
            dungeon[width + 1][height] = new Wall();
        }
        if (!doors[3] && !(dungeon[width][height+1] instanceof ExitRoom)  && !(dungeon[width][height+1] instanceof Chamber)) {
            dungeon[width][height + 1] = new Wall();
        }

    }
}