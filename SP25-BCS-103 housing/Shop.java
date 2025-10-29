public class Shop{

	private String ID;
	private double rentprice=5000;
	private boolean availability;


//-------------------------------------------------------------------------------------------Constructor
   Shop(String ID,double rentprice,boolean availability){
 	this.ID=ID;
	this.rentprice=rentprice;
	this.availability=availability;
}
   Shop(){}

//-------------------------------------------------------------------------------------------To String
   public String toString(){
	return String.format("%-10s \t %.2f \t %.2f \t %10s",getID(),getRentprice(),PlotSize.COMMERCIAL_SHOP.getPrice(),CheckAvailability(getAvailability()));
}

//-------------------------------------------------------------------------------------------Getter & Setters
 public String getID(){
	return ID;
}
 public double getRentprice(){
	return rentprice;
}
 public boolean getAvailability(){
	return availability;
}

//-------------------------------------------------------------------------------------------Booked & Available Methods
 public boolean Booked(){
      return availability=false;
} 
 public boolean CancelBooking(){
      return availability=true;
} 

//-------------------------------------------------------------------------------------------Show Availability Method
 public String CheckAvailability(boolean a){
	if(a==true)
		 return "Rented Out";
        else
		return "Available";
}

//-------------------------------------------------------------------------------------------Display Method

 public String display(){
	return "\nSHOP ID \t SHOP-RENT \t SHOP-PRICE \t  AVAILABILITY";
 }
}