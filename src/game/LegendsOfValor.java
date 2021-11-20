package game;

import controller.*;
import factory.HeroFactory;
import factory.MonsterFactory;
import model.Character;
import model.MarketModel;
import model.hero.Hero;
import model.monster.Monster;
import state.FightState;
import state.WalkState;
import utils.Text;
import view.MarketView;
import view.MonsterView;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class LegendsOfValor extends RPGGame {
    private Scanner scanner;
    private MarketController marketController;
    private List<MonsterController> monsterControllerList;
    private Iterator<MonsterController> monsterIter;
    private Iterator<HeroController> heroIter;
    // current lane
    private int lane = 0;
    // record the game status. 0 for quit.
    private int gameFlag = 1;

    public LegendsOfValor() {
        this(8, 8);
    }

    public LegendsOfValor(int row, int column) {
        super(row, column);
        scanner = new Scanner(System.in);
        marketController = new MarketControllerImpl(new MarketView(), new MarketModel());
        // init monster
        monsterControllerList = new ArrayList<>(3);
        for (Monster monster : new MonsterFactory().randomChoose(3, 1))
            monsterControllerList.add(new MonsterControllerImpl(monster, new MonsterView()));
    }

    public MarketController getMarketController() {
        return marketController;
    }

    // get current lane to do action.
    // lane starts from 0
    public int getLane() {
        return lane;
    }

    // set lane
    public void setLane(int lane) {
        this.lane = lane % 3;
    }

    /***
     * get next character. The order of the result is:
     * hero1 - hero2 - hero3 - monster1- monster2 - monster3 - null
     * and then repeat
     * Be careful when the hero or monster is died.
     * {@link CharacterController#isDied()}
     * @return
     */
    public CharacterController nextCharacter() {
        if (monsterIter == null || heroIter == null) {
            monsterIter = monsterControllerList.iterator();
            heroIter = getTeamController().getHeroControllerList().iterator();
        }

        // hero
        if (heroIter.hasNext()) {
            return heroIter.next();
        } else if (monsterIter.hasNext()) {
            // monster
            return monsterIter.next();
        } else {
            // round end, reset
            heroIter = getTeamController().getHeroControllerList().iterator();
            monsterIter = monsterControllerList.iterator();
            return null;
        }
    }

    /***
     * get position for specific character
     * @param character
     * @return null if not found
     */
    public Position getPosition(CharacterController character) {
        int index = getTeamController().getHeroControllerList().indexOf(character);
        if (index != -1) {
            return getBoardController().getPosition(index);
        }
        index = monsterControllerList.indexOf(character);
        if (index != -1) {
            return getBoardController().getPosition(index);
        }
        return null;
    }

    @Override
    protected void initGame() {
        getBoardController().init();
    }

    @Override
    protected boolean isEnd() {
        return gameFlag == 0;
    }

    @Override
    public void quit() {
        gameFlag = 0;
        System.out.println(Text.THANKS);
    }

    @Override
    public void start() {
        System.out.println(Text.WELCOM);
        System.out.println(Text.INSTRUCTION);
        // wait for 2 seconds
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chooseHero();
        context.addState(new WalkState());

        while (!isEnd()) {
            context.getState().showPrompt(context);
            String action = null;
            if (!(context.getState() instanceof FightState))
                action = scanner.nextLine();
            context.getState().doAction(context, action);
        }
    }

    private void chooseHero() {
        HeroFactory factory = new HeroFactory();
        List<Hero> heroList = new ArrayList<>();
        int index = 1;

        System.out.println("====== PALADIN ======");
        List<Hero> paladin = factory.readAll(factory.paladin);
        System.out.println("   " + Hero.header());
        for (Hero hero : paladin) {
            System.out.printf("%3d%s\n", index, hero);
            index++;
        }
        heroList.addAll(paladin);

        System.out.println("====== WARRIOR ======");
        System.out.println("   " + Hero.header());
        List<Hero> warrior = factory.readAll(factory.warrior);
        for (Hero hero : warrior) {
            System.out.printf("%3d%s\n", index, hero);
            index++;
        }
        heroList.addAll(warrior);

        System.out.println("====== SORCERER ======");
        System.out.println("   " + Hero.header());
        List<Hero> sorcerer = factory.readAll(factory.sorcerer);
        for (Hero hero : sorcerer) {
            System.out.printf("%3d%s\n", index, hero);
            index++;
        }
        heroList.addAll(sorcerer);

        String id = null;
        do {
            System.out.println("\nInput number to choose your legend: (press 0 to stop choosing)");
            id = scanner.next();
            if (Integer.parseInt(id) > 0 && Integer.parseInt(id) < heroList.size()) {
                teamController.addHero(heroList.get(Integer.parseInt(id) - 1));
                teamController.showTeam();
            }
        } while (!id.equals("0") || teamController.size() < 3);
    }
}
