import java.sql.Date;


/**
 * 
 * @author Maciek
 *
 */
public class DVD {
	
	int dvdId; 
	int mid; 
	boolean available;
	String userName;
	String userSurname;
	Date lentDate;
	
	@Override
	public String toString() {
		return "DVD [dvdId=" + dvdId + ", mid=" + mid + ", available=" + available + ", userName=" + userName
				+ ", userSurname=" + userSurname + ", lentDate=" + lentDate + "]";
	}
	public int getDvdId() {
		return dvdId;
	}
	public void setDvdId(int dvdId) {
		this.dvdId = dvdId;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
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
	public Date getLentDate() {
		return lentDate;
	}
	public void setLentDate(Date lentDate) {
		this.lentDate = lentDate;
	}
	/**
	 * @param dvdId
	 * @param mid
	 * @param available
	 * @param userName
	 * @param userSurname
	 * @param lentDate
	 */
	public DVD(int dvdId, int mid, boolean available, String userName, String userSurname, Date lentDate) {
		super();
		this.dvdId = dvdId;
		this.mid = mid;
		this.available = available;
		this.userName = userName;
		this.userSurname = userSurname;
		this.lentDate = lentDate;
	}
	/**
	 * 
	 */
	public DVD() {
		super();
	}
	
}
