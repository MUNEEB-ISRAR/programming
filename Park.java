public class Park {

    private String parkId;
    private double length;
    private double width;
    private PlotShape shape;

    public Park(String parkId, double length, double width) {
        this.parkId = parkId;
        this.length = length;
        this.width = width;
        this.shape = PlotShape.RECTANGLE;
    }

    public double getArea() {
        return length * width;
    }

    public String displayHeader() {
        return "\nPARK ID\t\tSHAPE\t\tAREA (sq yd)";
    }

    @Override
    public String toString() {
        return String.format("%-10s\t%-12s\t%.2f sq yd", parkId, shape, getArea());
    }
}
