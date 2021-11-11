package controller;

import model.hero.Hero;
import model.item.Armor;
import model.item.Potion;
import model.item.Spell;
import model.item.Weapon;

import java.util.List;

/**
 * team controller, control the team.
 * includes the model.hero.Hero list, backpack, money.
 */
public interface TeamController {

    void addHero(Hero hero);

    void addHeroList(List<Hero> heroList);

    int size();

    int getMoney();

    void setMoney(int money);

    List<Weapon> getWeaponList();

    List<Armor> getArmorList();

    List<Potion> getPotionList();

    List<Spell> getSpellList();

    void gainMoney(int money);

    void usePotion(HeroController heroController, int index);

    int useSpell(HeroController heroController, CharacterController enemy, Spell spell);

    void buyArmor(Armor armor);

    void buyWeapon(Weapon weapon);

    void buyPotion(Potion potion);

    void buySpell(Spell spell);

    void sellArmor(int index);

    void sellWeapon(int index);

    void sellPotion(int index);

    void sellSpell(int index);

    HeroController getHeroController(int index);

    List<HeroController> getHeroControllerList();

    void showTeam();

    void showItem();

    void showArmorList();

    void showWeaponList();

    void showPotionList();

    void showSpellList();
}
