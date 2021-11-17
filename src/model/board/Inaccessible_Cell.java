package model.board;

// inaccessible cell
public class Inaccessible_Cell extends Cell{
    public Inaccessible_Cell(Marker mark) {
        super(mark);
        type = 2;
    }

    public Inaccessible_Cell() {
        super();
        type = 2;
    }
}
