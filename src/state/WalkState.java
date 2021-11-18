package state;

import com.sun.media.jfxmedia.events.PlayerStateEvent;
import factory.HeroFactory;
import model.Character;
import model.board.LegendMarker;
import model.board.Marker;
import controller.BoardController;
import game.RPGGame;
import model.hero.Hero;
import utils.Dice;
import utils.Text;
import model.board.Cell;
/**
 * one state of state pattern
 * used in walking
 */
public class WalkState extends BaseState {
    Hero hero;
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

        Marker newCell = boardController.moveTo(row, column);
        Marker oldCell = boardController.getCell(row, column);

        //Leave the old cell
        switch (oldCell.getMark()) {
            case LegendMarker.BUSH -> hero.setDexterity(hero.getDexterity()*10/11);
            case LegendMarker.KOULOU -> hero.setStrength(hero.getStrength()*10/11);
            case LegendMarker.CAVE -> hero.setAgility(hero.getAgility()*10/11);
        }

        //Enter the new cell
        if (!oldCell.getMark().equals(newCell.getMark())) {
            switch (newCell.getMark()) {
                //when hero enters bush cell
                case LegendMarker.BUSH -> hero.setDexterity(hero.getDexterity()*11/10);
                //when hero enters koulou cell
                case LegendMarker.KOULOU -> hero.setStrength(hero.getStrength()*11/10);
                //when hero enters cave cell
                case LegendMarker.CAVE -> hero.setAgility(hero.getAgility()*11/10);
                //when hero enters market cell
                case LegendMarker.MARKET -> context.addState(new MarketState());
                //when hero encounters a monster
                case LegendMarker.MONSTER -> context.addState(new FightState());
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
