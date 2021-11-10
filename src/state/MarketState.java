package state;

import bean.item.Potion;
import controller.MarketController;
import controller.TeamController;
import bean.item.Armor;
import bean.item.Spell;
import bean.item.Weapon;
import game.LegendsGame;
import utils.Config;
import utils.Text;

/**
 * one state of state pattern
 * used for marker. To buy item
 */
public class MarketState extends BaseState {
    @Override
    public void doAction(Context context, String action) {
        super.doAction(context, action);

        action = action.toUpperCase();
        String[] strings = action.split("\\s+");
        LegendsGame legendsGame = (LegendsGame) context.getRpgGame();
        MarketController marketController = legendsGame.getMarketController();
        TeamController teamController = legendsGame.getTeamController();

        if (Integer.parseInt(strings[0]) == Config.QUIT) {
            context.popState();
            return;
        }

        if (Integer.parseInt(strings[0]) == Config.SELL)
            context.addState(new SellState());

        if (strings.length < 2)
            return;

        int index = Integer.parseInt(strings[1]) - 1;
        switch (Integer.parseInt(strings[0])) {
            case Config.WEAPON_ID -> {
                if (teamController.getMoney() >= marketController.getWeaponList().get(index).getCost()) {
                    Weapon weapon = marketController.buyWeapon(index);
                    legendsGame.getTeamController().buyWeapon(weapon);
                }
            }
            case Config.ARMOR_ID -> {
                if (teamController.getMoney() >= marketController.getArmorList().get(index).getCost()) {
                    Armor armor = marketController.buyArmor(index);
                    legendsGame.getTeamController().buyArmor(armor);
                }
            }
            case Config.POTION_ID -> {
                if (teamController.getMoney() >= marketController.getPotionList().get(index).getCost()) {
                    Potion potion = marketController.buyPotion(index);
                    legendsGame.getTeamController().buyPotion(potion);
                }
            }
            case Config.SPELL_ID -> {
                if (teamController.getMoney() >= marketController.getSpellList().get(index).getCost()) {
                    Spell spell = marketController.buySpell(index);
                    legendsGame.getTeamController().buySpell(spell);
                }
            }
            case 0 -> context.popState();
        }
    }

    @Override
    public void showPrompt(Context context) {
        System.out.println(Text.MARKET_TITLE);
        System.out.println(Text.MARKET_TYPE);
//        // wait for 2 seconds
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        LegendsGame game = (LegendsGame) context.getRpgGame();
        game.getMarketController().showAll();
        System.out.println("Your Team Money: " + game.getTeamController().getMoney());
        System.out.println("Input TYPE ID to choose the item: (Input 5 to sell item. Input 0 to quit)");
    }
}
