package controller;

import model.board.Cell;
import model.board.LegendMarker;
import model.board.Marker;
import model.BoardModel;
import model.board.Position;
import utils.Dice;
import view.BoardView;

import java.util.ArrayList;

/***
 * Board controller implement.
 */
public class BoardControllerImpl implements BoardController {

    private BoardView boardView;
    private BoardModel boardModel;


    public BoardControllerImpl(BoardView boardView, BoardModel boardModel) {
        this.boardView = boardView;
        this.boardModel = boardModel;
    }


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
        boardView.show(boardModel);
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
        boardModel.addHero_positions(new Position(7,0));
        boardModel.addHero_positions(new Position(7,3));
        boardModel.addHero_positions(new Position(7,6));

        // setup position for monsters
        boardModel.addMonster_positions(new Position(0,1));
        boardModel.addMonster_positions(new Position(0,4));
        boardModel.addMonster_positions(new Position(0,7));

//        // no block for player
//        if (markers[0][1].getMark().equals(LegendMarker.BLOCK)
//                && markers[1][0].getMark().equals(LegendMarker.BLOCK)) {
//            markers[0][1].setMark(LegendMarker.COMMON);
//        }

        fill(markers);
    }
}
