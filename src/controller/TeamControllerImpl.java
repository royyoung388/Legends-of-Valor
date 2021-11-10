package controller;

import bean.Backpack;
import bean.hero.Hero;
import bean.item.Armor;
import bean.item.Potion;
import bean.item.Spell;
import bean.item.Weapon;
import utils.Config;
import view.HeroView;

import java.util.ArrayList;
import java.util.List;

/**
 * team controller, control the team.
 * includes the bean.hero.Hero list, backpack, money.
 */
public class TeamControllerImpl implements TeamController {

    private int money;
    private List<HeroController> heroList;
    private Backpack backpack;

    public TeamControllerImpl() {
        heroList = new ArrayList<>();
        backpack = new Backpack();
        money = 0;
    }

    @Override
    public void addHero(Hero hero) {
        hero = hero.clone();
        money += hero.getMoney();
        heroList.add(new HeroControllerImpl(hero, new HeroView()));
    }

    @Override
    public void addHeroList(List<Hero> heroList) {
        for (Hero hero : heroList) {
            addHero(hero);
        }
    }

    @Override
    public int size() {
        return heroList.size();
    }

    @Override
    public int getMoney() {
        return money;
    }

    @Override
    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public List<Weapon> getWeaponList() {
        return backpack.getWeaponList();
    }

    @Override
    public List<Armor> getArmorList() {
        return backpack.getArmorList();
    }

    @Override
    public List<Potion> getPotionList() {
        return backpack.getPotionList();
    }

    @Override
    public List<Spell> getSpellList() {
        return backpack.getSpellList();
    }

    @Override
    public void gainMoney(int money) {
        this.money += money;
        for (HeroController heroController : heroList) {
            heroController.gainMoney(money);
        }
    }

    @Override
    public void usePotion(HeroController heroController, int index) {
        heroController.usePotion(getPotionList().remove(index));
    }

    @Override
    public int useSpell(HeroController heroController, CharacterController enemy, Spell spell) {
        return heroController.useSpell(spell, enemy);
    }


    @Override
    public void buyArmor(Armor armor) {
        backpack.addArmor(armor);
    }

    @Override
    public void buyWeapon(Weapon weapon) {
        money -= weapon.getCost();
        backpack.addWeapon(weapon);
    }

    @Override
    public void buyPotion(Potion potion) {
        backpack.addPotion(potion);
    }

    @Override
    public void buySpell(Spell spell) {
        backpack.addSpell(spell);
    }

    @Override
    public void sellArmor(int index) {
        Armor armor = getArmorList().remove(index);
        money += armor.getCost() / 2;
    }

    @Override
    public void sellWeapon(int index) {
        Weapon weapon = getWeaponList().remove(index);
        money += weapon.getCost() / 2;
    }

    @Override
    public void sellPotion(int index) {
        Potion potion = getPotionList().remove(index);
        money += potion.getCost() / 2;
    }

    @Override
    public void sellSpell(int index) {
        Spell spell = getSpellList().remove(index);
        money += spell.getCost() / 2;
    }

    @Override
    public HeroController getHeroController(int index) {
        return heroList.get(index);
    }

    @Override
    public List<HeroController> getHeroControllerList() {
        return heroList;
    }

    @Override
    public void showTeam() {
        System.out.println("===== Your Team =====");
        System.out.println("   " + HeroView.header());
        for (int i = 0; i < heroList.size(); i++) {
            System.out.printf("%3d%s\n", i + 1, heroList.get(i));
        }
        System.out.println("======================\n");
    }

    @Override
    public void showItem() {
        System.out.println("============== Your Items ================");
        showWeaponList();
        showArmorList();
        showPotionList();
        showSpellList();
        System.out.println("Team Money: " + money);
    }

    @Override
    public void showArmorList() {
        System.out.println("=========== ARMOR " + Config.ARMOR_ID + " ==============");
        System.out.println("   " + Armor.header());
        for (int i = 0; i < backpack.getArmorList().size(); i++) {
            System.out.printf("%3d%s\n", i + 1, backpack.getArmorList().get(i));
        }
    }

    @Override
    public void showWeaponList() {
        System.out.println("=========== WEAPON " + Config.WEAPON_ID + " ==============");
        System.out.println("   " + Weapon.header());
        for (int i = 0; i < backpack.getWeaponList().size(); i++) {
            System.out.printf("%3d%s\n", i + 1, backpack.getWeaponList().get(i));
        }
    }

    @Override
    public void showPotionList() {
        System.out.println("=========== POTION " + Config.POTION_ID + " ==============");
        System.out.println("   " + Potion.header());
        for (int i = 0; i < backpack.getPotionList().size(); i++) {
            System.out.printf("%3d%s\n", i + 1, backpack.getPotionList().get(i));
        }
    }

    @Override
    public void showSpellList() {
        System.out.println("=========== SPELL " + Config.SPELL_ID + " ==============");
        System.out.println("   " + Spell.header());
        for (int i = 0; i < backpack.getSpellList().size(); i++) {
            System.out.printf("%3d%s\n", i + 1, backpack.getSpellList().get(i));
        }
    }
}
