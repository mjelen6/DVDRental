package gui;
import javax.swing.JTable;
import org.apache.log4j.Logger;

import MainFrame;
import datatypes.Record;
import datatypes.RecordList;

public class DvdTable extends JTable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4476385232110473450L;
	private static Logger log = Logger.getLogger(MainFrame.class); // class logger
	
	
	public DvdTable(DvdTableModel dvdTableModel){
		super(dvdTableModel);
	}
	
	public void insertRow(Record record) {
		
		DvdTableModel dvdTableModel = (DvdTableModel)getModel();
		dvdTableModel.addRow(record);
		dvdTableModel.fireTableDataChanged();
		log.trace("" + record.getDvd().getDvdId() + " " + record.getMovie().getMid() + " " +  record.getMovie().getTitle() + " inserted");	
	}
	
	public void eraseTable() {
		
		log.trace("Erasing table");
		DvdTableModel dvdTableModel = (DvdTableModel)getModel();
		dvdTableModel.getRecordList().clear();
		dvdTableModel.fireTableDataChanged();
		log.trace("Movies table cleared");
	}
	
	
	public void insertTable(RecordList recordList) {

		log.trace("Inserting into table...");

		if (!recordList.isEmpty()) {
			
			for (Record record : recordList) {
				insertRow(record);
			}
		}
	}
	
}
