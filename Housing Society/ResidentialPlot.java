public class ResidentialPlot extends Plot {

    public ResidentialPlot(String id, PlotSize size, double price, boolean available, PlotShape shape, double length, double width) {
        super(id, size, price, available, shape, length, width);
    }

    @Override
    public String toString() {
        return "[Residential Plot] " + super.toString();
    }
}
