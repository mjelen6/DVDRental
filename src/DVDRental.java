import java.sql.Date;

import org.apache.log4j.Logger;

public abstract class DVDRental {

	private static Logger log = Logger.getLogger(DVDRental.class);

	private DVDRentInterface dvdRentInterface;

	public DVDRental(DVDRentInterface dvdRentInterface) {
		this.dvdRentInterface = dvdRentInterface;
	}

	public MoviesList getAllMovies() {
		return dvdRentInterface.getAllMovies();
	}

	public Movie findMovieByName(String name) {
		return dvdRentInterface.findMovieByName(name);
	}

	public boolean insertMovie(String name, String director, String category) {
		return dvdRentInterface.insertMovie(name, director, category);
	}

	public DVDList findDvdByName(String name) {
		return dvdRentInterface.findDvdByName(name);
	}

	public boolean insertDvd(int mid, Boolean avaliable, String userName, String userSurname, Date lentDate) {
		return dvdRentInterface.insertDvd(mid, avaliable, userName, userSurname, lentDate);
	}

	public DVDList getAllDvds() {
		return dvdRentInterface.getAllDvds();
	}

	public int countAvaliableDvd(Movie movie) {
		return dvdRentInterface.countAvaliableDvd(movie);
	}

}
