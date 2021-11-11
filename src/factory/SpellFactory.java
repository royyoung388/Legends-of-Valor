package factory;

import model.item.Spell;
import utils.Dice;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * factory pattern, used to create spell from file
 */
public class SpellFactory {
    private String fire, ice, lighting;

    public SpellFactory() {
        fire = "config/FireSpells.txt";
        ice = "config/IceSpells.txt";
        lighting = "config/LightningSpells.txt";
    }

    public List<Spell> readAll(String path) {
        List<Spell> spells = new ArrayList<>();

        Iterator<String> iterator = FileUtils.readFile(path).iterator();
        // skip first line
        iterator.next();
        while (iterator.hasNext()) {
            String[] array = iterator.next().split("[\s\t]+");

            if (path.equals(fire)) {
                spells.add(new Spell(Spell.FIRE, array));
            } else if (path.equals(ice)) {
                spells.add(new Spell(Spell.ICE, array));
            } else if (path.equals(lighting)) {
                spells.add(new Spell(Spell.LIGHTNING, array));
            }
        }

        return spells;
    }

    public List<Spell> randomChoose(int count) {
        List<Spell> choice = new ArrayList<>();
        choice.addAll(randomChoose(fire, count));
        choice.addAll(randomChoose(ice, count));
        choice.addAll(randomChoose(lighting, count));

        return choice;
    }

    private List<Spell> randomChoose(String path, int count) {
        List<Spell> all = readAll(path);
        List<Spell> choice = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = Dice.roll(all.size());
            choice.add(all.get(index));
        }
        return choice;
    }
}
