package state;

import controller.*;
import game.LegendsOfValor;
import utils.Text;
import view.FightView;

import java.util.List;

/**
 * one state of state pattern
 * Used for fight.
 */
public class FightState extends BaseState {

    @Override
    public void doAction(Context context, String action) {
        LegendsOfValor game = (LegendsOfValor) context.getRpgGame();
        BoardController boardController = game.getBoardController();
        TeamController team = game.getTeamController();
        List<MonsterController> monsterControllerList = boardController.find_fight_monster();

        FightController fightController = new FightControllerImpl(
                team,
                boardController.find_fight_hero(),
                monsterControllerList,
                new FightView());
        fightController.start();
        context.popState();

        // hero win, delete monster
        if (fightController.isHeroWin())
            for (MonsterController monsterController : monsterControllerList) {
                int index = boardController.getMonsterList().indexOf(monsterController);
                boardController.removeMonster(index);
            }
    }

    @Override
    public void showPrompt(Context context) {
        System.out.println(Text.FIGHT);
    }
}
