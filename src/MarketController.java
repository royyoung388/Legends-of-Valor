import java.util.List;

/**
 * market controller. Control the market.
 */
public interface MarketController {

    Armor buyArmor(int index);

    Weapon buyWeapon(int index);

    Potion buyPotion(int index);

    Spell buySpell(int index);

    void showAll();

    void showArmorList();

    void showWeaponList();

    void showPotionList();

    void showSpellList();

    List<Armor> getArmorList();

    List<Weapon> getWeaponList();

    List<Potion> getPotionList();

    List<Spell> getSpellList();
}
