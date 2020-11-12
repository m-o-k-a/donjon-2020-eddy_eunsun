package model.DataBase;

public class MonsterDataBase {
    public enum monsters {
        SLIME
    }
    public String getSprite(monsters monster) {
        return "../../ressources/slime.png";
    }
}
