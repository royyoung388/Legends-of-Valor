package factory;

import model.monster.Dragon;
import model.monster.Exoskeleton;
import model.monster.Monster;
import model.monster.Spirit;
import utils.Dice;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Factory pattern. Used to create monster from file
 */
public class MonsterFactory {
    private String dragons, exoskeleton, spirit;

    public MonsterFactory() {
        dragons = "config/Dragons.txt";
        exoskeleton = "config/Exoskeletons.txt";
        spirit = "config/Spirits.txt";
    }

    public List<Monster> readAll(String path) {
        List<Monster> monsters = new ArrayList<>();
        int type = 0;

        Iterator<String> iterator = FileUtils.readFile(path).iterator();
        // skip first line
        iterator.next();
        while (iterator.hasNext()) {
            String[] array = iterator.next().split("[\\s\\t]+");
            if (path.equals(dragons)) {
                monsters.add(new Dragon(array));
            } else if (path.equals(exoskeleton)) {
                monsters.add(new Exoskeleton(array));
            } else if (path.equals(spirit)) {
                monsters.add(new Spirit(array));
            }
        }

        return monsters;
    }

    public List<Monster> randomChoose(int count, int level) {
        List<Monster> all = readAll(dragons).stream().filter(m -> m.getLevel() <= level + 1).collect(Collectors.toList());
        all.addAll(readAll(exoskeleton).stream().filter(m -> m.getLevel() <= level + 1).collect(Collectors.toList()));
        all.addAll(readAll(spirit).stream().filter(m -> m.getLevel() <= level + 1).collect(Collectors.toList()));

        List<Monster> choice = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = Dice.roll(all.size());
            choice.add(all.get(index));
        }
        return choice;
    }
}
