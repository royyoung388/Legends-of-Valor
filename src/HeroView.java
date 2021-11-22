/**
 * show hero information
 */
public class HeroView {
    public static String header() {
//        return String.format("%20s%10s%10s%10s%10s%10s%10s%10s%10s", "Name", "Type", "Level", "Hp", "Mana", "Strength", "Agility", "Dexterity", "Exp");
        return String.format("%20s%10s%10s%10s%10s%10s%10s%10s%10s%10s%10s%10s%10s",
                "Name", "Type", "Level", "HP", "Mana", "Damage", "Defense", "Dodge", "Strength", "Agility", "Dexterity", "Money", "Exp");
    }

    public String show(Hero hero, Armor armor, Weapon weapon) {
//        StringBuilder stringBuilder = new StringBuilder(
//                String.format("%20s%10s%10d%10d%10d%10d%10d%10d%10d",
//                        hero.getName(), Hero.TYPE[hero.getType()], hero.getLevel(), hero.getHp(), hero.getMana(),
//                        hero.getStrength(), hero.getAgility(), hero.getDexterity(), hero.getExperience()));
        StringBuilder stringBuilder = new StringBuilder(hero.toString());
        if (armor.getName() != null || weapon.getName() != null)
            stringBuilder.append("\n\t EQUIPMENT: ");
        if (weapon.getName() != null)
            stringBuilder.append("Weapon: ").append(weapon.getName()).append("\t");
        if (armor.getName() != null)
            stringBuilder.append("Armor: ").append(armor.getName()).append("\t");

        return stringBuilder.toString();
    }
}
