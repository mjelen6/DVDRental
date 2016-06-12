package datatypes;

import java.util.ArrayList;

/**
 * 
 * @author Maciek
 *
 */
public class DVDList extends ArrayList<DVD> {

	/*
	 * 
	 */
	private static final long serialVersionUID = 8876791892694402157L;

	public DVDList() {
		super();
	}

	/**
	 * Creates empty movies list
	 * 
	 * @param size
	 *            Size of the list
	 */
	public DVDList(int size) {
		super(size);
	}

}

