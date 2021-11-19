package controller;

import model.board.LegendMarker;
import model.board.Marker;
import model.board.Cell;
import utils.Dice;


/***
 * Board controller, control the board
 */
public interface BoardController {
    void fill(Marker[][] markers);

    Marker moveTo(int row, int column);

    Marker getCell(int row, int column);

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
//
//    void setMarker(int row, int column, model.board.Marker mark);
//
//    model.board.Marker getMarker(int row, int column);
//
//    int getRow();
//
//    int getColumn();
}

