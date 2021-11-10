package factory;

import bean.item.Weapon;
import utils.Dice;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * factory pattern, used to create weapon
 */
public class WeaponFactory {
    private String path;

    public WeaponFactory() {
        path = "config/Weaponry.txt";
    }

    public List<Weapon> readAll(String path) {
        List<Weapon> weapons = new ArrayList<>();

        Iterator<String> iterator = FileUtils.readFile(path).iterator();
        // skip first line
        iterator.next();
        while (iterator.hasNext()) {
            String[] array = iterator.next().split("[\s\t]+");
            weapons.add(new Weapon(array));
        }

        return weapons;
    }

    public List<Weapon> randomChoose(int count) {
        List<Weapon> all = readAll(path);
        List<Weapon> choice = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = Dice.roll(all.size());
            choice.add(all.get(index));
        }
        return choice;
    }
}
