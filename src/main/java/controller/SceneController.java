package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.DataBase.*;
import model.Difficulty.*;
import model.Dungeon.*;
import model.Entity.EntityFactory;
import model.Entity.Player;
import model.Room.*;
import view.draw.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SceneController implements Initializable {

    private EntityFactory entityFactory = new EntityFactory();
    //20 = nb de cellules du donjon (20*20), reduire la taille pour simplifier, ne pas aller au dessus de 60.
    private final DifficultyStrategy difficultyStrategy = new SimpleDifficultyEnhance(1, 20);
    private final DungeonGenerator dungeonGenerator = new EnhancedStepByStepDungeonGenerator(difficultyStrategy.getCellSize());
    private Dungeon dungeon;
    private Player player;

    @FXML private TextFlow textLogsContent;
    @FXML private Pane lifebar;
    @FXML private Canvas minimap;
    @FXML private ImageView spriteMonster;
    @FXML private ImageView spriteChest;
    @FXML private TextFlow weaponInfo;
    @FXML private TextFlow itemInfo;
    @FXML private TextFlow magicInfo;
    @FXML private Pane scene;

    private MiniMap drawMiniMap;
    private LifeBar drawLifeBar;
    private ChestDrawable drawChest;
    private MonsterDrawable drawMonster;
    private DungeonDrawable drawDungeon;
    private LogsDrawable drawLogs;
    private InventoryInfo inventoryInfo;
    private boolean showedtextfaceAMonster = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dungeon = new Dungeon(dungeonGenerator.generate());

        Integer[] position = dungeonGenerator.getStartingPosition();
        //stats du personage
        player = entityFactory.createPlayer(position[0], position[1], 1000, 10, "Adventurer");

        Rectangle playerLife = new Rectangle(lifebar.getPrefWidth(), lifebar.getPrefHeight(), Color.LIME);
        lifebar.getChildren().add(playerLife);
        if(((Chamber) getPlayerRoom(player)).InitializeRoom(difficultyStrategy.getDifficulty())) { difficultyStrategy.doUpdateDifficulty(); }

        drawMiniMap = new MiniMap(minimap, player, dungeon, dungeonGenerator.getCellSize());
        drawLifeBar = new LifeBar(playerLife, player, lifebar.getPrefWidth());
        drawChest = new ChestDrawable(spriteChest);
        drawMonster = new MonsterDrawable(spriteMonster, scene);
        drawDungeon = new DungeonDrawable(scene);
        drawLogs = new LogsDrawable(textLogsContent);
        inventoryInfo = new InventoryInfo(weaponInfo, itemInfo, magicInfo, player.getInventory());
        drawLogs.addLogs(Color.WHITE, new Text("Thou didst wake up in a dark dungeon where danger lies in every room....\n"));
        update();
    }

    private void update() {
        updateMonster(player.x, player.y);
        updateChest(player.x, player.y);
        updateMiniMap();
        updateDungeon();
        updateLifeBar();
        updateInventoryInfo();
    }

    private void updateMiniMap() {
        drawMiniMap.draw();
    }
    private void updateLifeBar() { drawLifeBar.draw(); }
    private void updateInventoryInfo() { inventoryInfo.draw(); }
    private void updateDungeon() {
        if(getPlayerRoom(player) instanceof ExitRoom || dungeon.isExited()) {
            drawDungeon.setBackground("../ressources/room-final.jpg");
        }
        else if(getPlayerRoom(player) instanceof Chamber) {
            String wallLeft = (dungeon.getRoom(player.x-1, player.y) instanceof Wall) ? "0" : "1";
            String wallTop = (dungeon.getRoom(player.x, player.y-1) instanceof Wall) ? "0" : "1";
            String wallRight = (dungeon.getRoom(player.x+1, player.y) instanceof Wall) ? "0" : "1";
            String values = wallLeft+wallTop+wallRight;
            drawDungeon.setBackground("../ressources/room-"+values+".jpg");
        }
        drawDungeon.draw();
    }
    private void updateChest(int x, int y) {
        drawChest.clear();
        if(getPlayerRoom(player) instanceof Chamber) {
            Chamber chamber = (Chamber) dungeon.getRoom(x, y);
            if(chamber.chest != null) {
                drawChest.setChest(chamber.chest);
                drawChest.draw();
            }
        }
    }
    private void updateMonster(int x, int y) {
        drawMonster.clear();
        if(getPlayerRoom(player) instanceof Chamber) {
            Chamber chamber = (Chamber) dungeon.getRoom(x, y);
            if(chamber.monster != null) {
                if(!chamber.monster.isDead()) {
                    if(!showedtextfaceAMonster) { drawLogs.addLogs(Color.RED, new Text("Thou face a "+chamber.monster.getName()+"\n")); showedtextfaceAMonster = true; }
                    drawMonster.setMonster(chamber.monster);
                    drawMonster.draw();
                } else {showedtextfaceAMonster = false;}
            } else {showedtextfaceAMonster = false;}
        }
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
        if(player.isDead() || dungeon.isExited()) return;
        Chamber actualChamber = ((Chamber) getPlayerRoom(player));
        if(actualChamber.isBattle()) {
            actionBattle(action);
            return;
        }
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
            drawLogs.addLogs(Color.WHITE, new Text("<Thou cannot pass a wall>\n"));
            return;
        }
        drawLogs.addLogs(Color.WHITE, new Text("<Thou hast moved to ("+player.x+", "+player.y+")>\n"));
        if (getPlayerRoom(player) instanceof ExitRoom) {
            dungeon.setIsExited(true);
            drawLogs.addLogs(Color.GOLD, new Text("<VICTORY>\nThou succeed to exit the dungeon !\n" +
                    "as a reward thou treat thyself with a whole Schwarzwälder Kirschtorte\n<PRESS INTERACT TO GO BACK TO TITLE SCREEN>\n"));
        }
        else if(((Chamber) getPlayerRoom(player)).InitializeRoom(difficultyStrategy.getDifficulty())) {
            difficultyStrategy.doUpdateDifficulty();
            initBattle(getPlayerRoom(player));
        }
        update();
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
        if(player.isDead() || dungeon.isExited()) return;
        boolean haveNothing = true;
        if ((getPlayerRoom(player) instanceof Chamber)) {
            Chamber playerChamber = (Chamber) getPlayerRoom(player);
            if(playerChamber.isBattle()) {
                Text[] logs = playerChamber.battleTurn(action);
                drawLogs.addLogs(Color.RED, logs);
                if(playerChamber.monster.isDead()) {
                    drawMonster.setMonster(null);
                }
                haveNothing = false;
            }
            else if(action == ActionDataBase.Action.ITEM && player.getInventory().getUsableItem() != null) {
                if(player.getInventory().getUsableItem().getDamages() < 0) {
                    player.dammages(player.getInventory().getUsableItem().getDamages());
                    drawLogs.addLogs(Color.RED, new Text("Thou didst heal yourself using a "+player.getInventory().getUsableItem().toString()+"\n"));
                    player.getInventory().setUsableItem(null);
                    haveNothing = false;
                }
            }
            else if(action == ActionDataBase.Action.MAGIC && player.getInventory().getMagic() != null) {
                if(player.getInventory().getMagic().getDamages() < 0) {
                    player.dammages(player.getInventory().getMagic().getDamages());
                    drawLogs.addLogs(Color.RED, new Text("Thou read the incantation in the "+player.getInventory().getMagic().getDamages()+" and healed thyself\n"));
                    player.getInventory().setMagic(null);
                    haveNothing = false;
                }
            }
        }
        if(haveNothing) { drawLogs.addLogs(Color.WHITE, new Text("Thou hast tried to damage the void, without success...\n")); }
        update();
    }

    /**
     * method that will add an action log dedicated to interaction
     */
    private void actionInteract() {
        if(player.isDead() || dungeon.isExited()) {
            System.exit(0);
        }
        Text actionLog = new Text("<Thou art not did suppose to be there and thee know it>\n");
        if(getPlayerRoom(player) instanceof Chamber) {
            Chamber room = (Chamber) getPlayerRoom(player);
            if(room.monster != null) {
                if (!room.monster.isDead()) {
                    drawLogs.addLogs(Color.WHITE, new Text("<Thou hast tried to communicate with the "+room.monster.getName()+" but all thou could hear wast incomprehensible noises>\n"));
                    return;
                }
            }
            if(room.chest != null) {
                actionLog = room.chest.dropItem(player);
                drawChest.setChest(room.chest);
                drawChest.draw(); }
            else { actionLog = new Text("<This room is all dark and lonely>\n"); }
        }
        drawLogs.addLogs(Color.WHITE, actionLog);
        update();
    }
}
