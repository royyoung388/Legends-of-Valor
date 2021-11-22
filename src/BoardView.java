import java.util.ArrayList;
import java.util.List;

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

    private static String getCellComponent(int row, int col, BoardControllerImpl board){
        for (int i=0;i<board.getHero_num();i++){
            if (row == board.getHeroPosition(i).getRow() && col == board.getHeroPosition(i).getColumn()){
                return "H"+(i+1)+"   ";
            }
        }
        for (int i=0;i<board.getMonster_num();i++){
            if (row == board.getMonsterPosition(i).getRow() && col == board.getMonsterPosition(i).getColumn()){
                return "   "+"M"+(i+1);
            }
        }
        return "     ";
    }

    private static void createInnerCell(Cell[][] map, List<StringBuilder> printableMap, int row, int col, BoardControllerImpl board) {
        String component = getCellComponent(row/3, col, board);
        if (map[row/3][col].getType() == 2)
            component = "X X X";
        printableMap.get(row).append(getInnerCellStr(component));
    }

    private static void createOutterCell(Cell[][] map, List<StringBuilder> printableMap, int row, int col) {
        switch (map[row/3][col].getType()){
            case Cell.NEXUS:
                printableMap.get(row).append(getOuterCellStr(ColorUtils.BLUE+'N'+ColorUtils.RESET));
                break;
            case Cell.INACCESSIBLE:
                printableMap.get(row).append(getOuterCellStr(ColorUtils.PURPLE+'P'+ColorUtils.RESET));
                break;
            case Cell.PLAIN:
                printableMap.get(row).append(getOuterCellStr(ColorUtils.BLACK+'P'+ColorUtils.RESET));
                break;
            case Cell.BUSH:
                printableMap.get(row).append(getOuterCellStr(ColorUtils.GREEN+'B'+ColorUtils.RESET));
                break;
            case Cell.CAVE:
                printableMap.get(row).append(getOuterCellStr(ColorUtils.YELLOW+'C'+ColorUtils.RESET));
                break;
            case Cell.KOULOU:
                printableMap.get(row).append(getOuterCellStr(ColorUtils.RED+'K'+ColorUtils.RESET));
                break;
        }
    }

    public static void show(BoardModel board, BoardControllerImpl boardController) {
        int size = 8;

        List<StringBuilder> printableMap = new ArrayList<StringBuilder>();

        for (int row = 0; row < size * 3; row++) {
            printableMap.add(new StringBuilder());
            if ((row / 3) % 2 == 0){
                for (int col = 0; col < size; col++) {
                    if (row % 2 == 0){
                        createOutterCell(board.getCells(), printableMap, row, col);
                    }else{
                        createInnerCell(board.getCells(), printableMap, row, col, boardController);
                    }

                    if (col == size - 1)
                        printableMap.get(row).append("\n");
                }
            }else{
                for (int col = 0; col < size; col++) {
                    if (row % 2 == 1){
                        createOutterCell(board.getCells(), printableMap, row, col);
                    }else{
                        createInnerCell(board.getCells(), printableMap, row, col, boardController);
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
}
