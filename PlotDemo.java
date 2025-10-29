public class PlotDemo {
    public static void main(String[] args) {
        Plot p1 = new ResidentialPlot("R-01", PlotSize.RESIDENTIAL_5_MARLA, 3500000, true, PlotShape.RECTANGLE, 10, 15);
        CornerPlot c1 = new CornerPlot("CR-02", PlotSize.RESIDENTIAL_10_MARLA, 5500000, true, PlotShape.L_SHAPE, 12, 8, 6, 6);
        CommercialPlot com1 = new CommercialPlot("C-01", PlotSize.COMMERCIAL_OFFICE, 8000000, false, PlotShape.TRAPEZOID, 20, 15);

        System.out.println("\n=== PLOT DETAILS ===");
        System.out.println(p1.displayHeader());
        System.out.println(p1);
        System.out.println(c1);
        System.out.println(com1);

        p1.book();
        System.out.println("\nAfter booking Plot 1:");
        System.out.println(p1);
    }
}
