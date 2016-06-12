package gui;
import javax.swing.table.AbstractTableModel;
import datatypes.Record;
import datatypes.RecordList;

public class DvdTableItemModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7238979096107179292L;

    public DvdTableItemModel(RecordList recordList) {
    	super();
        this.recordList = recordList;  
    }
    
	private RecordList recordList;

	// Name + isCellEditable
    private Object[][] columnSpecs = {	{"dvdID",			false},
    									{"mid",				false},
    									{"Tytu³",			false},
    									{"Re¿yser",			false},
    									{"Kategoria",		false},
    									{"Dostêpny",		false},
    									{"Wypo¿yczy³",		false},
    									{"Data po¿yczki",	false},	};
        

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
    
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
       
    public int getColumnCount() {
        return columnSpecs.length;
    }

    public int getRowCount() {
        return recordList.size();
    }

    public String getColumnName(int column) {
        return (String)columnSpecs[column][0];
    }
    
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (boolean) columnSpecs[columnIndex][1];
	}  
}
