package factory;

import bean.hero.Hero;
import bean.hero.Paladin;
import bean.hero.Sorcerer;
import bean.item.Warrior;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * bean.hero.Hero factory, to create bean.hero.Hero class.
 */
public class HeroFactory {
    public final String paladin, sorcerer, warrior;

    public HeroFactory() {
        paladin = "config/Paladins.txt";
        sorcerer = "config/Sorcerers.txt";
        warrior = "config/Warriors.txt";
    }

    public List<Hero> readAll(String path) {
        List<Hero> heroes = new ArrayList<>();
        int type = 0;


        Iterator<String> iterator = FileUtils.readFile(path).iterator();
        // skip first line
        iterator.next();
        while (iterator.hasNext()) {
            String[] array = iterator.next().split("[\\s\\t]+");
            if (path.equals(paladin)) {
                heroes.add(new Paladin(array));
            } else if (path.equals(warrior)) {
                heroes.add(new Warrior(array));
            } else if (path.equals(sorcerer)) {
                heroes.add(new Sorcerer(array));
            }
        }

        return heroes;
    }
}
