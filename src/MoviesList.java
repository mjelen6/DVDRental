import java.util.ArrayList;

/**
 * Class represents list of movies
 * @author Maciek
 *
 */
public class MoviesList extends ArrayList<Movie> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7499611894762628892L;

	public MoviesList(){
		super();
	}

	/**
	 * Creates empty movies list
	 * @param size Size of the list
	 */
	public MoviesList(int size) {
		super(size);
	}
	
	
}
