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
		s.insertMovie(2, "ktos", "niznany");

		MoviesList movies = s.getAllMovies();

		System.out.println("lista filmow");
		for (Movie c : movies) {
			System.out.println(c);
		}

		System.out.println("znaleziony film od id " + 14);
		m = s.findMovieByID(14);
		System.out.println(m);



		System.out.println("wszyskite egzemplarze ");
		s.insertDvd(2, true);
		DVDList dvdList =  s.getAllDvds();
		for (DVD c : dvdList) {
			System.out.println(c);
		}
		
		
		
		s.close();
	}

}
