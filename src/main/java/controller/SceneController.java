package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.DungeonGenerator;
import model.Player;
import model.Room.Chamber;
import model.Room.Room;
import model.Room.Wall;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class SceneController implements Initializable {

    private enum Action {
        UP, DOWN, LEFT, RIGHT, INTERACT, ATTACK, ITEM, MAGIC
    }

    DungeonGenerator dungeonGenerator = new DungeonGenerator();
    Room[][] dungeon;
    Player player;
    Rectangle playerLife;
    int cellSize;
    @FXML GridPane dungeonGrid;
    @FXML TextFlow textLogsContent;
    @FXML Pane lifebar;
    @FXML Canvas minimap;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //todo initialize dungeon
        player = new Player(5, 5, 100);

        //todo refractor battle controller
        playerLife = new Rectangle(lifebar.getPrefWidth(), lifebar.getPrefHeight(), Color.LIME);
        lifebar.getChildren().add(playerLife);
        cellSize = 20;
        drawMiniMap(cellSize);

    }

    private void drawMiniMap(int roomSize) {
        dungeon = dungeonGenerator.generate(roomSize);
        Color color = Color.BLACK;
        int cellSize = (int) (minimap.getWidth()/roomSize);
        for(int width = 0; width<roomSize; width++) {
            for(int height = 0; height<roomSize; height++) {
                if(dungeon[width][height] instanceof Wall) { color = Color.DARKGRAY; }
                else {
                    if(dungeon[width][height] instanceof Chamber) {
                        if(player.x == width && player.y == height) { color = Color.LIME; }
                        else { color = Color.BLACK; }
                    }
                }
                drawMiniMapCell(color, cellSize, width, height);
            }
        }
    }

    private void drawMiniMapCell(Color color, int cellSize, int width, int height) {
        minimap.getGraphicsContext2D().setFill(color);
        minimap.getGraphicsContext2D().fillRect(width*cellSize, height*cellSize, cellSize-1, cellSize-1);
    }

    //todo refractor battle controller
    private void drawLifeBar() {
        playerLife.setWidth((lifebar.getPrefWidth()*player.getLifePoint())/100);
    }

    /**
     * JAVAFX BUTTONS ACTIONS
     */
    @FXML private void goUp() { actionMoveTo(Action.UP); }
    @FXML private void goDown() { actionMoveTo(Action.DOWN); }
    @FXML private void goLeft() { actionMoveTo(Action.LEFT); }
    @FXML private void goRight() { actionMoveTo(Action.RIGHT); }
    @FXML private void interact() { actionInteract(); }
    @FXML private void attack() { actionAttack(); } //todo implements
    @FXML private void item() { } //todo implements
    @FXML private void magic() { } //todo implements

    /**
     * mehtod that will add an action log for movement
     * @param action the enum value of the movement
     */
    private void actionMoveTo(Action action) {
        Text actionLog;
        try {
            Integer[] lastPosition = new Integer[2]; lastPosition[0] = player.x; lastPosition[1] = player.y;
            switch (action) {
                case UP:  player.goUp(); break;
                case DOWN: player.goDown(); break;
                case LEFT: player.goLeft(); break;
                case RIGHT: player.goRight(); break;
                default: return;
            }
            if (dungeon[player.x][player.y] instanceof Wall) {
                player.x = lastPosition[0]; player.y = lastPosition[1];
                throw new Exception();
            }
            drawMiniMapCell(Color.LIGHTGRAY, (int)(minimap.getWidth()/cellSize), lastPosition[0], lastPosition[1]);
            drawMiniMapCell(Color.LIME, (int)(minimap.getWidth()/cellSize), player.x, player.y);
            actionLog = new Text("<Moved to ("+player.x+", "+player.y+")>\n"); actionLog.setStyle("-fx-font-style: italic;"); actionLog.setFill(Color.WHITE);
        } catch (Exception e) {
            actionLog = new Text("You cannot pass a wall.\n"); actionLog.setStyle("-fx-font-style: italic;"); actionLog.setFill(Color.WHITE);
        }
        addLogs(actionLog);
    }

    /**
     * method that will add an action log dedicated to interaction
     */
    private void actionInteract() {
        Text actionLog = new Text("<You are alone... Forever alone...>\n"); actionLog.setFill(Color.WHITE);
        addLogs(actionLog);
    }

    /**
     * method that will add an action log dedicated to attack
     */
    private void actionAttack() {
        Text actionLog = new Text("You Attacked nothing\n"); actionLog.setFill(Color.RED);
        addLogs(actionLog);
    }

    /**
     * method that will add a log to the textFlow textLogsContent of the interface
     * @param log
     */
    private void addLogs(Text log) {
        textLogsContent.getChildren().add(0, log);
    }
}

/*
--fat method if we want to apply keyboard
    private void action(Action action) {
        if(false) {
            //Todo if cannot move
        }
        else {
            switch (action) {
                case UP: player.goUp(); actionMoveTo(); break;
                case DOWN: player.goDown(); actionMoveTo(); break;
                case LEFT: player.goLeft(); actionMoveTo(); break;
                case RIGHT: player.goRight(); actionMoveTo(); break;
                case INTERACT: actionInteract(); break; //todo implements interactions
                case ATTACK: actionAttack(); break; //todo implements attack
                case ITEM: break; //todo implements item
                case MAGIC: break; //todo implements magic
                default: return;
            }
        }
    }
 */