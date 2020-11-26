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
    Random random = new Random();
    public boolean isVisited;
    private int difficulty;

    private Battle battle = null;
    public Monster monster = null;
    public Chest chest = null;

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
        if(random.nextInt(101)%5 == 0) { monster = MonsterDataBase.generateMonster(difficulty); }
        if(random.nextInt(101)%3 == 0) {
            int randomItem = random.nextInt(3);
            Chest newChest = null;
            if(randomItem == 0) { newChest = new Chest(ItemDataBase.generateWeapon(difficulty)); }
            else if(randomItem == 1) { newChest = new Chest(ItemDataBase.generateUsableItem(difficulty)); }
            else if(randomItem == 2) { newChest = new Chest(ItemDataBase.generateMagic(difficulty)); }
            chest = newChest;
        }
    }
}
