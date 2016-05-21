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
import java.awt.event.KeyEvent;

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
	private JTextField searchTextField;
	private JButton searchButton;
	private Box searchBox;
	private Component verticalGlue;

	
	private MoviesList movies;
	private Box insertBox;
	private Label label_1;
	private JTextField textField_1;
	private JButton button;
	private Component verticalGlue_1;
	private Component verticalGlue_2;
	
	
	
	public MainFrame(DVDRentInterface dvdRentInterface) {
		super(dvdRentInterface);
	}
	
	
	private void insertRowInMoviesTable(Movie movie) {
				
		DefaultTableModel model = (DefaultTableModel) dvdTable.getModel();
		Category category = findCategoryByID(movie.getCid());
		model.addRow(new Object[] { movie.getName(), movie.getDirector(), category.getName(), 153 });
		log.trace(movie + " inserted");	
	}
	
	private void eraseMoviesTable() {
		log.trace("Erasing movies table");

		DefaultTableModel model = (DefaultTableModel) dvdTable.getModel();
		model.getDataVector().removeAllElements();
		model.fireTableDataChanged();
		
		log.trace("Movies table cleared");
	}
	
	private void insertMoviesTable(MoviesList moviesList) {

		log.trace("Inserting into moviesTable:");

		if (!moviesList.isEmpty()) {
			
			for (Movie movie : moviesList) {
				insertRowInMoviesTable(movie);
			}
		}
	}
	
	
	
	private MoviesList searchMovies(String nameOrDirector) {
		
		log.trace("Searching for movies");

		log.trace("Search for: " + nameOrDirector);
		
		MoviesList movies;
		
		if(nameOrDirector.isEmpty()){
			movies = getAllMovies();		
		}
	
		else {
			movies = findMovieByName(nameOrDirector);
		}
		
		log.debug(movies.size() + " movies found");
		
		return movies;
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
		sideBar.add(searchBox, BorderLayout.NORTH);
		Label searchLabel = new Label("Szukaj");
		searchLabel.setFont(new Font("Arial", Font.BOLD, 16));
		searchBox.add(searchLabel);
		
		
		searchTextField = new JTextField();
		searchBox.add(searchTextField);
		searchTextField.setColumns(20);
		searchTextField.setMaximumSize( searchTextField.getPreferredSize() );
		searchTextField.setHorizontalAlignment(SwingConstants.LEFT);
		searchTextField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				log.debug("Enter on textField");
				
				movies = searchMovies(searchTextField.getText());
				eraseMoviesTable();
				insertMoviesTable(movies);
				
			}
		});
		

		
		searchButton = new JButton("Szukaj");
		searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		searchBox.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				log.debug("Search button pressed");
				
				movies = searchMovies(searchTextField.getText());
				eraseMoviesTable();
				insertMoviesTable(movies);
				

			}
		});
		mainPanel.add(sideBar, BorderLayout.WEST);
		
		verticalGlue = Box.createVerticalGlue();
		sideBar.add(verticalGlue, BorderLayout.CENTER);
		
		insertBox = Box.createVerticalBox();
		sideBar.add(insertBox, BorderLayout.CENTER);
		insertBox.setAlignmentX(0.5f);
		
		label_1 = new Label("Szukaj");
		label_1.setFont(new Font("Arial", Font.BOLD, 16));
		insertBox.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setMaximumSize(new Dimension(166, 20));
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setColumns(20);
		insertBox.add(textField_1);
		
		button = new JButton("Szukaj");
		button.setAlignmentX(0.5f);
		insertBox.add(button);
		
		verticalGlue_1 = Box.createVerticalGlue();
		sideBar.add(verticalGlue_1, BorderLayout.SOUTH);
		
		verticalGlue_2 = Box.createVerticalGlue();
		sideBar.add(verticalGlue_2, BorderLayout.SOUTH);
		
		// Create tablepanel
		tablePanel = new JPanel(new BorderLayout());
		mainPanel.add(tablePanel,BorderLayout.CENTER);
		
		//tablePanel.
		title = new JLabel("Lista dostêpnych DVD");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		tablePanel.add(title,BorderLayout.NORTH);
		

		
		dvdTable = new JTable(new DefaultTableModel(new Object[][] {},
				new String[] { "Tytu\u0142", "Re\u017Cyser", "Kategoria", "Ilo\u015B\u0107" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		insertMoviesTable(getAllMovies());
	
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
