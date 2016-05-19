import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Main {

	private static Logger log = Logger.getLogger(Main.class);
	
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		log.debug("Application start");
		Movie m = new Movie();
		
		SqlHandler s = new SqlHandler();
		s.insertMovie(2, "nikt", "niznany");
		System.out.println("Przeszlo");
		
		List<Movie> movies = new LinkedList<Movie>();
		movies = s.getMovies();
		
		System.out.println("lista filmow");
		for(Movie c: movies){
			System.out.println(c);
		}
		
		
		System.out.println("znaleziony film");
		m = s.findMovieByID(3);
		System.out.println(m);
		
		s.close();
	}

}
