package model.Room;

public class Chamber implements  Room {
    private boolean[] openedDoors;
    private boolean isFinal;
    //entity list
    public boolean isVisited;

    public Chamber(boolean[] openedDoors, boolean isFinal) {
        isVisited = false;
        this.openedDoors = openedDoors;
        this.isFinal = isFinal;
    }
}
