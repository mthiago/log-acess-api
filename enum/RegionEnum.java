public enum RegionEnum {
	
	US_EAST(1),
	US_WEST(2),
	AP_SOUTH(3);

	private Integer codigo;
	
	RegionEnum(Integer codigo){
		this.codigo = codigo;
	}

    public Integer codigo() {
    	return codigo;
    }
    
   public static RegionEnum fromValue(Integer i){
	   if(i!= null){
		   for(RegionEnum a :RegionEnum.values()){
			   if(a.codigo().equals(i)){
				   return a;
			   }
		   }
	   }
	   return null;
   }

}
