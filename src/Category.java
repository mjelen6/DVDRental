
public class Category {
	private int cid; 
	private String name; 
	
	
	public Category(){}
	public Category(int cid, String name){
		this.setName(name);
		this.setCid(cid);
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
