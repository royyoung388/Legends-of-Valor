package model.board;

// plain cell
public class Plain_Cell extends Cell{
    public Plain_Cell(Marker mark) {
        super(mark);
        type = 3;
    }

    public Plain_Cell() {
        super();
        type = 3;
    }
}
