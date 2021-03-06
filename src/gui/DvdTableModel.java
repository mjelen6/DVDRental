package gui;
import java.sql.Date;

import javax.swing.table.AbstractTableModel;
import datatypes.Record;
import datatypes.RecordList;

/**
 * 
 * @author Maciek
 *
 */
public class DvdTableModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7238979096107179292L;

    public DvdTableModel(RecordList recordList) {
    	super();
        this.recordList = recordList;  
    }
    
	private RecordList recordList;

	// Name + isCellEditable
    private static final Object[][] COLUNM_SPECS = {{"dvdID",			false},
    												{"mid",				false},
    												{"Tytu�",			false},
    												{"Re�yser",			false},
    												{"Kategoria",		false},
    												{"Dost�pny",		false},
    												{"Wypo�yczy�",		false},
    												{"Data po�yczki",	false},	};
        
    private static final Class<?>[] COLUMN_CLASSES = { 	Integer.class,	
    													Integer.class,
    													String.class,
    													String.class,
    													String.class,
    													Boolean.class,
    													String.class,
    													Date.class};


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object value = "??";
        Record record = recordList.get(rowIndex);
        switch (columnIndex) {
        	case 0:
        		value = record.getDvd().getDvdId();
        		break;
            case 1:
                value = record.getMovie().getMid();
                break;
            case 2:
                value = record.getMovie().getTitle();
                break;
            case 3:
                value = record.getMovie().getDirector();
                break;
            case 4:
                value = record.getMovie().getCategory();
                break;
            case 5:
                value = record.getDvd().isAvailable();
                break;
            case 6:
                value = record.getDvd().getUserName();
                break;
            case 7:
                value = record.getDvd().getLentDate();
                break;
            default:
            	break;
        }

        return value;
    }
    
    
    public void addRow(Record record){
    	recordList.add(record);
    }
    
	public RecordList getRecordList() {
		return recordList;
	}

	public void setRecordList(RecordList recordList) {
		this.recordList = recordList;
	}
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }
       
    public int getColumnCount() {
        return COLUNM_SPECS.length;
    }

    public int getRowCount() {
        return recordList.size();
    }

    public String getColumnName(int column) {
        return (String)COLUNM_SPECS[column][0];
    }
    
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (boolean) COLUNM_SPECS[columnIndex][1];
	}





	
}
