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
                team, initMonster(team.size(),
                team.getHeroController(0).getLevel()), new FightView(),
                game.getLane());
        fightController.start();
        context.popState();
    }

    @Override
    public void showPrompt(Context context) {
        System.out.println(Text.FIGHT);
    }

    private List<MonsterController> initMonster(int count, int level) {
        List<MonsterController> monsterControllerList = new ArrayList<>(count);
        for (Monster monster : new MonsterFactory().randomChoose(count, level))
            monsterControllerList.add(new MonsterControllerImpl(monster, new MonsterView()));
        return monsterControllerList;
    }
}
