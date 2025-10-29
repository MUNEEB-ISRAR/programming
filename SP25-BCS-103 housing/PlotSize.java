public enum PlotSize{
	RESIDENTIAL_5_MARLA(4000000),
	RESIDENTIAL_10_MARLA(7500000),
	RESIDENTIAL_KANAL(14000000),
	COMMERCIAL_SHOP(3000000),
	COMMERCIAL_OFFICE(5000000),
	PARKING(200000);

        private double price;
	PlotSize(double price){
	this.price=price;
}

	public double getPrice(){
	 return price;
}
}