import java.sql.Date;

/**
 * 
 * @author Michal
 *
 */
public interface DVDRentInterface {

	public MoviesList getAllMovies();

	public Movie findMovieByTitle(String title);

	public boolean insertMovie(Movie movie);

	public DVDList findDvdByTitle(String title);

	public boolean insertDvd(DVD dvd);

	public DVDList getAllDvds();

	public int countAvaliableDvd(Movie movie);

	public boolean rentDVD(DVD dvd, String user);

	public boolean returnDVD(DVD dvd);

	public boolean deleteMovie(Movie movie);
	
	public boolean deleteDVD(DVD dvd);
	
	public boolean updateMovie(Movie movie);
	
}
