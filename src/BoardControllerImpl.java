import java.util.ArrayList;
import java.util.List;

/***
 * Board controller implement.
 */
public class BoardControllerImpl implements BoardController {

    private BoardView boardView;
    private BoardModel boardModel;

    private List<Position> hero_positions;
    private List<Position> monster_positions;
    private List<HeroController> herolist;
    private List<MonsterController> monsterlist;
    private int current_lane;

    private final static int TOP_LANE = 0;
    private final static int MID_LANE = 1;
    private final static int BOT_LANE = 2;

    public BoardControllerImpl(BoardView boardView, BoardModel boardModel) {
        this.boardView = boardView;
        this.boardModel = boardModel;
        hero_positions = new ArrayList<>();
        monster_positions = new ArrayList<>();
        herolist = new ArrayList<>();
        monsterlist = new ArrayList<>();
        current_lane = TOP_LANE;
    }

    // for positions
    public void setHero_positions(int index, Position p) {
        hero_positions.set(index, p);
    }

    public void setMonster_positions(int index, Position p) {
        monster_positions.set(index, p);
    }

    public void addHero_positions(Position p) {
        hero_positions.add(p);
    }

    public void addMonster_positions(Position p) {
        monster_positions.add(p);
    }

    public void removeHero_positions(int index) {
        hero_positions.remove(index);
    }

    public void removeMonster_positions(int index) {
        monster_positions.remove(index);
    }

    public int getHero_num() {
        return hero_positions.size();
    }

    public int getMonster_num() {
        return monster_positions.size();
    }

    /***
     * get position for specific character
     * @param characterController
     * @return null if not found
     */
    @Override
    public Position getPosition(CharacterController characterController) {
        int index = herolist.indexOf(characterController);
        if (index != -1)
            return hero_positions.get(index);
        index = monsterlist.indexOf(characterController);
        if (index != -1)
            return monster_positions.get(index);
        return null;
    }

    @Override
    public List<Position> getHeroPositionList() {
        return hero_positions;
    }

    public Position getHeroPosition(int index) {
        return hero_positions.get(index);
    }

    public Position getMonsterPosition(int index) {
        return monster_positions.get(index);
    }

    @Override
    public void setPosition(CharacterController characterController, Position position) {
        int index = herolist.indexOf(characterController);
        if (index != -1)
            hero_positions.set(index, position);
        index = monsterlist.indexOf(characterController);
        if (index != -1)
            monster_positions.set(index, position);
    }

    // for hero and monster list
    public void setHeroList(List<HeroController> herolist) {
        this.herolist = herolist;
    }

    public void setMonsterList(List<MonsterController> monsterlist) {
        this.monsterlist = monsterlist;
    }

    public void removeHero(int index) {
        this.herolist.remove(index);
        hero_positions.remove(index);
    }

    public void removeMonster(int index) {
        this.monsterlist.remove(index);
        monster_positions.remove(index);
    }

    public HeroController getHero(int index) {
        return herolist.get(index);
    }

    public MonsterController getMonster(int index) {
        return monsterlist.get(index);
    }

    @Override
    public List<MonsterController> getMonsterList() {
        return monsterlist;
    }

    // for current lane
    public void setCurrent_lane(int current_lane) {
        this.current_lane = current_lane % 3;
    }

    public int getCurrent_lane() {
        return current_lane;
    }

    @Override
    public void createNewMonsters() {
        boolean[] hasOneInNexus = new boolean[3];
        for (Position position : monster_positions) {
            if (position.getRow() == 0)
                hasOneInNexus[position.getColumn() / 3] = true;
        }
        for (int i = 0; i < 3; i++) {
            if (!hasOneInNexus[i]) {
                Monster monster = new MonsterFactory().randomChoose(1, herolist.get(0).getLevel()).get(0);
                MonsterController controller = new MonsterControllerImpl(monster, new MonsterView());
                monsterlist.add(controller);
                monster_positions.add(new Position(0, 3 * i + 1));
            }
        }
    }

    @Override
    public boolean teleport(HeroController heroController, int lane) {
        if (lane == current_lane)
            return false;

        // find the row of monster
        int maxRow = 0;
        for (Position position : monster_positions) {
            if (position.getColumn() / 3 != lane)
                continue;
            maxRow = Math.max(position.getRow(), maxRow);
        }

        // no monster, teleport to nexus
        if (maxRow == 0)
            maxRow = 7;
        else
            // in front of the monster
            maxRow++;

        // find the empty column
        // 0 empty left and right, 1 no empty left, 2 no empty right
        int column = 0;
        while (column == 0 && maxRow <= 7) {
            for (Position position : hero_positions) {
                if (position.getColumn() / 3 != lane)
                    continue;
                // find hero at same row
                if (position.getRow() == maxRow)
                    column += (position.getColumn() + 1) % 3;
                // no empty at right and left
                if (column == 3) {
                    maxRow++;
                    column = -1;
                    break;
                }
            }
            if (column == -1)
                column = 0;
            else if (column == 0)
                column = 2;
        }

        // no empty slot
        if (maxRow > 7)
            return false;

        int index = herolist.indexOf(heroController);
        Position position = hero_positions.get(index);
        position.setRow(maxRow);
        position.setColumn(lane * 3 + 2 - column);
        current_lane = lane;
        return true;
    }

    @Override
    public void fill(Marker[][] markers) {
        boardModel.fill(markers);
    }

    /***
     * judge the player can move to a new place.
     * @param row
     * @param column
     * @return the new cell. null if can't
     */
    @Override
    public Cell moveTo(int fromRow, int fromColumn, int row, int column) {
        if (row >= getRow() || row < 0 || column >= getColumn() || column < 0)
            return null;

        // can't move
        if (boardModel.getCell(row, column).getType() == Cell.INACCESSIBLE)
            return null;

        return boardModel.getCell(row, column);
    }

    @Override
    public void show() {
        BoardView.show(boardModel, this);
    }

    @Override
    public int getRow() {
        return boardModel.getRow();
    }

    @Override
    public int getColumn() {
        return boardModel.getColumn();
    }

    @Override
    public int getPRow() {
        return boardView.getPRow();
    }

    @Override
    public int getPColumn() {
        return boardView.getPColumn();
    }

    public void setCell(int row, int column, String type) {
        boardModel.createCell(row, column, type);
    }

    @Override
    public Cell getCell(int row, int col) {
        return boardModel.getCell(row, col);
    }

    @Override
    public void setMarker(int row, int column, Marker marker) {
        boardModel.setMarker(row, column, marker);
    }

    @Override
    public void initBoard() {
        int row = getRow();
        int column = getColumn();

        // set up nexus
        for (int j = 0; j < row; j++) {
            if (j != 2 && j != 5) {
                setCell(0, j, LegendMarker.NEXUS);
                setCell(7, j, LegendMarker.NEXUS);
            }
        }

        // set up inaccessible
        for (int i = 0; i < row; i++) {
            setCell(i, 2, LegendMarker.INACCESSIBLE);
            setCell(i, 5, LegendMarker.INACCESSIBLE);
        }

        // set up plain, bush, cave, koulou
        for (int i = 1; i < row - 1; i++) {
            for (int j = 0; j < column; j++) {
                if (j != 2 && j != 5) {
                    Marker m = Dice.rollMarker(true);
                    setCell(i, j, m.getMark());
                }
            }
        }

        // setup position for heroes
        addHero_positions(new Position(7, 0));
        addHero_positions(new Position(7, 3));
        addHero_positions(new Position(7, 6));

        // setup position for monsters
        addMonster_positions(new Position(0, 1));
        addMonster_positions(new Position(0, 4));
        addMonster_positions(new Position(0, 7));
    }

    @Override
    public ArrayList<HeroController> find_fight_hero() {
        return (ArrayList<HeroController>) find_fight(0);
    }

    @Override
    public ArrayList<MonsterController> find_fight_monster() {
        return (ArrayList<MonsterController>) find_fight(1);
    }

    // find the fight team for heroes and monsters
    public List<? extends CharacterController> find_fight(int type) {
        ArrayList<HeroController> fight_heroes = new ArrayList<>();
        ArrayList<MonsterController> fight_monsters = new ArrayList<>();
        for (int i = 0; i < monster_positions.size(); i++) {
            if (monster_positions.get(i).getColumn() == current_lane * 3 || monster_positions.get(i).getColumn() == (current_lane * 3 + 1)) {
                for (int j = 0; j < hero_positions.size(); j++) {
                    if (is_fight(monster_positions.get(i), hero_positions.get(j))) {
                        fight_heroes.add(herolist.get(j));
                        if (fight_monsters.size() == 0) {
                            fight_monsters.add(monsterlist.get(i));
                        }
                    }
                }
            }
        }
        if (type == 0) {
            // return heroes  type = 0
            return fight_heroes;
        } else {
            // return monsters  type = 1
            return fight_monsters;
        }
    }

    // judge whether hero and monster can fight
    public boolean is_fight(Position h, Position m) {
        if (h.getRow() == m.getRow()) {
            return Math.abs(h.getColumn() - m.getColumn()) == 1;
        } else if (Math.abs(h.getRow() - m.getRow()) == 1) {
            return Math.abs(h.getColumn() - m.getColumn()) <= 1;
        }
        return false;
    }
}
