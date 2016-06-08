import java.sql.Date;

import org.apache.log4j.Logger;


/**
 * 
 * @author Maciek
 *
 */
public abstract class DVDRental {

	private static Logger log = Logger.getLogger(DVDRental.class);

	private DVDRentInterface dvdRentInterface;

	public DVDRental(DVDRentInterface dvdRentInterface) {
		this.dvdRentInterface = dvdRentInterface;
	}

	public MoviesList getAllMovies() {
		return dvdRentInterface.getAllMovies();
	}

	public Movie findMovieByTitle(String title) {
		return dvdRentInterface.findMovieByTitle(title);
	}

	public boolean insertMovie(Movie movie) {
		return dvdRentInterface.insertMovie(movie);
	}

	public DVDList findDvdByTitle(String title) {
		return dvdRentInterface.findDvdByTitle(title);
	}

	public boolean insertDvd(DVD dvd) {
		return dvdRentInterface.insertDvd(dvd);
	}

	public DVDList getAllDvds() {
		return dvdRentInterface.getAllDvds();
	}

	public int countAvaliableDvd(Movie movie) {
		return dvdRentInterface.countAvaliableDvd(movie);
	}

	
	public boolean rentDVD(DVD dvd, String user){
		return dvdRentInterface.rentDVD(dvd, user);
	}

	public boolean returnDVD(DVD dvd){
		return dvdRentInterface.returnDVD(dvd);
	}
	
	public boolean deleteMovie(Movie movie){
		return dvdRentInterface.deleteMovie(movie);
	}
	
	public boolean deleteDVD(DVD dvd){
		return dvdRentInterface.deleteDVD(dvd);
	}
	
	
}
