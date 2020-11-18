package model.Battle;

import model.Entity.Monster;
import model.Entity.Player;

public class Battle {
    private final Player player;
    private final Monster monster;

    public Battle(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
    }

    public void fight() {

    }

    private void updateLifePoint(Character character, int damages) {

    }
}
