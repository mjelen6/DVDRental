import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class DVDRental {

	
	private static Logger log = Logger.getLogger(DVDRental.class);
	
	public static void main(String[] args) {


		// Prepare a logger
		BasicConfigurator.configure();
		log.debug("Application Start");

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DVDRental dvdrental = new DVDRental();
				dvdrental.createAndShowGUI();
			}
		});
		
		log.info("Main function closed");
		
	}

	private void createAndShowGUI() {
		System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());
		JFrame f = new JFrame("DVDRental");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new JPanel());
		
		f.pack();
		f.setVisible(true);
	}
	
	
}
