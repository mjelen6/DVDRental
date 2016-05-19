

public class DVD {
	int dvdId; 
	int mid; 
	boolean lent;
	/**
	 * @param dvdId
	 * @param mid
	 * @param lent
	 */
	public DVD(int dvdId, int mid, boolean lent) {
		
		super();
		this.dvdId = dvdId;
		this.mid = mid;
		this.lent = lent;
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
	public boolean isLent() {
		return lent;
	}
	public void setLent(boolean lent) {
		this.lent = lent;
	}
}
