public class Plot {

    protected String id;
    protected PlotSize size;
    protected double price;
    protected PlotShape shape;
    protected boolean isAvailable;

    protected double length, width, front, back, depth, w1, w2, d1, d2;

    public Plot(String id, PlotSize size, double price, boolean available, PlotShape shape, double length, double width) {
        this.id = id;
        this.size = size;
        this.price = price;
        this.shape = shape;
        this.isAvailable = available;
        this.length = length;
        this.width = width;
    }

    public Plot(String id, PlotSize size, double price, boolean available, PlotShape shape, double front, double back, double depth) {
        this.id = id;
        this.size = size;
        this.price = price;
        this.shape = shape;
        this.isAvailable = available;
        this.front = front;
        this.back = back;
        this.depth = depth;
    }

    public Plot(String id, PlotSize size, double price, boolean available, PlotShape shape, double w1, double w2, double d1, double d2) {
        this.id = id;
        this.size = size;
        this.price = price;
        this.shape = shape;
        this.isAvailable = available;
        this.w1 = w1;
        this.w2 = w2;
        this.d1 = d1;
        this.d2 = d2;
    }

    public Plot() {}

    public String getId() { 
	return id; 
    }
    public PlotSize getSize() {
	 return size; 
    }
    public double getPrice() { 
	return price; 
    }
    public boolean isAvailable() { 
	return isAvailable;
    }

    public void book() { 
	isAvailable = false; 
    }
    public void cancelBooking() { 
	isAvailable = true;
    }

    public double calculateArea() {
        switch (shape) {
            case RECTANGLE: return length * width;
            case TRAPEZOID: return ((front + back) / 2) * depth;
            case L_SHAPE: return (w1 * d1) + (w2 * d2);
            default: return 0;
        }
    }

    public String getAvailabilityStatus() {
       if (isAvailable) {
            return "Available";
        } else {
            return "Booked";
        }
    }

    public String displayHeader() {
        return "\nPLOT ID\t\tPLOT SIZE\tPRICE\t\tAREA (sq yd)\tAVAILABILITY";
    }

    @Override
    public String toString() {
        return String.format("%-10s %-20s %.2f\t%.2f sq yd\t%-10s",
                id, size, price, calculateArea(), getAvailabilityStatus());
    }
}
