import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.border.EtchedBorder;

/**
 * 
 * @author Maciek
 *
 */
public class MainFrame extends DVDRental{

	private static Logger log = Logger.getLogger(MainFrame.class); // class logger
	
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel sideBar;
	private JPanel tablePanel;
	private JLabel title;
	private DvdTable dvdTable;
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
	private JPanel rentPanel;
	private JButton button_1;
	private Component verticalStrut;
	private Component verticalStrut_1;
	private Component verticalStrut_2;
	private Component verticalStrut_3;
	private Component verticalStrut_4;
	
	public MainFrame(DVDRentInterface dvdRentInterface) {
		super(dvdRentInterface);
	}
	
	
	private ActionListener addMovieListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {

			log.trace("Adding new movie");
			addMovie();
			dvdTable.eraseTable();
			dvdTable.insertTable(getAllMovies());
		}
	};
	

	private ActionListener searchListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {

			log.debug("Search button pressed");
			movies = searchMovies(searchTextField.getText());
			dvdTable.eraseTable();
			dvdTable.insertTable(movies);
		}
	};	
	private Component horizontalStrut;
	private JPanel panel;
	private JLabel label_1;
	private JLabel label_2;
	private Component horizontalStrut_1;
	private JPanel panel_1;
	private JTextField textField;
	private JTextField textField_1;
	private Component verticalStrut_5;
	private Component verticalStrut_6;
	
	
	

	

	
	
	
	
	private boolean addMovie() {
		
		String title = titleField.getText();
		String director = directorField.getText();
		String category = categoryField.getText();
		
		boolean result = false;
		
		log.trace("Inserting new movie: " + title + " " + " " + director + " " + category);
		
		if (title.isEmpty() || director.isEmpty() || category.isEmpty()) {
			log.trace("Empty field");
			JOptionPane.showMessageDialog(frame, "¯adne pole nie mo¿e byæ puste");
			
		} else {
			
			result = insertMovie(new Movie(title, director, category));
			
			if(result == true){
				log.debug("New movie inserted");	
			}
			else {
				log.debug("Error during insertion or movie already exist");
				JOptionPane.showMessageDialog(frame, "Film o podanym tytule ju¿ istnieje");
			}
			
		}
		
		return result;
	}
	
	
	private MoviesList searchMovies(String nameOrDirector) {
		
		log.debug("Searching for movies");
		log.debug("Search for: " + nameOrDirector);
		
		MoviesList moviesList = null;
		
		if(nameOrDirector.isEmpty()){
			moviesList = getAllMovies();		
		}
	
		else {
			moviesList = new MoviesList();
			
			Movie movie = findMovieByTitle(nameOrDirector);
			
			if(movie != null)
				moviesList.add(movie);
		}
		
		log.debug(moviesList.size() + " movies found");
		
		return moviesList;
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
		searchTextField.addActionListener(searchListener);
		

		
		searchButton = new JButton("Szukaj");
		searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		searchBox.add(searchButton);
		searchButton.addActionListener(searchListener);
		
		
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
		
		horizontalStrut = Box.createHorizontalStrut(5);
		insertPanel.add(horizontalStrut, BorderLayout.CENTER);
		
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
		titleField.addActionListener(addMovieListener);
		
		directorField = new JTextField();
		fieldPanel.add(directorField);
		directorField.setMaximumSize(new Dimension(166, 20));
		directorField.setHorizontalAlignment(SwingConstants.LEFT);
		directorField.setColumns(20);
		directorField.addActionListener(addMovieListener);
		
		categoryField = new JTextField();
		fieldPanel.add(categoryField);
		categoryField.setMaximumSize(new Dimension(166, 20));
		categoryField.setHorizontalAlignment(SwingConstants.LEFT);
		categoryField.setColumns(20);
		categoryField.addActionListener(addMovieListener);
		
		addButton = new JButton("Dodaj");
		addButton.addActionListener(addMovieListener);
				
		insertBox.add(addButton);
		addButton.setAlignmentX(0.5f);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		sideBar.add(verticalStrut_1);
		
		verticalBox = Box.createVerticalBox();
		sideBar.add(verticalBox);
		
		label = new Label("Wypo\u017Cycz");
		label.setFont(new Font("Arial", Font.BOLD, 16));
		verticalBox.add(label);
		
		rentPanel = new JPanel();
		verticalBox.add(rentPanel);
		rentPanel.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		rentPanel.add(panel, BorderLayout.WEST);
		GridLayout gl_panel = new GridLayout(2, 1);
		gl_panel.setVgap(1);
		gl_panel.setHgap(1);
		panel.setLayout(gl_panel);
		
		label_1 = new JLabel("Tytu\u0142");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(label_1);
		
		label_2 = new JLabel("Re\u017Cyser");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(label_2);
		
		horizontalStrut_1 = Box.createHorizontalStrut(5);
		rentPanel.add(horizontalStrut_1, BorderLayout.CENTER);
		
		panel_1 = new JPanel();
		rentPanel.add(panel_1, BorderLayout.EAST);
		GridLayout gl_panel_1 = new GridLayout(2, 1);
		gl_panel_1.setVgap(1);
		gl_panel_1.setHgap(1);
		panel_1.setLayout(gl_panel_1);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setMaximumSize(new Dimension(166, 20));
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setColumns(20);
		panel_1.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setMaximumSize(new Dimension(166, 20));
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setColumns(20);
		panel_1.add(textField_1);
		
		button_1 = new JButton("Wypo¿ycz");
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
		
		verticalStrut_5 = Box.createVerticalStrut(20);
		sideBar.add(verticalStrut_5);
		
		verticalStrut_6 = Box.createVerticalStrut(20);
		sideBar.add(verticalStrut_6);
		
		// Create tablepanel
		tablePanel = new JPanel(new BorderLayout());
		mainPanel.add(tablePanel,BorderLayout.CENTER);
		
		//tablePanel.
		title = new JLabel("Lista dostêpnych DVD");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		tablePanel.add(title,BorderLayout.NORTH);
		

		
		dvdTable =  new DvdTable(new DefaultTableModel(new Object[][] {},
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

		dvdTable.insertTable(getAllMovies());
		
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
