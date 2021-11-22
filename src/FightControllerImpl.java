import java.util.Iterator;
import java.util.List;

/**
 * controller for fight
 * control the procedure of fight
 */
public class FightControllerImpl implements FightController {
    private TeamController team;
    private List<HeroController> heroControllerList;
    private List<MonsterController> monsterControllerList;
    private FightView fightView;
    private Iterator<HeroController> heroControllerIterator;
    private Iterator<MonsterController> monsterControllerIterator;

    public FightControllerImpl(TeamController team, List<HeroController> heroControllerList,
                               List<MonsterController> monsterControllerList, FightView fightView) {
        this.team = team;
        this.heroControllerList = heroControllerList;
        this.monsterControllerList = monsterControllerList;
        this.fightView = fightView;
    }

    @Override
    public boolean isFightEnd() {
        return monsterControllerList.stream().allMatch(CharacterController::isDied) ||
                heroControllerList.stream().allMatch(CharacterController::isDied);
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
        heroControllerIterator = heroControllerList.iterator();
        monsterControllerIterator = monsterControllerList.iterator();

        // hero attack first
        for (HeroController heroController : heroControllerList) {
            if (heroController.isDied())
                continue;

            showLog();
            fightView.showInformation(heroControllerList, monsterControllerList);

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
        for (MonsterController monster : monsterControllerList) {
            if (monster.isDied())
                continue;

            HeroController heroController = nextHero();
            int damage = heroController.damageDealt(monster.getDamage());
            fightView.attackLog(monster.getCharacter(), heroController.getCharacter(), damage);
        }

        // regain HP and MP
        for (HeroController heroController : heroControllerList) {
            if (!heroController.isDied()) {
                fightView.regainLog(heroController.getCharacter(), heroController.hpRegain(), heroController.mpRegain());
            }
        }
    }

    private HeroController nextHero() {
        HeroController heroController = null;
        while (heroControllerIterator.hasNext()) {
            heroController = heroControllerIterator.next();
            if (!heroControllerIterator.hasNext() && !isFightEnd())
                heroControllerIterator = heroControllerList.iterator();
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
                monsterControllerIterator = monsterControllerList.iterator();
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
            int money = monsterControllerList.stream().map(m -> m.getLevel() * 40).reduce(0, Integer::sum);
            for (HeroController controller : heroControllerList) {
                if (!controller.isDied()) {
                    team.gainMoney(money);
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
            // reset hp and mp
            for (HeroController heroController : heroControllerList) {
                heroController.respawn();
            }
        }

        // show result
        fightView.showLog();
        fightView.showResult(isHeroWin());
        fightView.showInformation(heroControllerList);
    }

    @Override
    public void showLog() {
        fightView.showLog();
    }

    @Override
    public boolean isHeroWin() {
        for (HeroController heroController : heroControllerList) {
            if (!heroController.isDied())
                return true;
        }
        return false;
    }
}
