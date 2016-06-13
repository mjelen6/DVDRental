package datatypes;

import java.sql.Date;

/**
 * 
 * @author Maciek
 *
 */
public class DVD {
	
	
	private int dvdId; 
	private int mid; 
	private boolean available;
	private String userName;
	private Date lentDate;
	
	
	
	/**
	 * @param dvdId
	 * @param mid
	 * @param available
	 * @param userName
	 * @param lentDate
	 */
	public DVD(int dvdId, int mid, boolean available, String userName, Date lentDate) {
		super();
		this.dvdId = dvdId;
		this.mid = mid;
		this.available = available;
		this.userName = userName;
		this.lentDate = lentDate;
	}
	
	
	public DVD(int mid, boolean available, String userName, Date lentDate) {
		super();
		this.mid = mid;
		this.available = available;
		this.userName = userName;
		this.lentDate = lentDate;
	}
	
	public DVD(int mid) {
		super();
		this.mid = mid;
		this.available = true;
		this.userName = null;
		this.lentDate = null;
	}
	
	
	/**
	 * 
	 */
	public DVD() {
		super();
	}
	
	
	
	

	
	
	
	@Override
	public String toString() {
		
		return "DVD [" + dvdId + "] mid=" + mid + ", available=" + available + ", userName=" + userName
				+ ", lentDate=" + lentDate;
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
	public Date getLentDate() {
		return lentDate;
	}
	public void setLentDate(Date lentDate) {
		this.lentDate = lentDate;
	}

	
}
