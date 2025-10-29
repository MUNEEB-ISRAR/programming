public class CommercialMarket{

          Shop shops[];
          String name;
          Shop s=new Shop();

//Constructor
   	CommercialMarket(String name){
		this.name=name;
		shops=new Shop[15];
		for(int i=0;i<shops.length;i++){
 			shops[i]=new Shop(String.format("Shop-%d",i+1),s.getRentprice(),s.getAvailability());
			}
}
//To String
	public String toString(){
		StringBuilder str=new StringBuilder();                
                str.append(name);
                str.append(s.display());
		for(int i=0;i<shops.length;i++){
			str.append("\n"+shops[i].toString());
			}
                    return str.toString();
}

//Cancel/Booking Methods

public boolean Booking(int shopnumber){
	if (!checkBound(shopnumber)) {
        System.out.println("Invalid Input! Please try again.");
        return true;
    }
	if(shops[shopnumber-1].getAvailability()){
		shops[shopnumber-1].Booked();
		return true;}
	else{
		return false;
}
}

//Check Bound Method

 public boolean checkBound(int shop) {
                       if ((shop-1)<0 || shop>shops.length) {
                        return false;
                        }
                        return true;
                        }
}

