
public interface DVDRentInterface {

	public MoviesList getAllMovies();
	public Movie findMovieByName(String name);
	public boolean insertMovie(String name, String director, String category);
}
