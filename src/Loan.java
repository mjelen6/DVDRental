import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Loan {

	private static Logger log = Logger.getLogger(Movie.class);
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	
	public static void main(String[] args) {
		
		
		BasicConfigurator.configure();
		log.debug("Application start");
		
		GregorianCalendar date = new GregorianCalendar();
		dateFormat.setTimeZone(date.getTimeZone());
		
		System.out.println(dateFormat.format(date.getTime()));
		
		
		log.debug("App stop");
		
	}
}
