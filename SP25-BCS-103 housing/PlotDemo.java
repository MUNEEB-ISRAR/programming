public class PlotDemo {

    public static void main(String[] args) {

        // Create Rectangle Plot (RES_5_MARLA)
        Plot p1 = new Plot("1-001", PlotSize.RESIDENTIAL_5_MARLA, 4000000, true, 
                           PlotShape.RECTANGLE, 30, 45);
        System.out.println("----- RECTANGLE PLOT -----");
        System.out.println(p1.display());
        System.out.println(p1);

        // Create Trapezoid Plot (RES_1_KANAL)
        Plot p2 = new Plot("3-007", PlotSize.RESIDENTIAL_KANAL, 14000000, true, 
                           PlotShape.TRAPEZOID, 50, 70, 40);
        System.out.println("\n----- TRAPEZOID PLOT -----");
        System.out.println(p2.display());
        System.out.println(p2);

        // Create L-Shape Plot (COMM_OFFICE)
        Plot p3 = new Plot("5-003", PlotSize.COMMERCIAL_OFFICE, 5000000, true, 
                           PlotShape.L_SHAPE, 20, 10, 15, 10);
        System.out.println("\n----- L-SHAPE PLOT -----");
        System.out.println(p3.display());
        System.out.println(p3);

        // Test Booking and Cancellation
        System.out.println("\n----- BOOKING TEST -----");
        System.out.println("Before booking: " + p1.CheckAvailability(p1.getAvailability()));
        p1.Booked(); // Book the plot
        System.out.println("After booking:  " + p1.CheckAvailability(p1.getAvailability()));
        p1.CancelBooking(); // Cancel booking
        System.out.println("After cancel:   " + p1.CheckAvailability(p1.getAvailability()));

        // Test area calculation separately
        System.out.println("\n----- AREA TEST -----");
        System.out.println("Rectangle area: " + p1.calArea(PlotShape.RECTANGLE));
        System.out.println("Trapezoid area: " + p2.calArea(PlotShape.TRAPEZOID));
        System.out.println("L-Shape area:   " + p3.calArea(PlotShape.L_SHAPE));
    }
}
