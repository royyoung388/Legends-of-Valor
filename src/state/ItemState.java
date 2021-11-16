package state;

import game.LegendsOfValor;
import model.item.Armor;
import model.item.Potion;
import model.item.Weapon;
import utils.Config;

import java.util.List;

/**
 * one state of state pattern.
 * Used to show all items
 */
public class ItemState extends BaseState {
    @Override
    public void doAction(Context context, String action) {
        super.doAction(context, action);
        action = action.toUpperCase();
        LegendsOfValor game = (LegendsOfValor) context.getRpgGame();

        String[] s = action.split("[\\s\\t]+");

        if (s[0].equals("0")) {
            context.popState();
            return;
        } else if (s.length < 3)
            return;

        int iteamId = Integer.parseInt(s[1]) - 1;
        int heroId = Integer.parseInt(s[2]) - 1;

        if (iteamId < 0 || heroId < 0 || heroId >= game.getTeamController().size())
            return;

        switch (Integer.parseInt(s[0])) {
            case Config.WEAPON_ID -> {
                List<Weapon> weaponList = game.getTeamController().getWeaponList();
                if (iteamId > weaponList.size())
                    return;
                game.getTeamController().getHeroController(heroId).equipWeapon(weaponList.get(iteamId));
            }
            case Config.ARMOR_ID -> {
                List<Armor> armorList = game.getTeamController().getArmorList();
                if (iteamId > armorList.size())
                    return;
                game.getTeamController().getHeroController(heroId).equipArmor(armorList.get(iteamId));
            }
            case Config.POTION_ID -> {
                List<Potion> potionList = game.getTeamController().getPotionList();
                if (iteamId > potionList.size())
                    return;
                game.getTeamController().getHeroController(heroId).usePotion(potionList.get(iteamId));
            }
        }
    }

    @Override
    public void showPrompt(Context context) {
        LegendsOfValor game = (LegendsOfValor) context.getRpgGame();
        game.getTeamController().showTeam();
        game.getTeamController().showItem();
        System.out.println("Input ItemType ItemID and HeroID to apply the item on hero. (Input 0 to quit)");
    }
}
