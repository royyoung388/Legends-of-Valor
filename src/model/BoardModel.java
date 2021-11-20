package model;

import model.board.*;

import java.util.ArrayList;

/***
 * board.Board, made by cells.
 * set the board size, and record the chess mark in the board
 */
public class BoardModel {
    private final Cell[][] cells;
    private int row, column;
    private ArrayList<Position> hero_positions;
    private ArrayList<Position> monster_positions;

    public BoardModel(int row, int column) {
        cells = new Cell[row][column];
//        for (int i = 0; i < row; i++)
//            for (int j = 0; j < column; j++)
//                cells[i][j] = new Cell();
        hero_positions = new ArrayList<Position>();
        monster_positions = new ArrayList<Position>();
        this.row = row;
        this.column = column;
    }

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


    public void fill() {
        fill(new Marker());
    }

    public void fill(Marker mark) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                cells[i][j].setMark(mark);
            }
        }
    }

    public void fill(Marker[][] marks) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                cells[i][j].setMark(marks[i][j]);
            }
        }
    }

    public void setMarker(int row, int column, Marker mark) {
        cells[row][column].setMark(mark);
    }

    public Marker getMarker(int row, int column) {
        return cells[row][column].getMark();
    }

    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

    public Cell[][] getCells(){
        return cells;
    }

    // create the cell for cells[row,column]
    public void createCell(int row, int column, String str) {
        switch (str){
            case LegendMarker.NEXUS:
                cells[row][column] = new Nexus_Cell();
                break;
            case LegendMarker.INACCESSIBLE:
                cells[row][column] = new Inaccessible_Cell();
                break;
            case LegendMarker.PLAIN:
                cells[row][column] = new Plain_Cell();
                break;
            case LegendMarker.BUSH:
                cells[row][column] = new Bush_Cell();
                break;
            case LegendMarker.CAVE:
                cells[row][column] = new Cave_Cell();
                break;
            case LegendMarker.KOULOU:
                cells[row][column] = new Koulou_Cell();
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    /***
     * show board, like this
     *     1  2  3
     *    +--+--+--+
     * 1  |X |X |  |
     *    +--+--+--+
     * 2  |O |  |  |
     *    +--+--+--+
     * 3  |X |O |O |
     *    +--+--+--+
     */
    public void show() {
        // extra one row to show column number.
        // odd row for board, even row for split line
        for (int i = -1; i <= row * 2; i++) {
            // show column number
            if (i == -1) {
                // column number
                System.out.print("    ");
                for (int k = 0; k < row; k++)
                    System.out.printf("%-3s", k + 1);
                System.out.println();
                continue;
            }

            // odd row for board
            if (i % 2 == 1) {
                // extra one column to show row number
                for (int j = -1; j < column; j++)
                    // row number
                    if (j == -1)
                        System.out.printf("%-3d", (i + 1) / 2);
                    else
                        System.out.printf("|%-2s", cells[i / 2][j].getMark());
                System.out.println("|");
            } else {
                // even row for split line
                System.out.print("   ");
                for (int k = 0; k < row; k++)
                    System.out.print("+--");
                System.out.println("+");
            }
        }
    }
}
