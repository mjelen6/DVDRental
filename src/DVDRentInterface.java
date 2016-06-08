import java.sql.Date;

/**
 * 
 * @author Michal
 *
 */
public interface DVDRentInterface {

	public MoviesList getAllMovies();

	public Movie findMovieByName(String name);

	public boolean insertMovie(String name, String director, String category);

	public DVDList findDvdByName(String name);

	public boolean insertDvd(int mid, Boolean avaliable, String userName, Date lentDate);

	public DVDList getAllDvds();

	public int countAvaliableDvd(Movie movie);

}
