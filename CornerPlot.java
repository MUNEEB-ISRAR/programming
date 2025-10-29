public class CornerPlot extends Plot {

    public CornerPlot(String id, PlotSize size, double price, boolean available, PlotShape shape, double length, double width) {
        super(id, size, price, available, shape, length, width);
    }

    public CornerPlot(String id, PlotSize size, double price, boolean available, PlotShape shape, double w1, double w2, double d1, double d2) {
        super(id, size, price, available, shape, w1, w2, d1, d2);
    }

    public CornerPlot(String id, PlotSize size, double price, boolean available, PlotShape shape, double front, double back, double depth) {
        super(id, size, price, available, shape, front, back, depth);
    }

    @Override
    public String toString() {
        return "[Corner Plot] " + super.toString();
    }
}
