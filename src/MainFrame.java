
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

import datatypes.DVD;
import datatypes.DVDList;
import datatypes.Movie;
import datatypes.MoviesList;
import datatypes.Record;
import datatypes.RecordList;
import engine.PdfCreator;
import engine.SqlHandler;
import gui.DvdTable;
import gui.DvdTableModel;
import sqlinterface.DVDRentInterface;
import sqlinterface.DVDRental;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Date;

import javax.swing.Box;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author Maciek
 *
 */
public class MainFrame extends DVDRental{

	private static Logger log = Logger.getLogger(MainFrame.class); // class logger
	private RecordList recordList;
	private Record selectedRecord;
	
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
	
	private JTextField directorField;
	private JTextField categoryField;
	private JPanel insertPanel;
	private JPanel labelPanel;
	private JPanel fieldPanel;
	private Box insertBox;
	private JLabel titleLabel;
	private JLabel directorLabel;
	private JLabel categoryLabel;
	private Box rentBox;
	private Label rentLabel;
	private JPanel rentPanel;
	private JButton button_1;
	private Component verticalStrut;
	private Component verticalStrut_1;
	private Component verticalStrut_2;
	private Component verticalStrut_4;
	private Component horizontalStrut;
	private JPanel panel;
	private JLabel label_1;
	private JLabel lblWypozyczajcy;
	private Component horizontalStrut_1;
	private JPanel panel_1;
	private JTextField selectedDvdTitleField;
	private JTextField rentUser;
	private Component verticalStrut_5;
	private Component verticalStrut_6;
	
	
	
	
	public MainFrame(DVDRentInterface dvdRentInterface) {
		super(dvdRentInterface);
		recordList = getAllRecords();
	}
	
	
	private ActionListener addDvdListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {

			log.trace("Adding new DVD");
			addDvd();
			dvdTable.eraseTable();
			
			recordList = getAllRecords();
			dvdTable.insertTable(recordList);
		}
	};
	

	private ActionListener searchListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {

			log.debug("Search button pressed");
			
			String searchPhrase = searchTextField.getText();
			

			if (searchPhrase.isEmpty()) 
				recordList = getAllRecords();
			else{
				recordList = searchMovies(searchPhrase);
			}
			
			dvdTable.eraseTable();
			dvdTable.insertTable(recordList);
		}
	};	
	
	
	private ListSelectionListener dvdSelectionListener = new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent event) {
			if (!event.getValueIsAdjusting()) {
				if (dvdTable.getSelectedRow() > -1) {
					// print first column value from selected row
					
					selectedRecord = recordList.get(dvdTable.getSelectedRow());
					
					int selectedDvdId = selectedRecord.getDvd().getDvdId();
					String selectedDvdTitle = selectedRecord.getMovie().getTitle();
					
					
					selectedDvdTitleField.setText(selectedDvdTitle);
					log.debug(selectedDvdId + " " + selectedDvdTitle + " selected");					
				}
			}
		}
	};
	private JButton button;
	
	
	
	
	
	private boolean addDvd() {

		String title = titleField.getText();
		String director = directorField.getText();
		String category = categoryField.getText();

		boolean result = false;

		if (title.isEmpty() || director.isEmpty() || category.isEmpty()) {
			
			log.trace("Empty field");
			JOptionPane.showMessageDialog(frame, "�adne pole nie mo�e by� puste");	
		} 
		else {

			log.trace("Inserting new movie: " + title + " " + " " + director + " " + category);
			result = insertMovie(new Movie(title, director, category));

			if (result == true) {
				log.debug("New movie inserted");
			} else {
				log.error("Error during movie insertion or movie already exist");
				//JOptionPane.showMessageDialog(frame, "Film o podanym tytule ju� istnieje");
			}
			
			log.trace("Getting movie ID");
			Movie movie = findMovieByTitle(title);
			
			result = insertDvd(new DVD(movie.getMid()));
			if(result == false)
				log.error("Error during dvd insertion");
			
		}
		return result;
	}
	
	
	private RecordList searchMovies(String titleOrDirector) {

		log.debug("Searching for movies");
		log.debug("Search for: " + titleOrDirector);

		RecordList recordList = new RecordList();
		Movie movie = findMovieByTitle(titleOrDirector);

		if (movie != null) {
			DVDList dvdList = findDvdByTitle(movie.getTitle());

			if (dvdList.size() != 0) {
				for (DVD dvd : dvdList) {
					recordList.add(new Record(movie, dvd));
				}
			}

		}

		log.debug(recordList.size() + " movies found");
		return recordList;
	}
	
	
	private RecordList getAllRecords() {
		
		RecordList recordList = new RecordList();
		MoviesList moviesList = getAllMovies();
		
		if(moviesList.size() != 0){
		
			for(Movie movie : moviesList){
				DVDList dvdList = findDvdByTitle(movie.getTitle());
				
				if(dvdList.size() != 0){
					for( DVD dvd : dvdList){
						recordList.add(new Record(movie,dvd));
					}
				}
				
			}
		}
		
		return recordList;
	}
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

	
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		
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
		searchTextField.setMaximumSize(searchTextField.getPreferredSize());
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

		insertLabel = new Label("Dodaj DVD");
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

		horizontalStrut = Box.createHorizontalStrut(1);
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
		titleField.addActionListener(addDvdListener);

		directorField = new JTextField();
		fieldPanel.add(directorField);
		directorField.setMaximumSize(new Dimension(166, 20));
		directorField.setHorizontalAlignment(SwingConstants.LEFT);
		directorField.setColumns(20);
		directorField.addActionListener(addDvdListener);

		categoryField = new JTextField();
		fieldPanel.add(categoryField);
		categoryField.setMaximumSize(new Dimension(166, 20));
		categoryField.setHorizontalAlignment(SwingConstants.LEFT);
		categoryField.setColumns(20);
		categoryField.addActionListener(addDvdListener);

		addButton = new JButton("Dodaj");
		addButton.addActionListener(addDvdListener);

		insertBox.add(addButton);
		addButton.setAlignmentX(0.5f);

		verticalStrut_1 = Box.createVerticalStrut(20);
		sideBar.add(verticalStrut_1);

		rentBox = Box.createVerticalBox();
		rentBox.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		sideBar.add(rentBox);

		rentLabel = new Label("Wypo\u017Cycz DVD");
		rentLabel.setFont(new Font("Arial", Font.BOLD, 16));
		rentBox.add(rentLabel);

		rentPanel = new JPanel();
		rentBox.add(rentPanel);
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

		lblWypozyczajcy = new JLabel("Wypo\u017Cyczaj\u0105cy");
		lblWypozyczajcy.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblWypozyczajcy);

		horizontalStrut_1 = Box.createHorizontalStrut(5);
		rentPanel.add(horizontalStrut_1, BorderLayout.CENTER);

		panel_1 = new JPanel();
		rentPanel.add(panel_1, BorderLayout.EAST);
		GridLayout gl_panel_1 = new GridLayout(2, 1);
		gl_panel_1.setVgap(1);
		gl_panel_1.setHgap(1);
		panel_1.setLayout(gl_panel_1);

		selectedDvdTitleField = new JTextField();
		selectedDvdTitleField.setEditable(false);
		selectedDvdTitleField.setMaximumSize(new Dimension(166, 20));
		selectedDvdTitleField.setHorizontalAlignment(SwingConstants.LEFT);
		selectedDvdTitleField.setColumns(20);
		panel_1.add(selectedDvdTitleField);

		rentUser = new JTextField();
		rentUser.setMaximumSize(new Dimension(166, 20));
		rentUser.setHorizontalAlignment(SwingConstants.LEFT);
		rentUser.setColumns(20);
		panel_1.add(rentUser);

		button_1 = new JButton("Wypo�ycz");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.setAlignmentX(0.5f);
		rentBox.add(button_1);

		verticalStrut_2 = Box.createVerticalStrut(20);
		sideBar.add(verticalStrut_2);
		
		button = new JButton("Zapisz");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir"));
				jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jFileChooser.setFileFilter(new FileNameExtensionFilter("PDF files (*.pdf)", "pdf"));
				
				if (jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

					File file = jFileChooser.getSelectedFile();
					String fname = file.getAbsolutePath();
					 
					if(!fname.endsWith(".pdf") ) 
		                file = new File(fname + ".pdf");
							
					PdfCreator pdfCreator = new PdfCreator(file.getName(), "Tabela");
					pdfCreator.createRentalPdf(recordList);
				}	
			}
		});
		button.setAlignmentX(0.5f);
		sideBar.add(button);

		verticalStrut_5 = Box.createVerticalStrut(20);
		sideBar.add(verticalStrut_5);

		verticalStrut_6 = Box.createVerticalStrut(20);
		sideBar.add(verticalStrut_6);

		// Create tablepanel
		tablePanel = new JPanel(new BorderLayout());
		mainPanel.add(tablePanel, BorderLayout.CENTER);

		// tablePanel.
		title = new JLabel("Lista dost�pnych DVD");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		tablePanel.add(title, BorderLayout.NORTH);

		
		
		dvdTable = new DvdTable(new DvdTableModel(recordList));
		dvdTable.getSelectionModel().addListSelectionListener(dvdSelectionListener);

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
