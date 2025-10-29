public enum PlotSize {
    RESIDENTIAL_5_MARLA("5 Marla", 2500000),
    RESIDENTIAL_10_MARLA("10 Marla", 4500000),
    RESIDENTIAL_1_KANAL("1 Kanal", 8500000),
    COMMERCIAL_SHOP("Commercial Shop", 5000000),
    COMMERCIAL_OFFICE("Commercial Office", 6500000);

    private final String label;
    private final double price;

    PlotSize(String label, double price) {
        this.label = label;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return label;
    }
}
