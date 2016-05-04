import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class DVDrent {

	
	private static Logger log = Logger.getLogger(DVDrent.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Prepare a logger
		BasicConfigurator.configure();
		log.debug("Application Start");

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DVDrent dvdrent = new DVDrent();
				dvdrent.createAndShowGUI();
			}
		});
		

		log.info("Main function closed");
	}

	private void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new JPanel());
        f.pack();
        f.setVisible(true);
    }
	
	
}
