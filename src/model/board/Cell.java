package model.board;

/**
 * One cell on the board.
 * Memory the mark
 */
public class Cell {
    protected Marker mark;

    protected int type;   // 1: nexus, 2: inaccessible, 3: plain, 4: bush, 5: cave, 6: koulou

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

    public int getType(){
        return type;
    }
}
