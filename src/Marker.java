/**
 * map marker
 */
public class Marker {
    private String mark;

    public Marker(String mark) {
        this.mark = mark;
    }

    public Marker() {
        this(LegendMarker.NULL);
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return mark;
    }
}
