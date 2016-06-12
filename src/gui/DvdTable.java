package gui;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import datatypes.Movie;
import datatypes.MoviesList;

public class DvdTable extends JTable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4476385232110473450L;
	private static Logger log = Logger.getLogger(MainFrame.class); // class logger
	
	
	public DvdTable() {
		super();
	}

	public DvdTable(TableModel tm){
		super(tm);
	}
	
	public void insertRow(Movie movie) {
		
		DefaultTableModel model = (DefaultTableModel)getModel();
		model.addRow(new Object[] { movie.getTitle(), movie.getDirector(), movie.getCategory(), 153 });
		log.trace(movie + " inserted");	
	}
	
	public void eraseTable() {
		
		log.trace("Erasing table");

		DefaultTableModel model = (DefaultTableModel)getModel();
		model.getDataVector().removeAllElements();
		model.fireTableDataChanged();
		
		log.trace("Movies table cleared");
	}
	
	
	public void insertTable(MoviesList moviesList) {

		log.trace("Inserting into table: ");

		if (!moviesList.isEmpty()) {
			
			for (Movie movie : moviesList) {
				insertRow(movie);
			}
		}
	}
	
}
