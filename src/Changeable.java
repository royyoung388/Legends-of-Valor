/**
 * Change interface. To change attribute
 */
public interface Changeable {
    int HP = 0;
    int MP = 1;
    int STRENGTH = 2;
    int AGILITY = 3;
    int DEXTERITY = 4;
    int MONEY = 5;
    int EXPERIENCE = 6;
    int LEVEL = 7;
    int DAMAGE = 8;
    int DEFENSE = 9;
    int DODGE = 10;

    void change(int attribute, int value);
}
