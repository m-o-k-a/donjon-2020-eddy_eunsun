package model.Battle;

import javafx.scene.text.Text;
import model.DataBase.ActionDataBase;
import model.Entity.Monster;
import model.Entity.Player;
import model.Item.Magic;
import model.Item.UsableItem;

public class Battle {
    private final Player player;
    private final Monster monster;

    public Battle(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
    }

    public Text[] newTurn(ActionDataBase.Action playerAction, ActionDataBase.Action monsterAction) {
        Text[] battleLogs = new Text[3]; battleLogs[2] = new Text("");
        battleLogs[0] = playerTurn(playerAction);
        if(monster.isDead()) { battleLogs[1] = new Text("The "+monster.type()+" is defeated\n"); }
        else {
            battleLogs[1] = monsterTurn(monsterAction);
            if(player.isDead()) { battleLogs[2] = new Text("<GAME OVER>\n<PRESS INTERACT TO GO BACK TO TITLE SCREEN>\nThe "+monster.type()+" hath killed thee\n"); }
        }
        return battleLogs;
    }

    private Text playerTurn(ActionDataBase.Action playerAction) {
        Text playerLog = new Text("Thee didst nothing, waiting for thy owneth death\n");
        switch (playerAction) {
            case UP:
            case LEFT:
            case DOWN:
            case RIGHT:
                playerLog = new Text("Thee cannot escapeth from thee owneth death\n");
                break;
            case ATTACK:
                int damages = updateMonsterHealth(monster, (player.getStrength() + (player.getInventory().getWeapon() == null ? 0 : player.getInventory().getWeapon().getDamages())));
                playerLog = new Text("Thee did attack the "+monster.type()+" It loses "+damages+" Health\n");
                break;
            case ITEM:
                UsableItem item = player.getInventory().getUsableItem();
                if(item.getDamages() < 0) {
                    updatePlayerHealth(player, item.getDamages());
                    playerLog = new Text("Thee did heal yourself using a "+item.toString()+"\n");
                } else {
                    updateMonsterHealth(monster, item.getDamages());
                    playerLog = new Text("Thee did throw a "+item.toString()+" on the "+monster.type()+". It inflicted "+item.getDamages()+" damages\n");
                }
                player.getInventory().setUsableItem(null);
                break;
            case MAGIC:
                Magic magic = player.getInventory().getMagic();
                if(magic.getDamages() < 0) {
                    updatePlayerHealth(player, magic.getDamages());
                    playerLog = new Text("Thee readeth the incantation in the "+player.getInventory().getMagic().getDamages()+" and healed theeself\n");
                } else {
                    updateMonsterHealth(monster, magic.getDamages());
                    playerLog = new Text("Thee readeth the incantation in the "+magic.toString()+" and inflicted "+magic.getDamages()+" damages to the "+monster.type()+"\n");
                }
                player.getInventory().setMagic(null);
            default:
                break;
        }
        return playerLog;
    }

    private Text monsterTurn(ActionDataBase.Action monsterAction) {
        Text monsterLog = new Text("The "+monster.type()+" didst something yond humans cannot understand\n");
        switch (monsterAction) {
            case ATTACK:
                int damages = updatePlayerHealth(player, monster.getStrength());
                monsterLog = new Text("The "+monster.type()+" Attacked thee ! Thee lose "+damages+" Health\n");
                break;
            default:
                break;
        }
        return monsterLog;
    }

    private int updatePlayerHealth(Player player, int damages) {
        player.dammages(damages);
        return damages;
    }
    private int updateMonsterHealth(Monster monster, int damages) {
        monster.dammages(damages);
        return damages;
    }


}
