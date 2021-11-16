package controller;

import model.item.Armor;
import model.item.Potion;
import model.item.Spell;
import model.item.Weapon;
import view.FightView;

import java.util.Iterator;
import java.util.List;

/**
 * controller for fight
 * control the procedure of fight
 */
public class FightControllerImpl implements FightController {
    private TeamController team;
    private int lane;
    private List<MonsterController> monsters;
    private FightView fightView;
    private Iterator<HeroController> heroControllerIterator;
    private Iterator<MonsterController> monsterControllerIterator;

    public FightControllerImpl(TeamController team, List<MonsterController> monsters, FightView fightView, int lane) {
        this.team = team;
        this.monsters = monsters;
        this.fightView = fightView;
        this.lane = lane;
    }

    @Override
    public boolean isFightEnd() {
        return monsters.stream().allMatch(CharacterController::isDied) ||
                team.getHeroControllerList().stream().allMatch(CharacterController::isDied);
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
        heroControllerIterator = team.getHeroControllerList().iterator();
        monsterControllerIterator = monsters.iterator();

        // hero attack first
        for (HeroController heroController : team.getHeroControllerList()) {
            if (heroController.isDied())
                continue;

            showLog();
            fightView.showInformation(team, monsters);

            MonsterController monsterController = nextMonster();
            int action = FightView.QUIT;
            while (action == FightView.QUIT) {
                // choose action
                fightView.showEnemy(monsterController);
                action = fightView.chooseAction(heroController);
                switch (action) {
                    // regular attack
                    case FightView.ATTACK -> {
                        if (monsterController.isDodge()) {
                            fightView.dodgeLog(heroController.getCharacter(), monsterController.getCharacter());
                        } else {
                            int damage =
                                    monsterController.damageDealt(heroController.getDamage());
                            fightView.attackLog(heroController.getCharacter(), monsterController.getCharacter(), damage);
                        }
                    }
                    case FightView.CAST -> {
                        team.showSpellList();
                        action = fightView.choose(team.getSpellList().size());
                        if (action == FightView.QUIT)
                            continue;
                        Spell spell = team.getSpellList().get(action - 1);
                        if (heroController.getCharacter().getMana() < spell.getMana() ||
                                heroController.getCharacter().getLevel() < spell.getLevel())
                            continue;
                        int damage = team.useSpell(heroController, monsterController, spell);
                        fightView.castLog(heroController.getCharacter(), monsterController.getCharacter(),
                                spell.getName(), damage);
                    }
                    case FightView.POTION -> {
                        team.showPotionList();
                        action = fightView.choose(team.getPotionList().size());
                        if (action == FightView.QUIT)
                            continue;
                        Potion potion = team.getPotionList().get(action - 1);
                        if (heroController.getCharacter().getLevel() < potion.getLevel())
                            continue;
                        team.usePotion(heroController, action - 1);
                        fightView.itemLog(heroController.getCharacter(), potion.getName());
                    }
                    case FightView.WEAPON -> {
                        team.showWeaponList();
                        action = fightView.choose(team.getWeaponList().size());
                        if (action == FightView.QUIT)
                            continue;
                        Weapon weapon = team.getWeaponList().get(action - 1);
                        if (heroController.getCharacter().getLevel() < weapon.getLevel())
                            continue;
                        heroController.equipWeapon(team.getWeaponList().get(action - 1));
                        fightView.itemLog(heroController.getCharacter(), weapon.getName());
                    }
                    case FightView.ARMOR -> {
                        team.showArmorList();
                        action = fightView.choose(team.getArmorList().size());
                        if (action == FightView.QUIT)
                            continue;
                        Armor armor = team.getArmorList().get(action - 1);
                        if (heroController.getCharacter().getLevel() < armor.getLevel())
                            continue;
                        heroController.equipArmor(team.getArmorList().get(action - 1));
                        fightView.itemLog(heroController.getCharacter(), armor.getName());
                    }
                }
            }
        }

        // monster attack
        for (MonsterController monster : monsters) {
            if (monster.isDied())
                continue;

            HeroController heroController = nextHero();
            int damage = heroController.damageDealt(monster.getDamage());
            fightView.attackLog(monster.getCharacter(), heroController.getCharacter(), damage);
        }

        // regain HP and MP
        for (HeroController heroController : team.getHeroControllerList()) {
            if (!heroController.isDied()) {
                fightView.regainLog(heroController.getCharacter(), heroController.hpRegain(), heroController.mpRegain());
            }
        }

//        showLog();
//        fightView.showInformation(team, monsters);
    }


    private HeroController nextHero() {
        HeroController heroController = null;
        while (heroControllerIterator.hasNext()) {
            heroController = heroControllerIterator.next();
            if (!heroControllerIterator.hasNext() && !isFightEnd())
                heroControllerIterator = team.getHeroControllerList().iterator();
            if (!heroController.isDied())
                break;
        }
        return heroController;
    }

    private MonsterController nextMonster() {
        MonsterController monsterController = null;
        while (monsterControllerIterator.hasNext()) {
            monsterController = monsterControllerIterator.next();
            if (!monsterControllerIterator.hasNext() && !isFightEnd())
                monsterControllerIterator = monsters.iterator();
            if (!monsterController.isDied())
                break;
        }
        return monsterController;
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
            List<HeroController> heroControllerList = team.getHeroControllerList();
            for (int i = 0; i < heroControllerList.size(); i++) {
                HeroController controller = heroControllerList.get(i);
                if (!controller.isDied()) {
                    team.gainMoney(monsters.get(i).getCharacter().getLevel() * 100);
                    controller.gainExp(2);
                    if (controller.levelUp())
                        fightView.levelUp(controller.getCharacter());
                } else {
                    controller.revive();
                }
            }
        } else {
            // hero lose, lose money
            team.setMoney(team.getMoney() / 2);
        }

        // show result
        fightView.showLog();
        fightView.showResult(isHeroWin());
        fightView.showInformation(team);


    }

    @Override
    public void showLog() {
        fightView.showLog();
    }

    @Override
    public boolean isHeroWin() {
        for (HeroController heroController : team.getHeroControllerList()) {
            if (!heroController.isDied())
                return true;
        }
        return false;
    }
}
