package model.board;

// bush
public class BushCell extends Cell{
    public BushCell(Marker mark) {
        super(mark);
        type = 4;
    }

    public BushCell() {
        super();
        type = 4;
    }
}
