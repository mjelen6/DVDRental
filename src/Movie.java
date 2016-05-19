
import org.apache.log4j.Logger;




public class Movie {

	private static Logger log = Logger.getLogger(Movie.class);
	
	private int mid;
	private int cid; 
	private String name;
	private String director; 
	
	public Movie(){}
	public Movie(int mid, int cid, String name, String director){
		this.mid= mid;
		this.cid = cid;
		this.name = name; 
		this.director = director;
	}
	 @Override
	    public String toString() {
	        return "["+mid+"] - "+name+" "+director;
	    }
	
	
}
