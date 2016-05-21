import org.apache.log4j.Logger;

public abstract class DVDRental {

	private static Logger log = Logger.getLogger(DVDRental.class);
	
	private DVDRentInterface dvdRentInterface;
	
	public DVDRental(DVDRentInterface dvdRentInterface){
		this.dvdRentInterface = dvdRentInterface;
	}
	
	
	public MoviesList getAllMovies() {
		return dvdRentInterface.getAllMovies();
	}

	public MoviesList findMovieByName(String name){
		return dvdRentInterface.findMovieByName(name);
	}
	
	public boolean insertMovie(String name, String director, String category){
		return dvdRentInterface.insertMovie(name, director, category);
	}
	
	
	
}

