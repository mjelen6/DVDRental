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
		this.setLoanId(loanId);
		this.setDvdId(dvdId); 
		this.setUserName(userName);
		this.setUserSurname(userSurname);
		this.setLentDate(lentDate);
		this.setReturnDate(returnDate);
		
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public int getDvdId() {
		return dvdId;
	}
	public void setDvdId(int dvdId) {
		this.dvdId = dvdId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSurname() {
		return userSurname;
	}
	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}
	public String getLentDate() {
		return lentDate;
	}
	public void setLentDate(String lentDate) {
		this.lentDate = lentDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	

	

	

}
