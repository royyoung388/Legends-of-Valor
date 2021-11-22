
/**
 * MVC: view. To show information about monster
 */
public class MonsterView {
    public static String header() {
        return String.format("%20s%10s%10s%10s%10s%10s%10s", "Name", "Type", "Level", "HP", "Damage", "Defense", "Dodge");
    }

    public String show(Monster monster) {
        return String.format("%20s%10s%10s%10s%10s%10s%10s",
                monster.getName(), Monster.TYPE[monster.getType()], monster.getLevel(),
                monster.getHp(), monster.getDamage(), monster.getDefense(), monster.getDodge());
    }
}
