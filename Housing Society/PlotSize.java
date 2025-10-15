public enum PlotSize{
5_MARLA(4,000,000),
10_MARLA(7,500,000),
1_KANAL(14,000,000),
COMMERCIAL_SHOP(3,000,000),
COMMERCIAL_OFFICE(5,000,000),
PARKING(200,000);
private double price;
PlotSize (double price){
this.price=price;
}
public double getprice(){
return price;
}

