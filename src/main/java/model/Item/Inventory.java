package model.Item;

public class Inventory {

    private Weapon weapon;
    private Magic magic;
    private UsableItem usableItem;

    public Inventory(Weapon weapon, Magic magic, UsableItem usableItem) {
        this.weapon = weapon;
        this.magic = magic;
        this.usableItem = usableItem;
    }

    public Weapon getWeapon() { return weapon; }

    public Magic getMagic() { return magic; }

    public UsableItem getUsableItem() { return usableItem; }

    public void setWeapon(Weapon weapon) { this.weapon = weapon; }

    public void setMagic(Magic magic) { this.magic = magic; }

    public void setUsableItem(UsableItem usableItem) { this.usableItem = usableItem; }
}
