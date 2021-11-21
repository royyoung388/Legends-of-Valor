package controller;

import model.board.Cell;
import model.board.Marker;
import model.board.Position;

import java.util.ArrayList;
import java.util.List;


/***
 * Board controller, control the board
 */
public interface BoardController {
    void setCurrent_lane(int current_lane);

    int getCurrent_lane();

    // create new monster for every lane
    void createNewMonsters();

    // teleport to other lane
    boolean teleport(HeroController heroController, int lane);

    void setHeroList(List<HeroController> herolist);

    void setMonsterList(List<MonsterController> monsterlist);

    void removeHero(int index);

    void removeMonster(int index);

    HeroController getHero(int index);

    MonsterController getMonster(int index);

    List<MonsterController> getMonsterList();

    void fill(Marker[][] markers);

    int getHero_num();

    int getMonster_num();

    Position getPosition(CharacterController characterController);

    List<Position> getHeroPositionList();

    Position getHeroPosition(int index);

    Position getMonsterPosition(int index);

    void setPosition(CharacterController characterController, Position position);

    Cell moveTo(int fromRow, int fromColumn, int row, int column);

    void show();

    int getRow();

    int getColumn();

    int getPRow();

    int getPColumn();

    void setMarker(int row, int column, Marker marker);

    void setCell(int i, int j, String nexus);

    Cell getCell(int row, int col);

    void initBoard();

    ArrayList<HeroController> find_fight_hero();

    ArrayList<MonsterController> find_fight_monster();
//
//    void setMarker(int row, int column, model.board.Marker mark);
//
//    model.board.Marker getMarker(int row, int column);
//
//    int getRow();
//
//    int getColumn();
}

