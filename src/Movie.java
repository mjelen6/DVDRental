
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
	
	 public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	@Override
	    public String toString() {
	        return "["+mid+"] - "+name+" "+director;
	    }
	
	
}
