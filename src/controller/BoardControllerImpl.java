package controller;

import model.Character;
import model.board.Cell;
import model.board.LegendMarker;
import model.board.Marker;
import model.BoardModel;
import model.board.Position;
import model.hero.Hero;
import model.monster.Monster;
import utils.Dice;
import view.BoardView;

import java.util.ArrayList;
import java.util.List;

/***
 * Board controller implement.
 */
public class BoardControllerImpl implements BoardController {

    private BoardView boardView;
    private BoardModel boardModel;

    private ArrayList<Position> hero_positions;
    private ArrayList<Position> monster_positions;
    private ArrayList<Hero> herolist;
    private ArrayList<Monster> monsterlist;
    private int current_lane;

    private final static int TOP_LANE = 0;
    private final static int MID_LANE = 1;
    private final static int BOT_LANE = 2;

    public BoardControllerImpl(BoardView boardView, BoardModel boardModel) {
        this.boardView = boardView;
        this.boardModel = boardModel;
        hero_positions = new ArrayList<Position>();
        monster_positions = new ArrayList<Position>();
        herolist = new ArrayList<Hero>();
        monsterlist = new ArrayList<Monster>();
        current_lane = TOP_LANE;
    }

    // for positions
    public void setHero_positions(int index, Position p){
        hero_positions.set(index, p);
    }

    public void setMonster_positions(int index, Position p){
        monster_positions.set(index, p);
    }

    public void addHero_positions(Position p){
        hero_positions.add(p);
    }

    public void addMonster_positions(Position p){
        monster_positions.add(p);
    }

    public void removeHero_positions(int index) {hero_positions.remove(index);}

    public void removeMonster_positions(int index) {monster_positions.remove(index);}

    public int getHero_num(){
        return hero_positions.size();
    }

    public int getMonster_num(){
        return monster_positions.size();
    }

    public Position getHero_positions(int index) {
        return hero_positions.get(index);
    }

    public Position getMonster_positions(int index){
        return monster_positions.get(index);
    }

    // for hero and monster list
    public void setHerolist(ArrayList<Hero> herolist){this.herolist = herolist;}

    public void setMonsterlist(ArrayList<Monster> monsterlist){this.monsterlist = monsterlist;}

    public void removeHerolist(int index){this.herolist.remove(index);}

    public void removeMonsterlist(int index){this.monsterlist.remove(index);}

    public Hero getHerolist(int index){return herolist.get(index);}

    public Monster getMonsterlist(int index){return monsterlist.get(index);}

    // for current lane
    public void setCurrent_lane(int current_lane){this.current_lane = current_lane;}

    public int getCurrent_lane(){return current_lane;}

    @Override
    public void fill(Marker[][] markers) {
        boardModel.fill(markers);
    }

    // move player to a new place
    @Override
    public Marker moveTo(int row, int column) {
        if (row >= getRow() || row < 0 || column >= getColumn() || column < 0)
            return null;

        if (boardModel.getMarker(row, column).getMark().equals(LegendMarker.BLOCK))
            return null;

        Marker marker = boardModel.getMarker(row, column);
        // create new marker for old cell
        boardModel.setMarker(getPRow(), getPColumn(), Dice.rollMarker(false));
        // move player
        boardModel.setMarker(row, column, new Marker(LegendMarker.PLAYER));
        boardView.setPlayer(row, column);
        return marker;
    }

    @Override
    public Marker getMarker(int row, int column) {
        return null;
    }

    @Override
    public void show() {
        boardView.show(boardModel,this);
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

    public void setCell(int row, int column, String str){boardModel.createCell(row,column,str);}

    @Override
    public Cell getCell(int row, int col) {
        return boardModel.getCell(row,col);
    }

    @Override
    public void setMarker(int row, int column, Marker marker) {
        boardModel.setMarker(row, column, marker);
    }

    @Override
    public void setPlayer(int row, int column) {
        boardView.setPlayer(row, column);
        boardModel.setMarker(row, column, new Marker(LegendMarker.PLAYER));
    }

    public void initBoard() {
        int row = getRow();
        int column = getColumn();
        Marker[][] markers = new Marker[row][column];

        // set up nexus
        for (int j = 0; j < row; j++){
            if (j!=2 && j!=5) {
                markers[0][j] = new Marker(LegendMarker.NEXUS);
                setCell(0,j,LegendMarker.NEXUS);
                markers[7][j] = new Marker(LegendMarker.NEXUS);
                setCell(7,j,LegendMarker.NEXUS);
            }
        }

        // set up inaccessible
        for (int i = 0; i < row; i++){
            markers[i][2] = new Marker(LegendMarker.INACCESSIBLE);
            setCell(i,2,LegendMarker.INACCESSIBLE);
            markers[i][5] = new Marker(LegendMarker.INACCESSIBLE);
            setCell(i,5,LegendMarker.INACCESSIBLE);
        }

        // set up plain, bush, cave, koulou
        for (int i = 1; i < row-1; i++) {
            for (int j = 0; j < column; j++) {
                if (j != 2 && j != 5) {
                    Marker m = Dice.rollMarker(true);
                    markers[i][j] = m;
                    setCell(i,j,m.getMark());
                }
            }
        }

        // Hero start at (7,0) (7,3) (7,6)
        markers[7][0].setMark(LegendMarker.PLAYER_1);
        markers[7][3].setMark(LegendMarker.PLAYER_2);
        markers[7][6].setMark(LegendMarker.PLAYER_3);
//            boardController.setPlayer(0, 0);

        // setup position for heroes
        addHero_positions(new Position(7,0));
        addHero_positions(new Position(7,3));
        addHero_positions(new Position(7,6));

        // setup position for monsters
        addMonster_positions(new Position(0,1));
        addMonster_positions(new Position(0,4));
        addMonster_positions(new Position(0,7));

//        // no block for player
//        if (markers[0][1].getMark().equals(LegendMarker.BLOCK)
//                && markers[1][0].getMark().equals(LegendMarker.BLOCK)) {
//            markers[0][1].setMark(LegendMarker.COMMON);
//        }

        fill(markers);
    }

    @Override
    public ArrayList<Hero> find_fight_hero() {
        return (ArrayList<Hero>) find_fight(0);
    }

    @Override
    public ArrayList<Monster> find_fight_monster() {
        return (ArrayList<Monster>) find_fight(1);
    }

    // find the fight team for heroes and monsters
    public List<? extends Character> find_fight(int type){
        ArrayList<Hero> fight_heroes = new ArrayList<Hero>();
        ArrayList<Monster> fight_monsters = new ArrayList<Monster>();
        for (int i=0;i<monster_positions.size();i++){
            if (monster_positions.get(i).getColumn()==current_lane*3 || monster_positions.get(i).getColumn()==(current_lane*3+1)){
                for (int j=0;j<hero_positions.size();j++){
                    if (is_fight(monster_positions.get(i),hero_positions.get(j))){
                        fight_heroes.add(herolist.get(j));
                        if (fight_monsters.size()==0){
                            fight_monsters.add(monsterlist.get(i));
                        }
                    }
                }
            }
        }
        if (type==0){
            // return heroes  type = 0
            return fight_heroes;
        }else{
            // return monsters  type = 1
            return fight_monsters;
        }
    }

    // judge whether hero and monster can fight
    public boolean is_fight(Position h, Position m){
        if (h.getRow() == m.getRow()){
            if (Math.abs(h.getColumn()-m.getColumn())==1){
                return true;
            }
        }else if (Math.abs(h.getRow()-m.getRow())==1){
            if (Math.abs(h.getColumn()-m.getColumn())<=1){
                return true;
            }
        }
        return false;
    }
}
