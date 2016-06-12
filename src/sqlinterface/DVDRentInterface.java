package sqlinterface;
import datatypes.DVD;
import datatypes.DVDList;
import datatypes.Movie;
import datatypes.MoviesList;

/**
 * 
 * @author Michal
 *
 */
public interface DVDRentInterface {

	
	public boolean insertMovie(Movie movie);
	public boolean updateMovie(Movie movie);
	public boolean deleteMovie(Movie movie);
	
	public MoviesList getAllMovies();
	public Movie findMovieByTitle(String title);
	public Movie findMovieByID(int mid);
	

	public boolean insertDvd(DVD dvd);
	public boolean rentDVD(DVD dvd, String user);
	public boolean returnDVD(DVD dvd);
	public boolean deleteDVD(DVD dvd);

	public int countAvaliableDvd(Movie movie);
	
	public DVDList getAllDvds();
	public DVDList findDvdByTitle(String title);
	public DVD findDvdByID(int dvdID);
}
