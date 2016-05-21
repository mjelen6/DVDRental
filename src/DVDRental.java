import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public abstract class DVDRental {

	
	private static Logger log = Logger.getLogger(DVDRental.class);
	
	private DVDRentInterface dvdRentInterface;
	
	public DVDRental(DVDRentInterface dvdRentInterface){
		this.dvdRentInterface = dvdRentInterface;
	}
	
	
	public MoviesList getAllMovies() {
		return dvdRentInterface.getAllMovies();
	}

	public MoviesList findMovieByName(String name){
		return dvdRentInterface.findMovieByName(name);
	}
	public Category findCategoryByID(int cid){
		return dvdRentInterface.findCategoryByID(cid);
		
	}
	
	
	
}

