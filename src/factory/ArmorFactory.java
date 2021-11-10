package factory;

import bean.item.Armor;
import utils.Dice;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***
 * bean.item.Armor factory, to create armor from file
 */
public class ArmorFactory {
    private String path;

    public ArmorFactory() {
        path = "config/Armory.txt";
    }

    public List<Armor> readAll(String path) {
        List<Armor> armories = new ArrayList<>();

        Iterator<String> iterator = FileUtils.readFile(path).iterator();
        // skip first line
        iterator.next();
        while (iterator.hasNext()) {
            String[] array = iterator.next().split("[\\s\\t]+");
            armories.add(new Armor(array));
        }

        return armories;
    }

    public List<Armor> randomChoose(int count) {
        List<Armor> all = readAll(path);
        List<Armor> choice = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = Dice.roll(all.size());
            choice.add(all.get(index));
        }
        return choice;
    }
}
