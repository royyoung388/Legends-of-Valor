package controller;

import model.item.Armor;
import model.item.Potion;
import model.item.Spell;
import model.item.Weapon;
import model.MarketModel;
import view.MarketView;

import java.util.List;

/**
 * market controller. Control the market.
 */
public class MarketControllerImpl implements MarketController {

    private MarketView marketView;
    private MarketModel marketModel;

    public MarketControllerImpl(MarketView marketView, MarketModel marketModel) {
        this.marketView = marketView;
        this.marketModel = marketModel;
    }

    @Override
    public Armor buyArmor(int index) {
        if (index < 0 || index >= marketModel.getArmorList().size())
            return null;
        return (Armor) marketModel.buy(marketModel.getArmorList(), index);
    }

    @Override
    public Weapon buyWeapon(int index) {
        if (index < 0 || index >= marketModel.getWeaponList().size())
            return null;
        return (Weapon) marketModel.buy(marketModel.getWeaponList(), index);
    }

    @Override
    public Potion buyPotion(int index) {
        if (index < 0 || index >= marketModel.getPotionList().size())
            return null;
        return (Potion) marketModel.buy(marketModel.getPotionList(), index);
    }

    @Override
    public Spell buySpell(int index) {
        if (index < 0 || index >= marketModel.getSpellList().size())
            return null;
        return (Spell) marketModel.buy(marketModel.getSpellList(), index);

    }

    @Override
    public void showAll() {
        showWeaponList();
        showArmorList();
        showPotionList();
        showSpellList();
    }

    @Override
    public void showArmorList() {
        marketView.showArmorList(marketModel.getArmorList());
    }

    @Override
    public void showWeaponList() {
        marketView.showWeaponList(marketModel.getWeaponList());
    }

    @Override
    public void showPotionList() {
        marketView.showPotionList(marketModel.getPotionList());
    }

    @Override
    public void showSpellList() {
        marketView.showSpellList(marketModel.getSpellList());
    }

    @Override
    public List<Armor> getArmorList() {
        return marketModel.getArmorList();
    }

    @Override
    public List<Weapon> getWeaponList() {
        return marketModel.getWeaponList();
    }

    @Override
    public List<Potion> getPotionList() {
        return marketModel.getPotionList();
    }

    @Override
    public List<Spell> getSpellList() {
        return marketModel.getSpellList();
    }
}
