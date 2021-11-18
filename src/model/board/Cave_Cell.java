package model.board;

// cave
public class Cave_Cell extends Cell{
    public Cave_Cell(Marker mark) {
        super(mark);
        type = 5;
    }

    public Cave_Cell() {
        super();
        type = 5;
    }
}
