package controller;

import model.board.LegendMarker;
import model.board.Marker;
import model.board.Cell;
import model.board.Position;
import model.hero.Hero;
import model.monster.Monster;
import utils.Dice;

import java.util.ArrayList;


/***
 * Board controller, control the board
 */
public interface BoardController {
    void fill(Marker[][] markers);

    int getHero_num();

    int getMonster_num();

    Position getHero_positions(int index);

    Position getMonster_positions(int index);

    Marker moveTo(int row, int column);

    Marker getMarker(int row, int column);

    void show();

    int getRow();

    int getColumn();

    int getPRow();

    int getPColumn();

    void setMarker(int row, int column, Marker marker);

    void setPlayer(int row, int column);

    void setCell(int i, int j, String nexus);

    Cell getCell(int row, int col);

    void initBoard();

    ArrayList<Hero> find_fight_hero();

    ArrayList<Monster> find_fight_monster();
//
//    void setMarker(int row, int column, model.board.Marker mark);
//
//    model.board.Marker getMarker(int row, int column);
//
//    int getRow();
//
//    int getColumn();
}

