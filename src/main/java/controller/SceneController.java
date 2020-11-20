package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.InnerShadow;
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
import org.w3c.dom.css.Rect;
import view.draw.*;

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
    private LifeBar drawLifeBar;
    private ChestDrawable drawChest;
    private MonsterDrawable drawMonster;
    private DungeonDrawable drawDungeon;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //todo initialize dungeon
        cellSize = 40; //pls do not go above 50
        dungeon = new Dungeon(dungeonGenerator.generate(cellSize));
        difficultyStrategy = new SimpleDifficultyEnhance();

        //todo character
        Integer[] position = startingPosition(cellSize);
        player = entityFactory.createPlayer(position[0], position[1], 50, 10, "Warrior");

        //todo refractor battle controller
        Rectangle playerLife = new Rectangle(lifebar.getPrefWidth(), lifebar.getPrefHeight(), Color.LIME);
        lifebar.getChildren().add(playerLife);
        if(((Chamber) getPlayerRoom(player)).InitializeRoom(difficultyStrategy.getDifficulty())) { difficultyStrategy.doUpdateDifficulty(); }
        //initialize drawable
        drawMiniMap = new MiniMap(minimap, player, dungeon, cellSize);
        drawLifeBar = new LifeBar(playerLife, player, lifebar.getPrefWidth());
        drawChest = new ChestDrawable(spriteChest);
        drawMonster = new MonsterDrawable(spriteMonster, scene);
        drawDungeon = new DungeonDrawable(scene);
        updateMiniMap();
        updateDungeon();
    }

    private void updateMiniMap() {
        drawMiniMap.draw();
    }
    private void updateLifeBar() { drawLifeBar.draw(); }
    private void updateDungeon() {
        if(getPlayerRoom(player) instanceof Chamber) {
            Chamber chamber = (Chamber) getPlayerRoom(player);
            String wallLeft = (dungeon.getRoom(player.x-1, player.y) instanceof Wall) ? "0" : "1";
            String wallTop = (dungeon.getRoom(player.x, player.y-1) instanceof Wall) ? "0" : "1";
            String wallRight = (dungeon.getRoom(player.x+1, player.y) instanceof Wall) ? "0" : "1";
            String values = wallLeft+wallTop+wallRight;
            System.out.println("VALUES: "+values);
            drawDungeon.setBackground("../ressources/room-"+values+".jpg");
            System.out.println("../ressources/room-"+values+".jpg");
            drawDungeon.draw();
        }
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
        if(player.isDead()) return;
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
            updateDungeon();
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
    //todo refractor
    private void actionBattle(ActionDataBase.Action action) {
        if(player.isDead()) return;
        boolean haveNothing = true;
        if ((getPlayerRoom(player) instanceof Chamber)) {
            Chamber playerChamber = (Chamber) getPlayerRoom(player);
            if(playerChamber.isBattle()) {
                Text[] logs = playerChamber.battleTurn(action);
                addLogs(Color.RED, logs);
                if(playerChamber.monster.isDead()) {
                    spriteMonster.setImage(null);
                }
                haveNothing = false;
            }
            else if(action == ActionDataBase.Action.ITEM && player.getInventory().getUsableItem() != null) {
                if(player.getInventory().getUsableItem().getDamages() < 0) {
                    player.dammages(player.getInventory().getUsableItem().getDamages());
                    addLogs(Color.RED, new Text("You Healed yourself using a "+player.getInventory().getUsableItem().toString()+"\n"));
                    player.getInventory().setUsableItem(null);
                    haveNothing = false;
                }
            }
            else if(action == ActionDataBase.Action.MAGIC && player.getInventory().getMagic() != null) {
                if(player.getInventory().getMagic().getDamages() < 0) {
                    player.dammages(player.getInventory().getMagic().getDamages());
                    addLogs(Color.RED, new Text("You read the incantation in the "+player.getInventory().getMagic().getDamages()+" and healed yourself\n"));
                    player.getInventory().setMagic(null);
                    haveNothing = false;
                }
            }
        }
        if(haveNothing) { addLogs(Color.WHITE, new Text("You tried to damage the void, without success...\n")); }
        updateInventoryInfo();
        updateLifeBar();
    }

    private void containsChest(int x, int y) {
        Chamber chamber = (Chamber) dungeon.getRoom(x, y);
        if(chamber.chest != null) {
            drawChest.setChest(chamber.chest);
            drawChest.draw();
        } else { drawChest.clear(); }
    }

    private void containsMonster(int x, int y) {
        Chamber chamber = (Chamber) dungeon.getRoom(x, y);
        if(chamber.monster != null) {
            if(!chamber.monster.isDead()) {
                addLogs(Color.RED, new Text("You face a "+chamber.monster.getName()+"\n"));
                drawMonster.setMonster(chamber.monster);
                drawMonster.draw();
            } else { drawMonster.clear(); }
        } else { drawMonster.clear(); }
    }

    /**
     * method that will add an action log dedicated to interaction
     */
    private void actionInteract() {
        if(player.isDead()) return;
        Text actionLog = new Text("<You are not supposed to be there and you know it>\n");
        if(getPlayerRoom(player) instanceof Chamber) {
            Chamber room = (Chamber) getPlayerRoom(player);
            if(room.monster != null) {
                if (!room.monster.isDead()) {
                    addLogs(Color.WHITE, new Text("<You tried to communicate with the "+room.monster.getName()+" but all you could hear was incomprehensible noises>\n"));
                    return;
                }
            }
            if(room.chest != null) {
                actionLog = room.chest.dropItem(player);
                drawChest.setChest(room.chest);
                drawChest.draw();
                updateInventoryInfo(); }
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
