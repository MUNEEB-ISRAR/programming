public class Shop {

    private String shopId;
    private double rentPrice;
    private boolean available;

    public Shop(String shopId, double rentPrice, boolean available) {
        this.shopId = shopId;
        this.rentPrice = rentPrice;
        this.available = available;
    }

    public Shop() {}

    public void book() { 
	available = false;
    }
    public void cancelBooking() { 
	available = true;
    }
    public boolean isAvailable() { 
	return available;
    }

    public String getAvailabilityStatus() {
       if (available) {
            return "Available";
        } else {
            return "Rented Out";
        }
    }

    public String displayHeader() {
        return "\nSHOP ID\t\tRENT PRICE\tMARKET PRICE\tAVAILABILITY";
    }

    @Override
    public String toString() {
        return String.format("%-10s\t%.2f\t\t%.2f\t\t%-12s",
                shopId,
                rentPrice,
                PlotSize.COMMERCIAL_SHOP.getPrice(),
                getAvailabilityStatus());
    }
}
