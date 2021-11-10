package model;

import bean.board.Cell;
import bean.board.Marker;

/***
 * board.Board, made by cells.
 * set the board size, and record the chess mark in the board
 */
public class BoardModel {
    private final Cell[][] cells;
    private int row, column;

    public BoardModel(int row, int column) {
        cells = new Cell[row][column];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                cells[i][j] = new Cell();

        this.row = row;
        this.column = column;
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
