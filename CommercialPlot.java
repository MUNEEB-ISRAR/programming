public class CommercialPlot extends Plot {

    public CommercialPlot(String id, PlotSize size, double price, boolean available, PlotShape shape, double length, double width) {
        super(id, size, price, available, shape, length, width);
    }

    @Override
    public String toString() {
        return "[Commercial Plot] " + super.toString();
    }
}
