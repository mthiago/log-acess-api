package br.com.java.enums;

public enum UrlEnum {

	GATO("/pets/exotic/cats/10"),
	CACHORRO("/pets/guaipeca/dogs/1"),
	TIGRE("/tiggers/bid/now");

	private String url;

	UrlEnum(String url){
		this.url = url;
	}

    public String url() {
    	return url;
    }

   public static UrlEnum fromValue(String s){
	   if(s!= null){
		   for(UrlEnum a :UrlEnum.values()){
			   if(a.url().equals(s)) {
				   return a;
			   }
		   }
	   }
	   return null;
   }	
	
}