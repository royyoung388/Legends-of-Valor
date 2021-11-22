import java.util.ArrayList;
import java.util.List;

/**
 * backpack, contains list of armor, weapon, potion, spell
 */
public class Backpack {
    private List<Armor> armorList;
    private List<Weapon> weaponList;
    private List<Potion> potionList;
    private List<Spell> spellList;

    public Backpack() {
        armorList = new ArrayList<>();
        weaponList = new ArrayList<>();
        potionList = new ArrayList<>();
        spellList = new ArrayList<>();
    }

    public List<Armor> getArmorList() {
        return armorList;
    }

    public List<Weapon> getWeaponList() {
        return weaponList;
    }

    public List<Potion> getPotionList() {
        return potionList;
    }

    public List<Spell> getSpellList() {
        return spellList;
    }

    public void addArmor(Armor armor) {
        armorList.add(armor);
    }

    public void addWeapon(Weapon weapon) {
        weaponList.add(weapon);
    }

    public void addPotion(Potion potion) {
        potionList.add(potion);
    }

    public void addSpell(Spell spell) {
        spellList.add(spell);
    }

    public void removeArmor(Armor armor) {
        armorList.remove(armor);
    }

    public void removeWeapon(Weapon weapon) {
        weaponList.remove(weapon);
    }

    public void removePotion(Potion potion) {
        potionList.remove(potion);
    }

    public void removeSpell(Spell spell) {
        spellList.remove(spell);
    }
}
