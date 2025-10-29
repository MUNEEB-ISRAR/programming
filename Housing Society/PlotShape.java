public enum PlotShape {
    RECTANGLE("Rectangle"),
    TRAPEZOID("Trapezoid"),
    L_SHAPE("L-Shape");

    private final String label;

    PlotShape(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
