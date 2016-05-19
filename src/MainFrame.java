import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class MainFrame {

	private static Logger log = Logger.getLogger(MainFrame.class); // class logger
	
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel sideBar;
	private JPanel tablePanel;
	private JTabbedPane movie;
	private JLabel title;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		log.debug("Application Start");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame mainFrame = new MainFrame();
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create "background" panel
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(960, 540));
		mainPanel.setMinimumSize(new Dimension(640, 360));
		
		// Create sidebar
		sideBar = new JPanel();
		sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
		sideBar.setBorder(BorderFactory.createTitledBorder("Menu boczne"));
		sideBar.add(new Label("lista"), BorderLayout.EAST);
		mainPanel.add(sideBar, BorderLayout.WEST);
		
		// Create tablepanel
		tablePanel = new JPanel(new BorderLayout());
		mainPanel.add(tablePanel,BorderLayout.EAST);
		
		//tablePanel.
		title = new JLabel("Lista dostêpnych DVD");
		tablePanel.add(title,BorderLayout.NORTH);
		
		
		// content panes must be opaque
		mainPanel.setOpaque(true);
		frame.setContentPane(mainPanel);
		frame.setMinimumSize(frame.getMinimumSize());

		// Display the window.
		frame.pack();
		frame.setVisible(true);

		
	}

}
