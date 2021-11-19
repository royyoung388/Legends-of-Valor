package view;
import model.BoardModel;
import model.board.Cell;

import java.util.ArrayList;
import java.util.List;

import model.board.Position;
import utils.ColorUtils;

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


    private static String getOuterCellStr(String c){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            str.append(c).append(" - ");
        }
        str.append(c).append("   ");
        return str.toString();
    }

    private static String getInnerCellStr(String component){
        return "| " + component + " |   ";
    }

    private static String getCellComponent(int row, int col, BoardModel board){
        for (int i=0;i<board.getHero_num();i++){
            if (row == board.getHero_positions(i).getRow() && col == board.getHero_positions(i).getColumn()){
                return "H"+(i+1)+"   ";
            }
        }
        for (int i=0;i<board.getMonster_num();i++){
            if (row == board.getMonster_positions(i).getRow() && col == board.getMonster_positions(i).getColumn()){
                return "   "+"M"+(i+1);
            }
        }
        return "     ";
    }

    private static void createInnerCell(Cell[][] map, List<StringBuilder> printableMap, int row, int col, BoardModel board) {
        String component = getCellComponent(row/3, col, board);
        if (map[row/3][col].getType() == 2)
            component = "X X X";
        printableMap.get(row).append(getInnerCellStr(component));
    }

    private static void createOutterCell(Cell[][] map, List<StringBuilder> printableMap, int row, int col) {
        switch (map[row/3][col].getType()){
            case 1:
                printableMap.get(row).append(getOuterCellStr(ColorUtils.BLUE+'N'+ColorUtils.RESET));
                break;
            case 2:
                printableMap.get(row).append(getOuterCellStr(ColorUtils.PURPLE+'P'+ColorUtils.RESET));
                break;
            case 3:
                printableMap.get(row).append(getOuterCellStr(ColorUtils.BLACK+'P'+ColorUtils.RESET));
                break;
            case 4:
                printableMap.get(row).append(getOuterCellStr(ColorUtils.GREEN+'B'+ColorUtils.RESET));
                break;
            case 5:
                printableMap.get(row).append(getOuterCellStr(ColorUtils.YELLOW+'C'+ColorUtils.RESET));
                break;
            case 6:
                printableMap.get(row).append(getOuterCellStr(ColorUtils.RED+'K'+ColorUtils.RESET));
                break;
        }
    }

    public static void show(BoardModel board) {
        int size = 8;

        List<StringBuilder> printableMap = new ArrayList<StringBuilder>();

        for (int row = 0; row < size * 3; row++) {
            printableMap.add(new StringBuilder());
            if ((row / 3) % 2 == 0){
                for (int col = 0; col < size; col++) {
                    if (row % 2 == 0){
                        createOutterCell(board.getCells(), printableMap, row, col);
                    }else{
                        createInnerCell(board.getCells(), printableMap, row, col, board);
                    }

                    if (col == size - 1)
                        printableMap.get(row).append("\n");
                }
            }else{
                for (int col = 0; col < size; col++) {
                    if (row % 2 == 1){
                        createOutterCell(board.getCells(), printableMap, row, col);
                    }else{
                        createInnerCell(board.getCells(), printableMap, row, col, board);
                    }

                    if (col == size - 1)
                        printableMap.get(row).append("\n");
                }
            }

            if (row % 3 == 2)
                printableMap.get(row).append("\n");
        }

        for (int i = 0; i < size * 3; i++) {
            System.out.print(printableMap.get(i));
        }
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
//    public void show(BoardModel board) {
//        System.out.println();
//        // extra one row to show column number.
//        // odd row for board, even row for split line
//        for (int i = -1; i <= board.getRow() * 2; i++) {
//            // show column number
//            if (i == -1) {
//                // column number
//                System.out.print("    ");
//                for (int k = 0; k < board.getRow(); k++)
//                    System.out.printf("%-4s", k + 1);
//                System.out.println();
//                continue;
//            }
//
//            // odd row for board
//            if (i % 2 == 1) {
//                // extra one column to show row number
//                for (int j = -1; j < board.getColumn(); j++)
//                    // row number
//                    if (j == -1)
//                        System.out.printf("%-3d", (i + 1) / 2);
//                    else
//                        System.out.printf("|%-3s", board.getMarker(i / 2, j));
//                System.out.println("|");
//            } else {
//                // even row for split line
//                System.out.print("   ");
//                for (int k = 0; k < board.getRow(); k++)
//                    System.out.print("+---");
//                System.out.println("+");
//            }
//        }
//    }
}
