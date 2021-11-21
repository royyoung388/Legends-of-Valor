package game;

import controller.*;
import factory.HeroFactory;
import factory.MonsterFactory;
import model.MarketModel;
import model.board.Position;
import model.hero.Hero;
import model.monster.Monster;
import state.FightState;
import state.WalkState;
import utils.Text;
import view.MarketView;
import view.MonsterView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class LegendsOfValor extends RPGGame {
    private Scanner scanner;
    private MarketController marketController;
    private List<MonsterController> monsterControllerList;
    private Iterator<HeroController> heroIter;
    // current lane
    private int lane = 0;
    // record the game status. 0 for quit.
    private int gameFlag = 1;
    // round counter, create new monster after every 4 round.
    private int round = 0;

    public LegendsOfValor() {
        this(8, 8);
    }

    public LegendsOfValor(int row, int column) {
        super(row, column);
        scanner = new Scanner(System.in);
        marketController = new MarketControllerImpl(new MarketView(), new MarketModel());
    }

    public MarketController getMarketController() {
        return marketController;
    }

    // get current round
    // when round == 0, then should create new monster
    public int getRound() {
        return round % 4;
    }

    /***
     * get next hero. The order of the result is:
     * hero1 - hero2 - hero3 - null
     * and then repeat
     * Be careful when the hero is died.
     * {@link CharacterController#isDied()}
     * @return
     */
    public HeroController nextHero() {
        if (heroIter == null) {
            heroIter = getTeamController().getHeroControllerList().iterator();
        }

        if (heroIter.hasNext()) {
            return heroIter.next();
        } else {
            // round end, reset
            heroIter = getTeamController().getHeroControllerList().iterator();
            // create new monster
            round++;
            if (getRound() == 0) {
                boardController.show();
                boardController.createNewMonsters();
            }
            return heroIter.next();
        }
    }

    public boolean hasNextHero() {
        return heroIter.hasNext();
    }

    @Override
    protected void initGame() {
        getBoardController().initBoard();
        // init mosnter
        // init monster
        monsterControllerList = new ArrayList<>(3);
        for (Monster monster : new MonsterFactory().randomChoose(3, 1))
            monsterControllerList.add(new MonsterControllerImpl(monster, new MonsterView()));
        boardController.setMonsterList(monsterControllerList);
    }

    @Override
    protected boolean isEnd() {
        for (Position position : boardController.getHeroPositionList()) {
            if (position.getRow() == 0)
                return true;
        }
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
        boardController.setHeroList(teamController.getHeroControllerList());
        context.addState(new WalkState());

        while (!isEnd()) {
            performState();
        }

        boardController.show();
        System.out.println(Text.GAME_WIN);
        System.out.println(Text.THANKS);
    }

    public void performState() {
        context.getState().showPrompt(context);
        String action = null;
        if (!(context.getState() instanceof FightState))
            action = scanner.nextLine();
        context.getState().doAction(context, action);
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
            System.out.println("\nInput number to choose your legend: (Start with 3 heros)");
            id = scanner.next();
            if (Integer.parseInt(id) > 0 && Integer.parseInt(id) < heroList.size()) {
                teamController.addHero(heroList.get(Integer.parseInt(id) - 1));
                teamController.showTeam();
            }
            if (teamController.size() >= 3) {
                // skip /n
                scanner.nextLine();
                break;
            }
        } while (true);
    }
}
