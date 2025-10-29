import java.util.Random;
public class Block{
	
	 String blockname;
	private Plot plots[][];
	Plot p=new Plot();
	Park b=new Park();
	CommercialMarket c=new CommercialMarket("MARKET...");
	private Park parks[];
        private int streetcount;

//-------------------------------------------------------------------------------------------Constructor
  Block(String blockname){
	this.blockname=blockname;

	parks=new Park[2];                                         //for parks
	for(int i=0;i<parks.length;i++){
		parks[i]=new Park(String.format("Park-%d",i+1),25,25);
}
	
	plots=new Plot[5][];                                       //for plots
	for(int i=0;i<plots.length;i++){
		plots[i]=new Plot[10+i];
	           for(int j=0;j<plots[i].length;j++){
			plotdeciding(i,j);
}
}
}
  Block(String blockname,int streetcount){
	this.blockname=blockname;
	this.streetcount=streetcount;

	parks=new Park[2];                                         //for parks
	for(int i=0;i<parks.length;i++){
		parks[i]=new Park(String.format("Park-%d",i+1),25,25);
}
	
	plots=new Plot[streetcount][];                                       //for plots
	for(int i=0;i<plots.length;i++){
		plots[i]=new Plot[10+i];
	           for(int j=0;j<plots[i].length;j++){
			plotdeciding(i,j);
}
}
}
 Block(){}
//-------------------------------------------------------------------------------------------To String
  public String toString(){
	StringBuilder str=new StringBuilder();
	str.append(String.format("\n%40s",blockname));
	for(int i=0;i<plots.length;i++){
              str.append(String.format("\n\nStreet:%d\n",i+1));
	str.append(p.display());
		for(int j=0;j<plots[i].length;j++){
			str.append("\n"+plots[i][j].toString());
}
}
        str.append("\n\n-------------AMENETIES-------------\n\n");
	str.append(c);
	str.append("\n\nPARKS..");
        str.append(b.display());
        for(int i=0;i<parks.length;i++){
		str.append("\n"+parks[i].toString());
}
	
		return str.toString();
}

//-------------------------------------------------------------------------------------------PLot Deciding Method
  public void plotdeciding(int row,int col){
        PlotSize size[]={
			PlotSize.RESIDENTIAL_5_MARLA,
			PlotSize.RESIDENTIAL_10_MARLA,
			PlotSize.RESIDENTIAL_KANAL,
			PlotSize.COMMERCIAL_SHOP,
			PlotSize.COMMERCIAL_OFFICE,
			PlotSize.PARKING};
	String plotID=String.format("S%d-%03d",row+1,col+1);
        if(row==0){                                                                                                            //for 5 Marla Rectangle
	   	plots[row][col]=new Plot(plotID,size[row],plotprice(size[row]),p.getAvailability(),PlotShape.RECTANGLE,10,15);
           			}
        else if(row==1){                                                                                                       //for 10 Marla Rectangle
	   	plots[row][col]=new Plot(plotID,size[row],plotprice(size[row]),p.getAvailability(),PlotShape.RECTANGLE,20,15);
           			}
        else if(row==2){                                                                                                       //for 1 kanal Trapezoid
	   	plots[row][col]=new Plot(plotID,size[row],plotprice(size[row]),p.getAvailability(),PlotShape.TRAPEZOID,40,40,15);
           			}
        else if(row==3){                                                                                                       //for Shop
	   	plots[row][col]=new Plot(plotID,size[row],plotprice(size[row]),p.getAvailability(),PlotShape.RECTANGLE,10,8);
           			}
        else if(row==4){                                                                                                       //for Office
	   	plots[row][col]=new Plot(plotID,size[row],plotprice(size[row]),p.getAvailability(),PlotShape.RECTANGLE,10,10);
           			}
	if(row<=2){parkingandcornerplot(row,col,plotID,size);}
}
 

public void parkingandcornerplot(int row,int col,String plotID, PlotSize size[]){
	if(col==4){
	   	plots[row][col]=new Plot(plotID,PlotSize.PARKING,plotprice(PlotSize.PARKING),p.getAvailability(),PlotShape.RECTANGLE,150,3);
           			}
	else if((col+1)%4==0){
                 if(row==0){
	   	plots[row][col]=new CornerPlot(plotID,size[row],plotprice(size[row])+(8*plotprice(size[row])),p.getAvailability(),PlotShape.L_SHAPE,15,15,5,5);
           			}
                 if(row==1){
	   	plots[row][col]=new CornerPlot(plotID,size[row],plotprice(size[row])+(8*plotprice(size[row])),p.getAvailability(),PlotShape.L_SHAPE,15,15,10,10);
           			}
                 if(row==2){
	   	plots[row][col]=new CornerPlot(plotID,size[row],plotprice(size[row])+(8*plotprice(size[row])),p.getAvailability(),PlotShape.L_SHAPE,30,30,10,10);
           			}
}
}

//-------------------------------------------------------------------------------------------Pricing Method
 public double plotprice(PlotSize p){
	return p.getPrice();
    }

//-------------------------------------------------------------------------------------------Booking Method
  public void bookplot(int row,int col){
	if(!checkbound(row,col)){
           System.out.println("Invalid Input!");
		return;
}
	if(plots[row-1][col-1].getAvailability()==true){	
             plots[row-1][col-1].Booked();}
}

//-------------------------------------------------------------------------------------------Cancel Method
  public void cancelbooking(int row,int col){
	if(!checkbound(row,col)){
           System.out.println("Invalid Input!");
}
	if(plots[row-1][col-1].getAvailability()==false){	
             plots[row-1][col-1].CancelBooking();}
}

//-------------------------------------------------------------------------------------------Check Bound Method
  public boolean checkbound(int row,int col){
	if(row>=0&&row<=plots.length){
	     if(col>=0&&col<=plots[row].length){
		return true;}
	     else
		return false;}
	else return false;
}
//-------------------------------------------------------------------------------------------Random Booking Method
 public void randombooking(){
        for(int i=0;i<plots.length;i++){
        for(int j=0;j<plots[i].length;j++){
	Random rand=new Random();
        int ran=rand.nextInt(plots[i].length);
        Plot temp=plots[i][ran];
        plots[i][ran]=plots[i][0];
        plots[i][0]=temp;
        plots[i][0].Booked();
}
}
}

//-------------------------------------------------------------------------------------------Booking & Cancelling by String Method
 public boolean booking(String s){
      String PlotID;
      Plot p;
     for(int i=0;i<plots.length;i++){
       for(int j=0;j<plots[i].length;j++){
		if(plots[i][j].getAvailability()==true&&s.equals(String.format("S%d-%03d",i+1,j+1))){
			plots[i][j].Booked();
			return false;
}
}
}
                        return true;
}

 public boolean CancelBooking(String s){
      String PlotID;
      Plot p;
     for(int i=0;i<plots.length;i++){
       for(int j=0;j<plots[i].length;j++){
		if(plots[i][j].getAvailability()==false&&s.equals(String.format("S%d-%03d",i+1,j+1))){
			plots[i][j].CancelBooking();
				return true;
}
}
}
				return false;
}
//-------------------------------------------------------------------------------------------Lists of Available & Booked Method
 public void ReturnLists(){

        Plot bookedlist[][]=new Plot[5][];
        Plot availablelist[][]=new Plot[5][];

	for(int i=0;i<plots.length;i++){
                    availablelist[i]=new Plot[plots[i].length];
                    bookedlist[i]=new Plot[plots[i].length];

          for(int j=0;j<plots[i].length;j++){
                  if(plots[i][j].getAvailability()==true){
                      bookedlist[i][j]=plots[i][j];}
                  else{
		      availablelist[i][j]=plots[i][j];}
}
}
                System.out.println("\n=====Booked Seats=====");
          	for(int i=0;i<bookedlist.length;i++){
                      System.out.println("\nStreet:"+(i+1));
                System.out.println(p.display());
          for(int j=0;j<bookedlist[i].length;j++){
                if(bookedlist[i][j]!=null){
                System.out.println(bookedlist[i][j]);}
}
}
                System.out.println("\n=====Available Seats=====");
          	for(int i=0;i<availablelist.length;i++){
                      System.out.println("\nStreet:"+(i+1));
                System.out.println(p.display());
          for(int j=0;j<availablelist[i].length;j++){
                if(availablelist[i][j]!=null){
                System.out.println(availablelist[i][j]);}
}
}
}
//-------------------------------------------------------------------------------------------Get First Avaialble Plot Method
 public void firstavailable(PlotSize p){
                 System.out.println("\nAvailable Plot of Type:"+p);
  	     for(int i=0;i<plots.length;i++){
       for(int j=0;j<plots[i].length;j++){
              if(p==plots[i][j].getsize()&&plots[i][j].getAvailability()==true){
                 System.out.println(plots[i][j]);
                 break;
}
}
}
}
//-------------------------------------------------------------------------------------------Count Plot Method

    public int totalPlots(){
      int c=0; 
      for(int i=0;i<plots.length;i++)
         c+=plots[i].length;
            return c; }

    public int availablePlots(){
         int c=0;
         for(int i=0;i<plots.length;i++)
            for(int j=0;j<plots[i].length;j++)
                if(!plots[i][j].getAvailability()) {
                     c++;}
                 return c; }


}
