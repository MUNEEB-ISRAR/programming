public class SocietyDemo {
    public static void main(String[] args) {

        // Create a Housing Society
        HousingSociety s = new HousingSociety("Lahore Housing Society");
        System.out.println("✅ Society created successfully!");

        //  Print basic info
        s.printSummary();

        //  Display all blocks
        System.out.println("\n========= SOCIETY DETAILS =========");
        System.out.println(s);

        //  Add a new custom block with a specific number of streets
        s.AddBlock(4);
        System.out.println("\n✅ Added new block with 4 streets!");
        s.printSummary();

        //  Book a plot using Block name and Plot ID
        System.out.println("\n========= BOOKING TEST =========");
        boolean booked = s.book("Block-A", "S1-001");
        if (!booked) {
            System.out.println("Plot booking failed (maybe already booked or not found).");
        } else {
            System.out.println("Plot successfully booked in Block-A, Plot S1-001!");
        }

        //  Try cancelling a booking
        System.out.println("\n========= CANCELLATION TEST =========");
        boolean cancelled = s.cancel("Block-A", "S1-001");
        if (cancelled)
            System.out.println("Booking cancelled successfully!");
        else
            System.out.println("Cancellation failed (plot may already be available).");

        //  Find a block by name and display total plots
        System.out.println("\n========= BLOCK SEARCH TEST =========");
        Block found = s.findBlockByName("Block-B");
        if (found != null) {
            System.out.println("Block found: " + found);
            System.out.println("Total Plots in Block-B = " + found.totalPlots());
        } else {
            System.out.println("Block not found!");
        }

        //  Show final summary of the society
        System.out.println("\n========= FINAL SUMMARY =========");
        s.printSummary();

        System.out.println("\n DEMO COMPLETED SUCCESSFULLY!");
    }
}
