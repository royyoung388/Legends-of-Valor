// inaccessible cell
public class InaccessibleCell extends Cell{
    public InaccessibleCell(Marker mark) {
        super(mark);
        type = 2;
    }

    public InaccessibleCell() {
        super();
        type = 2;
    }
}
