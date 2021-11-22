import java.util.List;
import java.util.Scanner;

/**
 * one state of state pattern
 * used in walking
 */
public class WalkState extends BaseState {
    private HeroController character;
    private Position position;

    @Override
    public void doAction(Context context, String action) {
        super.doAction(context, action);
        action = action.toUpperCase();
        LegendsOfValor game = (LegendsOfValor) context.getRpgGame();
        BoardController boardController = game.getBoardController();

        // hero character, move
        Hero hero = (Hero) character.getCharacter();
        int row = position.getRow();
        int column = position.getColumn();

        switch (action) {
            // move
            case "W" -> {
                row--;
                // judge fight
                List<HeroController> heroControllerList = boardController.find_fight_hero();
                List<MonsterController> monsterControllerList = boardController.find_fight_monster();

                if (heroControllerList.size() > 0 && monsterControllerList.size() > 0) {
                    context.addState(new FightState());
                    return;
                }
            }
            case "A" -> column--;
            case "S" -> row++;
            case "D" -> column++;
            case "B" -> {
                if (row == 7) {
                    // open market
                    context.addState(new MarketState());
                    game.performState();
                } else {
                    row = 7;
                }
            }
            case "T" -> {
                System.out.println("Teleport to lane: (input 0, 1, 2)");
                int lane = Integer.parseInt(new Scanner(System.in).nextLine());
                if (lane < 0 || lane > 2 || !boardController.teleport(character, lane)) {
                    System.out.println("Teleport failed");
                }
                return;
            }
        }

        Cell newCell = boardController.moveTo(position.getRow(), position.getColumn(), row, column);
        Cell oldCell = boardController.getCell(position.getRow(), position.getColumn());

        // can't move, then skip
        if (newCell == null)
            return;

        // change position
        position.setColumn(column);
        position.setRow(row);
        boardController.setPosition(character, position);

        //Leave the old cell
        switch (oldCell.getType()) {
            case Cell.BUSH -> hero.setDexterity(hero.getDexterity() * 10 / 11);
            case Cell.KOULOU -> hero.setStrength(hero.getStrength() * 10 / 11);
            case Cell.CAVE -> hero.setAgility(hero.getAgility() * 10 / 11);
        }

        //Enter the new cell
        switch (newCell.getType()) {
            //when hero enters bush cell
            case Cell.BUSH -> hero.setDexterity(hero.getDexterity() * 11 / 10);
            //when hero enters koulou cell
            case Cell.KOULOU -> hero.setStrength(hero.getStrength() * 11 / 10);
            //when hero enters cave cell
            case Cell.CAVE -> hero.setAgility(hero.getAgility() * 11 / 10);
        }

        // move monster
        if (!game.hasNextHero()) {
            for (MonsterController monsterController : boardController.getMonsterList()) {
                Position pm = boardController.getPosition(monsterController);
                boardController.setCurrent_lane(pm.getColumn() / 3);
                // don't move if fight
                List<MonsterController> fight = boardController.find_fight_monster();
                if (fight.size() > 0 && fight.contains(monsterController))
                    continue;
                pm.setRow(pm.getRow() + 1);
                boardController.setPosition(monsterController, pm);
            }
        }
    }

    @Override
    public void showPrompt(Context context) {
        // get character, position, set lane
        LegendsOfValor game = (LegendsOfValor) context.getRpgGame();
        BoardController boardController = game.getBoardController();
        character = game.nextHero();
        position = boardController.getPosition(character);
        boardController.setCurrent_lane(position.getColumn() / 3);

        System.out.println(Text.WALK_CONTROL);
        context.getRpgGame().getBoardController().show();
        System.out.println("Current position: " + position);
        System.out.println("\nInput your action:");
    }
}
