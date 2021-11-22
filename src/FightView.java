import java.util.List;
import java.util.Scanner;

/**
 * MVC: view. Used to show fight information, log, and so on.
 */
public class FightView {
    public static final int QUIT = 0;
    public static final int ATTACK = 1;
    public static final int CAST = 2;
    public static final int POTION = 3;
    public static final int WEAPON = 4;
    public static final int ARMOR = 5;

    private StringBuilder stringBuilder;
    private Scanner scanner;

    public FightView() {
        this.stringBuilder = new StringBuilder();
        this.scanner = new Scanner(System.in);
    }

    public void fightStart() {
        stringBuilder.append("++++++++++++ Fight Log ++++++++++++").append("\n");
    }

    public int chooseAction(CharacterController characterController) {
        int choice = 0;
        do {
            System.out.println(characterController.getCharacter().getName());
            System.out.println("Input Your Action: (1: Attack. 2: Cast Spell. 3: Use potion. 4: Change weapon. 5: Change Armor.): ");
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 5);
        return choice;
    }

    public int choose(int bound) {
        int choice = 0;
        do {
            System.out.println("Input the ID: (Input 0 to quit.)");
            choice = scanner.nextInt();
        } while (choice < 0 || choice > bound);
        return choice;
    }

    public void dodgeLog(Character attacker, Character defender) {
        stringBuilder.append(" - ").append(defender.getName()).append(ColorUtils.GREEN + " dodges " + ColorUtils.RESET)
                .append(attacker.getName()).append("'s attack\n");
    }

    public void attackLog(Character attacker, Character defender, int damage) {
        stringBuilder.append(" + ").append(attacker.getName()).append(ColorUtils.RED + " |attack| " + ColorUtils.RESET)
                .append(defender.getName()).append(". Damage: ").append(damage).append("\n");
    }

    public void castLog(Character attacker, Character defender, String name, int damage) {
        stringBuilder.append(" + ").append(attacker.getName()).append(ColorUtils.BLUE + " |cast ")
                .append(name).append(" to| " + ColorUtils.RESET)
                .append(defender.getName()).append(". Damage: ").append(damage).append("\n");
    }

    public void itemLog(Character character, String name) {
        stringBuilder.append(" * ").append(character.getName()).append(ColorUtils.YELLOW + " |Used Item| " + ColorUtils.RESET)
                .append(name).append("\n");
    }

    public void regainLog(Character character, int hp, int mp) {
        stringBuilder.append(character.getName()).append(" regained ").append(hp)
                .append(" HP and ").append(mp).append(" MP\n");
    }

    public void levelUp(Character character) {
        stringBuilder.append("*** ").append(character.getName()).append(" Level Up !!!\n");
    }

    public void showEnemy(CharacterController characterController) {
        System.out.println("--------- ENEMY ----------");
        System.out.println(MonsterView.header());
        characterController.show();
        System.out.println("--------------------------");
    }

    public void showResult(boolean win) {
        if (win) {
            System.out.println(Text.FIGHT_WIN);
        } else {
            System.out.println(Text.FIGHT_LOSE);
        }
    }

    public void showLog() {
        System.out.println(stringBuilder.toString());
        System.out.println("+++++++++++++++++++++++++++++++++");
    }

    public void showInformation(List<HeroController> heroControllerList, List<MonsterController> monsterControllerList) {
        // show hero
        System.out.println("===== Your Team =====");
        System.out.println("   " + HeroView.header());
        for (int i = 0; i < heroControllerList.size(); i++) {
            if (heroControllerList.get(i).isDied())
                continue;
            System.out.printf("%3d%s\n", i + 1, heroControllerList.get(i));
        }
        System.out.println("=========================\n");

        // show monster
        if (monsterControllerList != null) {
            System.out.println("------ Monster Team ------");
            System.out.println("   " + MonsterView.header());
            for (int i = 0; i < monsterControllerList.size(); i++) {
                if (monsterControllerList.get(i).isDied())
                    continue;
                System.out.printf("%3d%s\n", i + 1, monsterControllerList.get(i));
            }
            System.out.println("----------------------------\n");
        }
    }

    public void showInformation(List<HeroController> heroControllerList) {
        showInformation(heroControllerList, null);
    }
}
