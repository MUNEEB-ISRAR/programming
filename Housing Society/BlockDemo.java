public class BlockDemo {
    public static void main(String[] args) {
        Park park1 = new Park("P-01", 40, 30);
        Shop shop1 = new Shop("S-01", 6000, true);

        System.out.println("\n=== PARK DETAILS ===");
        System.out.println(park1.displayHeader());
        System.out.println(park1);

        System.out.println("\n=== SHOP DETAILS ===");
        System.out.println(shop1.displayHeader());
        System.out.println(shop1);
    }
}
