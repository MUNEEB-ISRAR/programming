public class Park{
	private String ID;
	private double area;
	private PlotShape shape;
	private double length;
	private double width;

//Constructor

	Park(String ID,double length,double width){
		this.ID=ID;
		this.shape=PlotShape.RECTANGLE;
		this.length=length;
		this.width=width;	
}
Park(){}

//Getter & Setter

public String getID(){
	return ID;
}
public double getArea(){
	return length*width;
}

//To String

	public String toString(){
		return String.format("%-10s \t %-10s \t %.2fsq Yd",getID(),shape,getArea());
}
//Display Method

 public String display(){
	return"\nPARK ID \t PARK-SHAPE \t PARK-AREA";
 }
}