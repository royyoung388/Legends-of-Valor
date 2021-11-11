package model.board;

/**
 * One cell on the board.
 * Memory the mark
 */
public class Cell {
    private Marker mark;

    public Cell() {
        this(new Marker());
    }

    public Cell(Marker mark) {
        this.mark = mark;
    }

    public Marker getMark() {
        return mark;
    }

    public void setMark(Marker mark) {
        this.mark = mark;
    }
}
