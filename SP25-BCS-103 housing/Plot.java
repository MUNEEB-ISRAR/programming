public class Plot{
	private PlotSize  size;
	private double  price;
	private PlotShape shape;
        private String ID; 
        private boolean availability=true;
        private double area;
	private double length;
	private double width;
	private double front;
	private double back;
	private double depth;
	private double w1;
	private double w2;
	private double d1;
	private double d2;

 	//RECTANGLE
 	Plot(String ID, PlotSize size, double price, boolean availability,PlotShape shape,double length,double width){
   	this.ID=ID;
	this.size=size;
	this.length=length;
	this.width=width;
	this.price=price;
	this.shape=shape;
	this.availability=availability;
}    
 	//TRAPEZOID
 	Plot(String ID, PlotSize size, double price, boolean availability,PlotShape shape,double front,double back,double depth){
   	this.ID=ID;
	this.size=size;
	this.front=front;
	this.back=back;
	this.depth=depth;
	this.price=price;
	this.shape=shape;
	this.availability=availability;
} 
 	//L-SHAPE
 	Plot(String ID, PlotSize size, double price, boolean availability,PlotShape shape,double w1,double w2,double d1,double d2){
   	this.ID=ID;
	this.size=size;
	this.w1=w1;
	this.w2=w2;
	this.d1=d1;
	this.d2=d2;
	this.price=price;
	this.shape=shape;
	this.availability=availability;
}
	Plot(){}   
	//Getter & Setter
 
 public String getID(){
	return ID;
}
 public boolean getAvailability(){
	return availability;
}
 public PlotShape getshape(){
	return shape;
}
 public PlotSize getsize(){
	return size;
}
 public double getPrice(){
	return price;
}
 public double getArea(){
	return area;
}
 public double getLength(){
	return length;
}
 public double getWidth(){
	return width;
}
 public double getFront(){
	return front;
}
 public double getBack(){
	return back;
}
 public double getW1(){
	return w1;
}
 public double getW2(){
	return w2;
}
 public double getD1(){
	return d1;
}
 public double getD2(){
	return d2;
}

//To String
@Override 
public String toString(){
    	return String.format("%-15s %-15s \t %.2f \t %.2f sq yd %15s",getID(),getsize(),getPrice(),calArea(getshape()),CheckAvailability(getAvailability()));
}

//Booked & Available Methods
 public boolean Booked(){
      return availability=false;
} 
 public boolean CancelBooking(){
      return availability=true;
} 

//Check Availability Methods
 public String CheckAvailability(boolean a){
	if(a==false)
		 return "Booked";
        else
		return "Available";
}
 public String ShowAvailabilitySymbol(boolean a){
	if(a==false)
		 return "B";
        else
		return "A";
}

//Calculate Area Methods
 public double calArea(PlotShape s){
	if(shape==PlotShape.RECTANGLE){return length*width;}
	else if(shape==PlotShape.TRAPEZOID){return ((front+back)/2)*depth;}
	else if(shape==PlotShape.L_SHAPE){return ((w1*d1)+(w2*d2));}
        return 0;
}

//Display Method

 public String display(){
	return"\nPLOT ID \t PLOT-TYPE \t\t PLOT-PRICE \t PLOT-AREA \t AVAILABILITY";
 }