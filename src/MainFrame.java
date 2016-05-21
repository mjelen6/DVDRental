import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;

public class MainFrame extends DVDRental{

	private static Logger log = Logger.getLogger(MainFrame.class); // class logger
	
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel sideBar;
	private JPanel tablePanel;
	private JLabel title;
	private JTable dvdTable;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JButton searchButton;
	private Box searchBox;
	private Component verticalGlue;
	private Component verticalGlue_1;
	private Component verticalGlue_3;
	private Component verticalGlue_4;

	
	
	public MainFrame(DVDRentInterface dvdRentInterface) {
		super(dvdRentInterface);
	}
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		log.debug("Application Start");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DVDRentInterface dvdRentInterface = new SqlHandler();
					MainFrame mainFrame = new MainFrame(dvdRentInterface);
					mainFrame.createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		log.info("Main function closed");
	}


	/** Method that launches main frame */
	@SuppressWarnings("serial")
	private void createAndShowGUI() { 
		
		// Prepare main frame
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img/dvd-icon.png"));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create "background" panel
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(960, 540));
		mainPanel.setMinimumSize(new Dimension(640, 360));
		
		// Create sidebar
		sideBar = new JPanel();
		sideBar.setBorder(BorderFactory.createTitledBorder("Menu boczne"));
		sideBar.setLayout(new BorderLayout(0, 0));
		
		searchBox = Box.createVerticalBox();
		searchBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		sideBar.add(searchBox);
		Label label = new Label("Szukaj");
		label.setFont(new Font("Arial", Font.BOLD, 16));
		searchBox.add(label);
		
		
		textField = new JTextField();
		searchBox.add(textField);
		textField.setColumns(20);
		textField.setMaximumSize( textField.getPreferredSize() );
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		

		
		searchButton = new JButton("Szukaj");
		searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		searchBox.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MoviesList movies = getAllMovies();
				DefaultTableModel model = (DefaultTableModel) dvdTable.getModel();
				model.getDataVector().removeAllElements();
				model.fireTableDataChanged();
				
				System.out.println("lista filmow");
				for(Movie movie : movies){
					model.addRow(new Object[]{movie.getName(), movie.getDirector(), movie.getCid(), 3});
				}
			}
		});
		
		
		verticalGlue_4 = Box.createVerticalGlue();
		searchBox.add(verticalGlue_4);
		
		verticalGlue_3 = Box.createVerticalGlue();
		searchBox.add(verticalGlue_3);
		
		verticalGlue_1 = Box.createVerticalGlue();
		searchBox.add(verticalGlue_1);
		
		verticalGlue = Box.createVerticalGlue();
		searchBox.add(verticalGlue);
		mainPanel.add(sideBar, BorderLayout.WEST);
		
		// Create tablepanel
		tablePanel = new JPanel(new BorderLayout());
		mainPanel.add(tablePanel,BorderLayout.CENTER);
		
		//tablePanel.
		title = new JLabel("Lista dostêpnych DVD");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		tablePanel.add(title,BorderLayout.NORTH);
		

		
		
		
		dvdTable = new JTable(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tytu\u0142", "Re\u017Cyser", "Kategoria", "Ilo\u015B\u0107"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		
		scrollPane = new JScrollPane(dvdTable);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		
		
		
		// content panes must be opaque
		mainPanel.setOpaque(true);
		frame.setContentPane(mainPanel);
		frame.setMinimumSize(frame.getMinimumSize());

		
		// Display the window.
		frame.pack();
		frame.setVisible(true);

		
	}

}
