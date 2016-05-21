
public interface DVDRentInterface {

	public MoviesList getAllMovies();
	public MoviesList findMovieByName(String name);
	public Category findCategoryByID(int cid);
	
}
