package controller;

import model.item.Armor;
import model.item.Potion;
import model.item.Spell;
import model.item.Weapon;
import view.FightView;

/**
 * controller for fight
 * control the procedure of fight
 */
public class FightControllerImpl implements FightController {
    private TeamController team;
    private HeroController hero;
    private MonsterController monster;
    private FightView fightView;

    public FightControllerImpl(TeamController team, HeroController hero, MonsterController monster, FightView fightView) {
        this.team = team;
        this.hero = hero;
        this.monster = monster;
        this.fightView = fightView;
    }

    @Override
    public boolean isFightEnd() {
        return monster.isDied() || hero.isDied();
    }

    @Override
    public void start() {
        fightStart();
        while (!isFightEnd()) {
            round();
        }
        fightEnd();
    }

    @Override
    public void round() {
        // hero attack first
        showLog();
        fightView.showInformation(hero, monster);

        int action = FightView.QUIT;
        while (action == FightView.QUIT) {
            // choose action
            fightView.showEnemy(monster);
            action = fightView.chooseAction(hero);
            switch (action) {
                // regular attack
                case FightView.ATTACK -> {
                    if (monster.isDodge()) {
                        fightView.dodgeLog(hero.getCharacter(), monster.getCharacter());
                    } else {
                        int damage =
                                monster.damageDealt(hero.getDamage());
                        fightView.attackLog(hero.getCharacter(), monster.getCharacter(), damage);
                    }
                }
                case FightView.CAST -> {
                    team.showSpellList();
                    action = fightView.choose(team.getSpellList().size());
                    if (action == FightView.QUIT)
                        continue;
                    Spell spell = team.getSpellList().get(action - 1);
                    if (hero.getCharacter().getMana() < spell.getMana() ||
                            hero.getCharacter().getLevel() < spell.getLevel())
                        continue;
                    int damage = team.useSpell(hero, monster, spell);
                    fightView.castLog(hero.getCharacter(), monster.getCharacter(),
                            spell.getName(), damage);
                }
                case FightView.POTION -> {
                    team.showPotionList();
                    action = fightView.choose(team.getPotionList().size());
                    if (action == FightView.QUIT)
                        continue;
                    Potion potion = team.getPotionList().get(action - 1);
                    if (hero.getCharacter().getLevel() < potion.getLevel())
                        continue;
                    team.usePotion(hero, action - 1);
                    fightView.itemLog(hero.getCharacter(), potion.getName());
                }
                case FightView.WEAPON -> {
                    team.showWeaponList();
                    action = fightView.choose(team.getWeaponList().size());
                    if (action == FightView.QUIT)
                        continue;
                    Weapon weapon = team.getWeaponList().get(action - 1);
                    if (hero.getCharacter().getLevel() < weapon.getLevel())
                        continue;
                    hero.equipWeapon(team.getWeaponList().get(action - 1));
                    fightView.itemLog(hero.getCharacter(), weapon.getName());
                }
                case FightView.ARMOR -> {
                    team.showArmorList();
                    action = fightView.choose(team.getArmorList().size());
                    if (action == FightView.QUIT)
                        continue;
                    Armor armor = team.getArmorList().get(action - 1);
                    if (hero.getCharacter().getLevel() < armor.getLevel())
                        continue;
                    hero.equipArmor(team.getArmorList().get(action - 1));
                    fightView.itemLog(hero.getCharacter(), armor.getName());
                }
            }
        }

        // monster attack
        int damage = hero.damageDealt(monster.getDamage());
        fightView.attackLog(monster.getCharacter(), hero.getCharacter(), damage);

        // regain HP and MP
        if (!hero.isDied()) {
            fightView.regainLog(hero.getCharacter(), hero.hpRegain(), hero.mpRegain());
        }
    }


    @Override
    public void fightStart() {
        fightView.fightStart();
    }

    @Override
    public void fightEnd() {
        // hero win
        if (isHeroWin()) {
            // gain exp and money
            if (!hero.isDied()) {
                team.gainMoney(monster.getCharacter().getLevel() * 100);
                hero.gainExp(2);
                if (hero.levelUp())
                    fightView.levelUp(hero.getCharacter());
            } else {
                hero.revive();
            }

        } else {
            // hero lose, lose money
            team.setMoney(team.getMoney() / 2);
        }

        // show result
        fightView.showLog();
        fightView.showResult(isHeroWin());
        fightView.showInformation(hero);
    }

    @Override
    public void showLog() {
        fightView.showLog();
    }

    @Override
    public boolean isHeroWin() {
        if (!hero.isDied())
            return true;
        return false;
    }
}
