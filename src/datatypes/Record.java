package datatypes;
public class Record {

	private Movie movie;
	private DVD dvd;
	
	public Record() {

	}
	
	public Record(Movie movie, DVD dvd){
		this.movie = movie;
		this.dvd = dvd;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public DVD getDvd() {
		return dvd;
	}

	public void setDvd(DVD dvd) {
		this.dvd = dvd;
	}
}
