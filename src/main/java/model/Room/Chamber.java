package model.Room;

import javafx.scene.text.Text;
import model.Battle.Battle;
import model.DataBase.ActionDataBase;
import model.DataBase.ItemDataBase;
import model.DataBase.MonsterDataBase;
import model.Entity.Chest;
import model.Entity.EntityFactory;
import model.Entity.Monster;
import model.Entity.Player;
import model.Item.ItemFactory;

import java.util.Random;

public class Chamber implements  Room {
    EntityFactory entityFactory = new EntityFactory();
    ItemFactory itemFactory = new ItemFactory();
    public boolean[] openedDoors;
    Random random = new Random();
    private boolean isFinal;
    public boolean isVisited;
    private int difficulty;

    private Battle battle = null;
    public Monster monster = null;
    public Chest chest = null;

    public Chamber(boolean[] openedDoors, boolean isFinal) {
        isVisited = false;
        this.openedDoors = openedDoors;
        this.isFinal = isFinal;
    }

    public Boolean InitializeRoom(int difficulty) {
        if(isVisited) return false;
        this.difficulty = difficulty;
        fillChamber();
        isVisited = true;
        return true;
    }

    public Boolean initializeBattle(Player player) {
        if(isBattle() || monster == null || monster.isDead()) return false;
        battle = new Battle(player, monster);
        return true;
    }

    public Boolean isBattle() { return battle != null; }

    public Text[] battleTurn(ActionDataBase.Action playerAction) {
        ActionDataBase.Action monsterAction = monster.selectAction();
        Text[] battleLogs = battle.newTurn(playerAction, monsterAction);
        if(monster.isDead()) {
            battle = null;
        }
        return battleLogs;
    }

    private void fillChamber() {
        if(isFinal) return;
        if(random.nextInt(101)%5 == 0) {
            //todo generate lot of possibilities
            MonsterDataBase.monsters newMonster = MonsterDataBase.getRandomMonster();
            monster = entityFactory.createMonster(newMonster, random.nextInt(50*difficulty), random.nextInt(5*difficulty), newMonster.toString());
        }
        if(random.nextInt(101)%3 == 0) {
            //todo generate lot of possibilities
            int randomItem = random.nextInt(3);
            Chest newChest = null;
            if(randomItem == 0) { newChest = new Chest(itemFactory.createWeapon("God", 10*difficulty, ItemDataBase.getRandomWeapon())); }
            else if(randomItem == 1) { newChest = new Chest(itemFactory.createUsableItem("God", 10*difficulty, ItemDataBase.getRandomUsableItem())); }
            //todo if healing item or magic negative dammages
            else if(randomItem == 2) { newChest = new Chest(itemFactory.createMagic("God", 15*difficulty, ItemDataBase.getRandomMagic())); }
            chest = newChest;
        }
    }
}
