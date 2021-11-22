package model.board;

// Nexus
public class NexusCell extends Cell{
    public NexusCell(Marker mark) {
        super(mark);
        type = 1;
    }

    public NexusCell() {
        super();
        type = 1;
    }
}
