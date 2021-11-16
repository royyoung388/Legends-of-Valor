package game;

import controller.MarketController;
import controller.MarketControllerImpl;
import factory.HeroFactory;
import model.MarketModel;
import model.board.LegendMarker;
import model.board.Marker;
import model.hero.Hero;
import state.FightState;
import state.WalkState;
import utils.Dice;
import utils.Text;
import view.MarketView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class LegendsOfValor extends RPGGame {
    private Scanner scanner;
    private MarketController marketController;
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
    }

    private void initBoard() {
        int row = boardController.getRow();
        int column = boardController.getColumn();
        Marker[][] markers = new Marker[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                markers[i][j] = Dice.rollMarker(true);
            }
        }

        // Player start at (0,0)
        markers[0][0].setMark(LegendMarker.PLAYER);
        boardController.setPlayer(0, 0);

        // no block for player
        if (markers[0][1].getMark().equals(LegendMarker.BLOCK)
                && markers[1][0].getMark().equals(LegendMarker.BLOCK)) {
            markers[0][1].setMark(LegendMarker.COMMON);
        }

        boardController.fill(markers);
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
        this.lane = lane;
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

    @Override
    protected void initGame() {
        initBoard();
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
}
