package controller;

import model.hero.Hero;
import model.item.Armor;
import model.item.Potion;
import model.item.Spell;
import model.item.Weapon;
import utils.Dice;
import view.HeroView;

/**
 * model.hero.Hero controller. Some action for hero
 */
public class HeroControllerImpl extends CharacterControllerImpl implements HeroController {
    private final Hero hero;
    private HeroView heroView;
    private Armor armor;
    private Weapon weapon;

    public HeroControllerImpl(Hero hero, HeroView heroView) {
        this.hero = hero;
        this.heroView = heroView;
        armor = new Armor(null, 0, 0, 0);
        weapon = new Weapon(null, 0, 0, 0, 0);
    }

    @Override
    public Hero getCharacter() {
        return hero;
    }

    @Override
    public void respawn() {
        Hero hero = getCharacter();
        hero.setHp(getLevel() * 50);
        hero.setMana(getLevel() * 50);
    }

    @Override
    public void gainExp(int exp) {
        hero.setExperience(hero.getExperience() + exp);
        if (hero.getExperience() > hero.getLevel() * 10) {
            levelUp();
        }
    }

    @Override
    public int hpRegain() {
        Hero hero = getCharacter();
        int regain = (int) (hero.getHp() * 0.1);
        hero.setHp(hero.getHp() + regain);
        return regain;
    }

    @Override
    public int mpRegain() {
        Hero hero = getCharacter();
        int regain = (int) (hero.getMana() * 0.1);
        hero.setMana(hero.getMana() + regain);
        return regain;
    }

    @Override
    public boolean isDodge() {
        return Dice.roll(1000) <= (getCharacter().getDodge() * 10 + getCharacter().getAgility() * 2);
    }

    @Override
    public boolean levelUp() {
        if (hero.getExperience() < hero.getLevel() * 10)
            return false;
        hero.levelUp();
        return true;
    }

    @Override
    public void gainMoney(int money) {
        hero.setMoney(hero.getMoney() + money);
    }


    @Override
    public int getDamage() {
        return (int) Math.ceil((getCharacter().getStrength() + weapon.getDamage()) * 0.05);
    }

    @Override
    public int useSpell(Spell spell, CharacterController enemy) {
        Hero user = getCharacter();
        int damage = spell.getDamage() + (int) Math.ceil((hero.getDexterity() / 10000.0) * spell.getDamage());
        user.setMana(hero.getMana() - spell.getMana());
        enemy.damageDealt(damage);
        enemy.spellDealt(spell);
        return damage;
    }

    @Override
    public void usePotion(Potion potion) {
        potion.use(getCharacter());
    }

    @Override
    public void equipArmor(Armor newArmor) {
        if (newArmor == null)
            newArmor = new Armor(null, 0, 0, 0);

        if (newArmor.getLevel() > hero.getLevel()) {
            System.out.println("Required level is: " + newArmor.getLevel());
            return;
        }

        hero.setDefense(hero.getDefense() - armor.getReduction() + newArmor.getReduction());
        armor = newArmor;
    }

    @Override
    public void equipWeapon(Weapon newWeapon) {
        if (newWeapon == null)
            newWeapon = new Weapon(null, 0, 0, 0, 0);

        if (newWeapon.getLevel() > hero.getLevel()) {
            System.out.println("Required level is: " + newWeapon.getLevel());
            return;
        }

        hero.setDamage(hero.getDamage() - weapon.getDamage() + newWeapon.getDamage());
        weapon = newWeapon;
    }

    @Override
    public void show() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return heroView.show(hero, armor, weapon);
    }
}
