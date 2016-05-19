import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Loan {
	
	private static Logger log = Logger.getLogger(Movie.class);
	
	private int loanId;
	private int dvdId;
	private String userName;
	private String userSurname;
	private String lentDate; 		// do zmiant na Date 
	private String returnDate; 
	
	public Loan(){}
	public Loan(int loanId, int dvdId,String userName, String userSurname,String lentDate, String returnDate){
		this.loanId = loanId;
		this.dvdId = dvdId; 
		this.userName = userName;
		this.userSurname = userSurname;
		this.lentDate = lentDate;
		this.returnDate = returnDate;
		
	}
	

	

	
	/*public static void main(String[] args) {
		
		BasicConfigurator.configure();
		log.debug("Application start");
		
		GregorianCalendar date = new GregorianCalendar();	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm");
		
		System.out.println("" + dateFormat.format(date.getTime()));
		
		
		log.debug("App stop");
		
	}*/

}
