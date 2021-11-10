package view;

import model.BoardModel;

/***
 * MVC: view, used to show board
 */
public class BoardView {
    private int pRow, pColumn;

    public void setPlayer(int pRow, int pColumn) {
        this.pRow = pRow;
        this.pColumn = pColumn;
    }

    public int getPRow() {
        return pRow;
    }

    public int getPColumn() {
        return pColumn;
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
    public void show(BoardModel board) {
        System.out.println();
        // extra one row to show column number.
        // odd row for board, even row for split line
        for (int i = -1; i <= board.getRow() * 2; i++) {
            // show column number
            if (i == -1) {
                // column number
                System.out.print("    ");
                for (int k = 0; k < board.getRow(); k++)
                    System.out.printf("%-4s", k + 1);
                System.out.println();
                continue;
            }

            // odd row for board
            if (i % 2 == 1) {
                // extra one column to show row number
                for (int j = -1; j < board.getColumn(); j++)
                    // row number
                    if (j == -1)
                        System.out.printf("%-3d", (i + 1) / 2);
                    else
                        System.out.printf("|%-3s", board.getMarker(i / 2, j));
                System.out.println("|");
            } else {
                // even row for split line
                System.out.print("   ");
                for (int k = 0; k < board.getRow(); k++)
                    System.out.print("+---");
                System.out.println("+");
            }
        }
    }
}
