package datatypes;

public class Movie {

//	private static Logger log = Logger.getLogger(Movie.class);

	private int mid;
	private String title;
	private String director;
	private String category;
	

	public Movie() {
	}

	public Movie(int mid, String title, String director, String category) {
		this.mid = mid;
		this.title = title;
		this.director = director;
		this.category = category;
	}
	
	public Movie(String title, String director, String category) {
		this.title = title;
		this.director = director;
		this.category = category;
	}
	

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getCategory() {
		return category;
	}

	public void setCid(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Override
	public String toString() {
		return "[" + mid + "] Title: " + title + " Director: " + director + " Category: " + category;
	}

}
