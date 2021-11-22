package model.board;

// cave
public class CaveCell extends Cell{
    public CaveCell(Marker mark) {
        super(mark);
        type = 5;
    }

    public CaveCell() {
        super();
        type = 5;
    }
}
