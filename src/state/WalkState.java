package state;

import model.board.LegendMarker;
import model.board.Marker;
import controller.BoardController;
import game.RPGGame;
import utils.Dice;
import utils.Text;

/**
 * one state of state pattern
 * used in walking
 */
public class WalkState extends BaseState {
    @Override
    public void doAction(Context context, String action) {
        super.doAction(context, action);
        action = action.toUpperCase();
        RPGGame rpgGame = context.getRpgGame();
        BoardController boardController = rpgGame.getBoardController();
        int row = boardController.getPRow();
        int column = boardController.getPColumn();

        switch (action) {
            // move
            case "W" -> row--;
            case "A" -> column--;
            case "S" -> row++;
            case "D" -> column++;
        }

        Marker marker = boardController.moveTo(row, column);
        if (marker != null) {
            switch (marker.getMark()) {
                // market cell
                case LegendMarker.MARKET -> context.addState(new MarketState());
                // common cell
                case "" -> {
                    // 20% probability to encounter a monster
                    int prob = Dice.roll(10);
                    if (prob < 2)
                        context.addState(new FightState());
                }
            }
        }
    }

    @Override
    public void showPrompt(Context context) {
        System.out.println(Text.WALK_CONTROL);
        context.getRpgGame().getBoardController().show();
        System.out.println("\nInput your action:");
    }

}
