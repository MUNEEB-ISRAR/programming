public class CornerPlot extends Plot {

//-------------------------------------------------------------------------------------------Constructor

    CornerPlot(String ID, PlotSize size, double price, boolean availability,PlotShape shape,double length,double width){
        super(ID,size,price,availability,shape,length,width);
    }
    CornerPlot(String ID, PlotSize size, double price, boolean availability,PlotShape shape,double w1,double w2,double d1,double d2){
        super(ID,size,price,availability,shape,w1,w2,d1,d2);
    }
    CornerPlot(String ID, PlotSize size, double price, boolean availability,PlotShape shape,double front,double back,double depth){
        super(ID,size,price,availability,shape,front,back,depth);
    }

//-------------------------------------------------------------------------------------------TO String

@Override 
public String toString(){
    	return String.format("%-15s %-15s \t %.2f \t %.2f sq yd %15s",getID(),getsize(),getPrice(),calArea(getshape()),CheckAvailability(getAvailability()));
}
}