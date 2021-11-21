package controller;

import model.hero.Hero;
import model.item.Armor;
import model.item.Potion;
import model.item.Spell;
import model.item.Weapon;

/**
 * model.hero.Hero controller. Some action for hero
 */
public interface HeroController extends CharacterController {

    @Override
    Hero getCharacter();

    void respawn();

    void gainExp(int exp);

    int hpRegain();

    int mpRegain();

    boolean levelUp();

    void gainMoney(int money);

    int useSpell(Spell spell, CharacterController enemy);

    void usePotion(Potion potion);

    void equipArmor(Armor armor);

    void equipWeapon(Weapon weapon);
}
