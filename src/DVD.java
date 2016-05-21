

public class DVD {
	int dvdId; 
	int mid; 
	boolean available;
	/**
	 * @param dvdId
	 * @param mid
	 * @param available
	 */
	public DVD(int dvdId, int mid, boolean available) {
		
		super();
		this.dvdId = dvdId;
		this.mid = mid;
		this.available = available;
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
		return available;
	}
	public void setLent(boolean lent) {
		this.available = lent;
	}
	
	@Override
    public String toString() {
        return "["+dvdId+"] - "+mid+" "+available;
    }
}
