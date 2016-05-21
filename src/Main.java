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
		s.insertMovie("komedia", "andrzej wajda", "dupa");
		s.insertMovie("dramat ", "andrzej wajda wajda", "papa");
		MoviesList movies = s.getAllMovies();
		System.out.println("lista filmow");
		for (Movie c : movies) {
			System.out.println(c);
		}

		System.out.println("szukany film po id  " + 14);
		m = s.findMovieByID(14);
		System.out.println(m);

		System.out.println(" find movie by name " + "dupa");
		movies = s.findMovieByName("dupa");
		for (Movie c : movies) {
			System.out.println(c);
		}

//
//		System.out.println("wszyskite egzemplarze ");
//		s.insertDvd(2, true);
//		DVDList dvdList =  s.getAllDvds();
//		for (DVD c : dvdList) {
//			System.out.println(c);
//		}
		
		
		
		s.close();
	}

}
