
public interface DVDRentInterface {

	public MoviesList getAllMovies();
	public MoviesList findMovieByName(String name);
	public boolean insertMovie(String name, String director, String category);
}
