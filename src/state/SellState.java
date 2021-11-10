package state;

import controller.TeamController;
import game.LegendsGame;
import utils.Config;
import utils.Text;

/**
 * one state of state pattern
 * Used to sell items in market
 */
public class SellState extends BaseState {
    @Override
    public void doAction(Context context, String action) {
        super.doAction(context, action);
        action = action.toUpperCase();
        String[] strings = action.split("\\s+");
        TeamController teamController = context.getRpgGame().getTeamController();

        if (strings[0].equals("0")) {
            context.popState();
            return;
        }

        if (strings.length < 2)
            return;

        int index = Integer.parseInt(strings[1]) - 1;
        switch (Integer.parseInt(strings[0])) {
            case Config.WEAPON_ID -> teamController.sellWeapon(index);
            case Config.ARMOR_ID -> teamController.sellArmor(index);
            case Config.POTION_ID -> teamController.sellPotion(index);
            case Config.SPELL_ID -> teamController.sellSpell(index);
            case 0 -> context.popState();
        }
    }

    @Override
    public void showPrompt(Context context) {
        System.out.println(Text.MARKET_TITLE);
        System.out.println(Text.SELL_TYPE);
//        // wait for 2 seconds
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        LegendsGame game = (LegendsGame) context.getRpgGame();
        game.getTeamController().showItem();
        System.out.println("Your Team Money: " + game.getTeamController().getMoney());
        System.out.println("Input TYPE ID to sell the item: (Input 0 to quit)");
    }
}
