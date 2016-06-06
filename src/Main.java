import java.sql.Date;
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

		// dodanie kilku filmow 
		SqlHandler s = new SqlHandler();
		s.insertMovie("dupa","andrzej wajda","komedia");
		s.insertMovie("papa", "andrzej wajda wajda","dramat ");
		
		
		System.out.println("\nlista filmow");
		MoviesList moviesss = s.getAllMovies();
		for (Movie c : moviesss) {
			System.out.println(c);
		}
		
		// znajdowanie filmu mo mid 
		Movie movies;
		System.out.println("szukany film po id  " + 14);
		m = s.findMovieByID(14);
		System.out.println(m);

		// szukanie filmu po mid
		System.out.println(" \nfind movie by name " + "papa");
		movies = s.findMovieByName("papa");
		System.out.println(movies);
		
		//dodanie DVD
		Date data = new Date(2015, 5, 21);
		System.out.println("dodanie dvd");
		s.insertDvd(2, true, "Jacek", "placek", data);
		
		System.out.println("\n wyswietlenie wszystkich rekordów z tablei DVD");
		DVDList dvdList = s.getAllDvds();
		for (DVD c : dvdList) {
			System.out.println(c);
			System.out.println( c.getLentDate());
		}
		
		System.out.println("find dvd by name: " + "papa" );
		dvdList = s.findDvdByName("papa");
		for (DVD c : dvdList) {
			System.out.println(c);
		}
		
		
		
//		System.out.println("wszyskite egzemplarze ");
//		s.insertDvd(2, true);
//		DVDList dvdList =  s.getAllDvds();
//		for (DVD c : dvdList) {
//			System.out.println(c);
//		}
		
		
		
		s.close();
	}

}
