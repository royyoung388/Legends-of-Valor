package controller;

import model.board.LegendMarker;
import model.board.Marker;
import model.BoardModel;
import utils.Dice;
import view.BoardView;

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

    @Override
    public void setMarker(int row, int column, Marker marker) {
        boardModel.setMarker(row, column, marker);
    }

    @Override
    public void setPlayer(int row, int column) {
        boardView.setPlayer(row, column);
        boardModel.setMarker(row, column, new Marker(LegendMarker.PLAYER));
    }
}
