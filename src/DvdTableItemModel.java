import javax.swing.table.AbstractTableModel;

public class DvdTableItemModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7238979096107179292L;


	private RecordList recordList;

    public DvdTableItemModel(RecordList recordList) {

        this.recordList = recordList;

    }

    @Override
    public int getRowCount() {
        return recordList.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object value = "??";
        Record record = recordList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                value = record.getUserUsername();
                break;
            case 1:
                value = record.getUserName();
                break;
            case 2:
                value = record.getUserPhone();
                break;
            case 3:
                value = record.getUserNic();
                break;
            case 4:
                value = record.getUserAddress();
                break;
            case 5:
                value = record.getUserEmail();
                break;
        }

        return value;

    }



turn 
     */
}
