package state;

import controller.*;
import factory.MonsterFactory;
import game.LegendsOfValor;
import model.monster.Monster;
import utils.Text;
import view.FightView;
import view.MonsterView;

import java.util.ArrayList;
import java.util.List;

/**
 * one state of state pattern
 * Used for fight.
 */
public class FightState extends BaseState {

    @Override
    public void doAction(Context context, String action) {
        LegendsOfValor game = (LegendsOfValor) context.getRpgGame();
        TeamController team = game.getTeamController();
        FightController fightController = new FightControllerImpl(
                team,
                team.getHeroController(game.getLane()),
                initMonster(1, team.getHeroController(game.getLane()).getLevel()).get(0),
                new FightView());
        fightController.start();
        context.popState();
    }

    @Override
    public void showPrompt(Context context) {
        System.out.println(Text.FIGHT);
    }

    private List<MonsterController> initMonster(int count, int level) {

    }
}
