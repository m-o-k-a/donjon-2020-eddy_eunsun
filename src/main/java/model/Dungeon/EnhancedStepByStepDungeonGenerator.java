package model.Dungeon;

import model.Room.Chamber;
import model.Room.ExitRoom;
import model.Room.Room;
import model.Room.Wall;

import java.util.Random;

public class EnhancedStepByStepDungeonGenerator implements DungeonGenerator {

    private Random random = new Random();
    private Room[][] dungeon;
    private Integer[] startingPosition;
    private int cellSize;

    public EnhancedStepByStepDungeonGenerator(int cellSize) {
        this.cellSize = cellSize;
    }

    @Override
    public Room[][] generate() {
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

    @Override
    public int getCellSize() { return cellSize; }

    private void stepUntil(int maxStep) {
        int x = startingPosition[0];
        int y = startingPosition[1];
        Chamber startingChamber = new Chamber(); startingChamber.InitializeRoom(1);
        startingChamber.monster = null; startingChamber.chest = null;
        dungeon[x][y] = startingChamber;
        int step = 0;
        do {
            int tempx; int tempy;
            int subStep = cellSize;
            do {
                tempx = x + (-1) + random.nextInt(3);
                tempy = y + (-1) + random.nextInt(3);
                subStep--;
            } while (subStep != 0 && dungeon[x][y] instanceof Room);
            x = tempx; y = tempy;
            dungeon[x][y] = new Chamber();
            step++;
        } while (step < maxStep && x < cellSize - 1 && x > 0 && y < cellSize - 1 && y > 0);
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