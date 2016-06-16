package sqlinterface;
import datatypes.DVD;
import datatypes.DVDList;
import datatypes.Movie;
import datatypes.MoviesList;


/**
 * 
 * @author Maciek
 *
 */
public abstract class DVDRental {

//	private static Logger log = Logger.getLogger(DVDRental.class);
	private DVDRentInterface dvdRentInterface;

	
	public DVDRental(DVDRentInterface dvdRentInterface) {
		this.dvdRentInterface = dvdRentInterface;
	}
	
	
	public boolean insertMovie(Movie movie) {
		return dvdRentInterface.insertMovie(movie);
	}
	public boolean updateMovie(Movie movie){
		return dvdRentInterface.updateMovie(movie);
	}	
	public boolean deleteMovie(Movie movie){
		return dvdRentInterface.deleteMovie(movie);
	}
	
	
	
	public MoviesList getAllMovies() {
		return dvdRentInterface.getAllMovies();
	}
	public Movie findMovieByTitle(String title) {
		return dvdRentInterface.findMovieByTitle(title);
	}
	public Movie findMovieByID(int mid){
		return dvdRentInterface.findMovieByID(mid);
	}


	
	public boolean insertDvd(DVD dvd) {
		return dvdRentInterface.insertDvd(dvd);
	}	
	public boolean rentDVD(DVD dvd, String user){
		return dvdRentInterface.rentDVD(dvd, user);
	}
	public boolean returnDVD(DVD dvd){
		return dvdRentInterface.returnDVD(dvd);
	}	
	public boolean deleteDVD(DVD dvd){
		return dvdRentInterface.deleteDVD(dvd);
	}
	
	
	
	
	public int countAvaliableDvd(Movie movie) {
		return dvdRentInterface.countAvaliableDvd(movie);
	}
	
	
	
	
	
	public DVDList getAllDvds() {
		return dvdRentInterface.getAllDvds();
	}
	public DVDList findDvdByTitle(String title) {
		return dvdRentInterface.findDvdByTitle(title);
	}
	public DVD findDvdByID(int dvdID){
		return dvdRentInterface.findDvdByID(dvdID);
	}
	
	

}
