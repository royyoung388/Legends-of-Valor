package game;

import model.board.LegendMarker;
import model.board.Marker;
import controller.MarketController;
import controller.MarketControllerImpl;
import factory.HeroFactory;
import model.MarketModel;
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

/**
 * legend game
 */
public class LegendsGame extends RPGGame {
    private Scanner scanner;
    private MarketController marketController;
    // record the game status. 0 for quit.
    private int gameFlag = 1;

    public LegendsGame() {
        this(8, 8);
    }

    public LegendsGame(int row, int column) {
        super(row, column);
        scanner = new Scanner(System.in);
        marketController = new MarketControllerImpl(new MarketView(), new MarketModel());
    }

    private void initBoard() {
        int row = boardController.getRow();
        int column = boardController.getColumn();
        Marker[][] markers = new Marker[row][column];

        // set up nexus
        for (int j = 0; j < row; j++){
            if (j!=2 && j!=5) {
                markers[0][j] = new Marker(LegendMarker.NEXUS);
                boardController.setCell(0,j,LegendMarker.NEXUS);
                markers[7][j] = new Marker(LegendMarker.NEXUS);
                boardController.setCell(7,j,LegendMarker.NEXUS);
            }
        }

        // set up inaccessible
        for (int i = 0; i < row; i++){
            markers[i][2] = new Marker(LegendMarker.INACCESSIBLE);
            boardController.setCell(i,2,LegendMarker.INACCESSIBLE);
            markers[i][5] = new Marker(LegendMarker.INACCESSIBLE);
            boardController.setCell(i,5,LegendMarker.INACCESSIBLE);
        }

        // set up plain, bush, cave, koulou
        for (int i = 1; i < row-1; i++) {
            for (int j = 0; j < column; j++) {
                if (j != 2 && j != 5) {
                    Marker m = Dice.rollMarker(true);
                    markers[i][j] = m;
                    boardController.setCell(i,j,m.getMark());
                }
            }
        }

        // Hero start at (7,0) (7,3) (7,6)
        markers[7][0].setMark(LegendMarker.PLAYER_1);
        markers[7][3].setMark(LegendMarker.PLAYER_2);
        markers[7][6].setMark(LegendMarker.PLAYER_3);
//            boardController.setPlayer(0, 0);

//        // no block for player
//        if (markers[0][1].getMark().equals(LegendMarker.BLOCK)
//                && markers[1][0].getMark().equals(LegendMarker.BLOCK)) {
//            markers[0][1].setMark(LegendMarker.COMMON);
//        }

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
        // only three heroes
        for (int i=0; i<3; i++){
            System.out.println("\nInput number to choose your legend: (press 0 to stop choosing)");
            id = scanner.next();
            if (Integer.parseInt(id) > 0 && Integer.parseInt(id) < heroList.size()) {
                teamController.addHero(heroList.get(Integer.parseInt(id) - 1));
                teamController.showTeam();
            }
        }
    }

    public MarketController getMarketController() {
        return marketController;
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
