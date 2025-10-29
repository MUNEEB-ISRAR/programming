public class CityHousing{

	private String cityname;
	HousingSociety societies[];
	HousingSociety h=new HousingSociety();
//Constructor
  CityHousing(String cityname){
	this.cityname=cityname;
        societies=new HousingSociety[2];
        for(int i=0;i<societies.length;i++){
		societies[i]=new HousingSociety(String.format("LDA Avenue-%d",i+1));
}
}

//To String
	public String toString(){
		StringBuilder str=new StringBuilder();                
                str.append(String.format("\n%40s",cityname));
		for(int i=0;i<societies.length;i++){
			str.append("\n"+societies[i].toString());
			}
                    return str.toString();
}

//Find Society Method
 public HousingSociety findSocietyByName(String name){
         for(int i=0;i<societies.length;i++) 
           if(societies[i].societyname.equals(name))
              {return societies[i];}
               return null; }

//Booking & Cancellation Method

    public boolean relayBook(String societyName, String blockName, String plotId){
       HousingSociety s = findSocietyByName(societyName);
           if(s==null) 
             {return false;} 
             return h.book(blockName,plotId); }
    public boolean relayCancel(String societyName, String blockName, String plotId){
        HousingSociety s = findSocietyByName(societyName); 
             if(s==null) 
              {return false;}
             return s.cancel(blockName,plotId);
  }


}