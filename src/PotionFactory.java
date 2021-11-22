import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Factory pattern. Used to create potion from file
 */
public class PotionFactory {
    private String path;

    public PotionFactory() {
        path = "config/Potions.txt";
    }

    public List<Potion> readAll() {
        List<Potion> potions = new ArrayList<>();

        Iterator<String> iterator = FileUtils.readFile(path).iterator();
        // skip first line
        iterator.next();
        while (iterator.hasNext()) {
            String[] array = iterator.next().split("[\\s\\t]+");
            potions.add(new Potion(array));
        }

        return potions;
    }

    public List<Potion> randomChoose(int count) {
        List<Potion> all = readAll();
        List<Potion> choice = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = Dice.roll(all.size());
            choice.add(all.get(index));
        }
        return choice;
    }
}
