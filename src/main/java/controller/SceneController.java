package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.DataBase.ActionDataBase;
import model.DataBase.MonsterDataBase;
import model.Difficulty.DifficultyStrategy;
import model.Difficulty.SimpleDifficultyEnhance;
import model.Dungeon.Dungeon;
import model.Dungeon.DungeonGenerator;
import model.Entity.Chest;
import model.Entity.EntityFactory;
import model.Entity.Monster;
import model.Entity.Player;
import model.Room.Chamber;
import model.Room.Room;
import model.Room.Wall;
import view.draw.MiniMap;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class SceneController implements Initializable {

    EntityFactory entityFactory = new EntityFactory();
    Random random = new Random();
    DungeonGenerator dungeonGenerator = new DungeonGenerator();
    DifficultyStrategy difficultyStrategy;
    Dungeon dungeon;
    Player player;
    Rectangle playerLife;
    int cellSize;

    @FXML GridPane dungeonGrid;
    @FXML TextFlow textLogsContent;
    @FXML Pane lifebar;
    @FXML Canvas minimap;
    @FXML ImageView spriteMonster;
    @FXML ImageView spriteChest;
    @FXML TextFlow weaponInfo;
    @FXML TextFlow itemInfo;
    @FXML TextFlow magicInfo;
    @FXML Pane scene;

    private MiniMap drawMiniMap;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //todo initialize dungeon
        cellSize = 40; //pls do not go above 50
        dungeon = new Dungeon(dungeonGenerator.generate(cellSize));
        difficultyStrategy = new SimpleDifficultyEnhance();

        //todo character
        Integer[] position = startingPosition(cellSize);
        player = entityFactory.createPlayer(position[0], position[1], 1000, 10, "Warrior");

        //todo refractor battle controller
        playerLife = new Rectangle(lifebar.getPrefWidth(), lifebar.getPrefHeight(), Color.LIME);
        lifebar.getChildren().add(playerLife);
        if(((Chamber) getPlayerRoom(player)).InitializeRoom(difficultyStrategy.getDifficulty())) { difficultyStrategy.doUpdateDifficulty(); }
        //initialize drawable
        drawMiniMap = new MiniMap(minimap, player, dungeon, cellSize);
        updateMiniMap();
    }

    private void updateMiniMap() {
        drawMiniMap.draw();
    }

    private Integer[] startingPosition(int cellSize) {
        Integer[] position = new Integer[2];
        int x; int y;
        do {
            x = random.nextInt(cellSize); y = random.nextInt(cellSize);
        } while(dungeon.getRoom(x, y) != null && (dungeon.getRoom(x, y) instanceof Wall));
        position[0] = x; position[1] = y;
        return position;
    }

    private Room getPlayerRoom(Player player) {
        return dungeon.getRoom(player.x, player.y);
    }

    //todo refractor battle controller

    //TODO REGLER LE SOUCIS DES MURS

    private void drawLifeBar() {
        playerLife.setWidth((lifebar.getPrefWidth()*player.getHealth())/player.getMaxHealth());
    }

    /**
     * JAVAFX BUTTONS ACTIONS
     */
    @FXML private void goUp() { actionMoveTo(ActionDataBase.Action.UP); }
    @FXML private void goDown() { actionMoveTo(ActionDataBase.Action.DOWN); }
    @FXML private void goLeft() { actionMoveTo(ActionDataBase.Action.LEFT); }
    @FXML private void goRight() { actionMoveTo(ActionDataBase.Action.RIGHT); }
    @FXML private void interact() { actionInteract(); }

    @FXML private void attack() { actionBattle(ActionDataBase.Action.ATTACK); }
    @FXML private void item() { actionBattle(ActionDataBase.Action.ITEM); }
    @FXML private void magic() { actionBattle(ActionDataBase.Action.MAGIC); }

    /**
     * mehtod that will add an action log for movement
     * @param action the enum value of the movement
     */
    private void actionMoveTo(ActionDataBase.Action action) {
        Text actionLog;
        Chamber actualChamber = ((Chamber) getPlayerRoom(player));
        if(actualChamber.isBattle()) {
            actionBattle(action);
            return;
        }
        try {
            Integer[] lastPosition = new Integer[2]; lastPosition[0] = player.x; lastPosition[1] = player.y;
            switch (action) {
                case UP:  player.goUp(); break;
                case DOWN: player.goDown(); break;
                case LEFT: player.goLeft(); break;
                case RIGHT: player.goRight(); break;
                default: return;
            }
            if (getPlayerRoom(player) instanceof Wall) {
                player.x = lastPosition[0]; player.y = lastPosition[1];
                throw new Exception();
            }
            if(((Chamber) getPlayerRoom(player)).InitializeRoom(difficultyStrategy.getDifficulty())) { difficultyStrategy.doUpdateDifficulty(); }
            updateMiniMap();
            actionLog = new Text("<Moved to ("+player.x+", "+player.y+")>\n"); actionLog.setStyle("-fx-font-style: italic;");
        } catch (Exception e) {
            actionLog = new Text("You cannot pass a wall.\n"); actionLog.setStyle("-fx-font-style: italic;");
        }
        addLogs(Color.WHITE, actionLog);
        containsMonster(player.x, player.y);
        containsChest(player.x, player.y);
        initBattle(getPlayerRoom(player));
    }

    private void initBattle(Room playerRoom) {
        if ((playerRoom instanceof Chamber)) {
            Chamber playerChamber = (Chamber) playerRoom;
            if(!playerChamber.isBattle()) {
                playerChamber.initializeBattle(player);
            }
        }
    }

    /**
     * method that will add an action log for battle
     * @param action the enum value of the movement
     */
    private void actionBattle(ActionDataBase.Action action) {
        if ((getPlayerRoom(player) instanceof Chamber)) {
            Chamber playerChamber = (Chamber) getPlayerRoom(player);
            if(playerChamber.isBattle()) {
                Text[] logs = playerChamber.battleTurn(action);
                addLogs(Color.RED, logs);
                drawLifeBar();
                if(playerChamber.monster.isDead()) {
                    spriteMonster.setImage(null);
                }
            } else {
                addLogs(Color.WHITE, new Text("You tried to damages the void, without success...\n"));
            }
            updateInventoryInfo();
        }
    }

    private void containsChest(int x, int y) {
        Chamber chamber = (Chamber) dungeon.getRoom(x, y);
        if(chamber.chest != null) {
            drawChest(chamber.chest);
        } else { spriteChest.setImage(null); }
    }

    private void drawChest(Chest chest) {
        Image sprite = new Image(getClass().getResource("../view/ressources/chest_"+chest.isOpened()+".png").toExternalForm());
        spriteChest.setImage(sprite);
    }

    private void containsMonster(int x, int y) {
        Chamber chamber = (Chamber) dungeon.getRoom(x, y);
        if(chamber.monster != null) {
            if(!chamber.monster.isDead()) {
                addLogs(Color.RED, new Text("You face a "+chamber.monster.getName()+"\n"));
                drawMonster(chamber.monster);
            } else { spriteMonster.setImage(null); }
        } else { spriteMonster.setImage(null); }
    }

    private void drawMonster(Monster monster) {
        Image sprite = new Image(getClass().getResource(MonsterDataBase.getSprite(monster.type())).toExternalForm());
        spriteMonster.setImage(sprite);
        spriteMonster.layoutXProperty().bind(scene.widthProperty().subtract(sprite.getWidth()).divide(2));
        spriteMonster.layoutYProperty().bind(scene.heightProperty().subtract(sprite.getHeight()).divide(2));
    }

    /**
     * method that will add an action log dedicated to interaction
     */
    private void actionInteract() {
        Text actionLog = new Text("<You are not supposed to be there, and you know it>\n");;
        if(getPlayerRoom(player) instanceof Chamber) {
            Chamber room = (Chamber) getPlayerRoom(player);
            if(room.monster != null) {
                if (!room.monster.isDead()) {
                    addLogs(Color.WHITE, new Text("<You tried to communicate with the "+room.monster.getName()+" but all you can hear is incomprehensible noises>\n"));
                    return;
                }
            }
            if(room.chest != null) {  actionLog = room.chest.dropItem(player); drawChest(room.chest); updateInventoryInfo(); }
            else { actionLog = new Text("<This room is all dark and lonely>\n"); }
        }
        addLogs(Color.WHITE, actionLog);
    }

    private void updateInventoryInfo() {
        weaponInfo.getChildren().clear(); itemInfo.getChildren().clear(); magicInfo.getChildren().clear();
        if(player.getInventory().getWeapon() != null) {
            Text weapon = new Text(player.getInventory().getWeapon().toString()+"\nDamages: "+player.getInventory().getWeapon().getDamages());
            weapon.setFill(Color.WHITE);
            weaponInfo.getChildren().add(weapon);
        }
        if(player.getInventory().getUsableItem() != null) {
            Text item = new Text(player.getInventory().getUsableItem().toString()+"\nDamages: "+player.getInventory().getUsableItem().getDamages());
            item.setFill(Color.WHITE);
            itemInfo.getChildren().add(item);
        }
        if(player.getInventory().getMagic() != null) {
            Text magic = new Text(player.getInventory().getMagic().toString()+"\nDamages: "+player.getInventory().getMagic().getDamages());
            magic.setFill(Color.WHITE);
            magicInfo.getChildren().add(magic);
        }
    }

    /**
     * method that will add a log to the textFlow textLogsContent of the interface
     * @param logs
     */
    private void addLogs(Color color, Text... logs) {
        for(Text log : logs) {
            log.setFill(color);
            textLogsContent.getChildren().add(0, log);
        }
    }
}
