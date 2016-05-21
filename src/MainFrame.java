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
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;

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
	private Label insertLabel;
	private JTextField titleField;
	private JButton addButton;
	
	private MoviesList movies;
	private JTextField directorField;
	private JTextField categoryField;
	private JPanel insertPanel;
	private JPanel labelPanel;
	private JPanel fieldPanel;
	private Box insertBox;
	private JLabel titleLabel;
	private JLabel directorLabel;
	private JLabel categoryLabel;
	private Box verticalBox;
	private Label label;
	private JPanel panel;
	private JButton button_1;
	private Component verticalStrut;
	private Component verticalStrut_1;
	private Component verticalStrut_2;
	private Component verticalStrut_3;
	private Component verticalStrut_4;
	
	public MainFrame(DVDRentInterface dvdRentInterface) {
		super(dvdRentInterface);
	}
	
	
	private void insertRowInMoviesTable(Movie movie) {
				
		DefaultTableModel model = (DefaultTableModel) dvdTable.getModel();
		model.addRow(new Object[] { movie.getName(), movie.getDirector(), movie.getCategory(), 153 });
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
		sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
		
		searchBox = Box.createVerticalBox();
		searchBox.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		searchBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		sideBar.add(searchBox);
		Label searchLabel = new Label("Szukaj");
		searchLabel.setFont(new Font("Arial", Font.BOLD, 16));
		searchBox.add(searchLabel);
		
		
		searchTextField = new JTextField();
		searchTextField.setColumns(25);
		searchBox.add(searchTextField);
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
		
		verticalStrut = Box.createVerticalStrut(20);
		sideBar.add(verticalStrut);
		
		insertBox = Box.createVerticalBox();
		insertBox.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		sideBar.add(insertBox);
		
		insertLabel = new Label("Dodaj film");
		insertBox.add(insertLabel);
		insertLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		insertPanel = new JPanel();
		insertBox.add(insertPanel);
		insertPanel.setLayout(new BorderLayout(0, 0));
		
		labelPanel = new JPanel();
		insertPanel.add(labelPanel, BorderLayout.WEST);
		GridLayout gl_labelPanel = new GridLayout(3, 1);
		gl_labelPanel.setVgap(1);
		gl_labelPanel.setHgap(1);
		labelPanel.setLayout(gl_labelPanel);
		
		titleLabel = new JLabel("Tytu\u0142");
		titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(titleLabel);
		
		directorLabel = new JLabel("Re\u017Cyser");
		directorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(directorLabel);
		
		categoryLabel = new JLabel("Kategoria");
		categoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(categoryLabel);
		
		fieldPanel = new JPanel();
		insertPanel.add(fieldPanel, BorderLayout.EAST);
		GridLayout gl_fieldPanel = new GridLayout(3, 1);
		gl_fieldPanel.setVgap(1);
		gl_fieldPanel.setHgap(1);
		fieldPanel.setLayout(gl_fieldPanel);
		
		titleField = new JTextField();
		fieldPanel.add(titleField);
		titleField.setMaximumSize(new Dimension(166, 20));
		titleField.setHorizontalAlignment(SwingConstants.LEFT);
		titleField.setColumns(20);
		
		directorField = new JTextField();
		fieldPanel.add(directorField);
		directorField.setMaximumSize(new Dimension(166, 20));
		directorField.setHorizontalAlignment(SwingConstants.LEFT);
		directorField.setColumns(20);
		
		categoryField = new JTextField();
		fieldPanel.add(categoryField);
		categoryField.setMaximumSize(new Dimension(166, 20));
		categoryField.setHorizontalAlignment(SwingConstants.LEFT);
		categoryField.setColumns(20);
		
		addButton = new JButton("Dodaj");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
			}
		});
		insertBox.add(addButton);
		addButton.setAlignmentX(0.5f);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		sideBar.add(verticalStrut_1);
		
		verticalBox = Box.createVerticalBox();
		sideBar.add(verticalBox);
		
		label = new Label("Dodaj film");
		label.setFont(new Font("Arial", Font.BOLD, 16));
		verticalBox.add(label);
		
		panel = new JPanel();
		verticalBox.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		button_1 = new JButton("Szukaj");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.setAlignmentX(0.5f);
		verticalBox.add(button_1);
		
		verticalStrut_2 = Box.createVerticalStrut(20);
		sideBar.add(verticalStrut_2);
		
		verticalStrut_3 = Box.createVerticalStrut(20);
		sideBar.add(verticalStrut_3);
		
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
		
		verticalStrut_4 = Box.createVerticalStrut(20);
		tablePanel.add(verticalStrut_4, BorderLayout.SOUTH);
	
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
