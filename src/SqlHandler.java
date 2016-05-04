

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 * Class provides saving to database
 * @author Maciek
 *
 */
public class SqlHandler {

	private String file;
	private Connection conn = null;
	private static Logger log = Logger.getLogger(SqlHandler.class);

	/**
	 * Create one handler to database.
	 * @param file File of database
	 */
	public SqlHandler(String file) {

		this.file = file;

		try {
			Class.forName("org.sqlite.JDBC");
			log.info("Driver was found.");

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			log.error("Missed driver. You should download driver first:  http://www.xerial.org/maven/repository/artifact/org/xerial/sqlite-jdbc/3.7.2");
			
		}
		log.debug("SqlHandler created.");
	}

	/**
	 * Method that opens database and connects with.
	 */
	public void connect() {

		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + file);
			log.info("Database connection was established.");
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
	}

	/**
	 * Method that close database update process.
	 */
	public void close() {
		try {
			conn.close();
			log.info("Connection was closed.");
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
	}

	/*
	 * Table: CHANNEL stid INTEGER NOT NULL name TEXT NOT NULL url TEXT NOT NULL
	 * PRIMARY KEY(stid)
	 * 
	 * Table: PROGRAM prid INTEGER NOT NULL name TEXT NOT NULL year INTEGER NOT
	 * NULL month INTEGER NOT NULL day INTEGER NOT NULL hour INTEGER minute
	 * INTEGER url TEXT NOT NULL info TEXT stid INTEGER NOT NULL PRIMARY
	 * KEY(prid) FOREIGN KEY(stid) REFERENCES CHANNEL(stid) ON DELETE CASCADE ON
	 * UPDATE CASCADE
	 */

	/**
	 * Method that insert channel list to database table.
	 * @param channels List of channels to insert
	 */
	public void insert(Channels channels) {

		Statement stat = null;
		try {
			stat = conn.createStatement();
			log.debug("Statement created.");
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
		try {
			log.info("Drop table...");
			stat.executeUpdate("drop table if exists CHANNEL;");
			log.info("Table dropped");
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}

		try {
			stat.execute("PRAGMA foreign_keys = ON");
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}

		try {
			stat.executeUpdate("create table CHANNEL ("
					+ "stid INTEGER NOT NULL, " + "name TEXT NOT NULL, "
					+ "url TEXT NOT NULL, " + "PRIMARY KEY(stid));");
			log.info("Table CHANNEL created");
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}

		PreparedStatement prep = null;

		try {
			prep = conn
					.prepareStatement("insert into CHANNEL values (?, ?, ?);");

			log.info("Create rows");
			for (Channel c : channels) {
				prep.setString(1, Integer.toString(c.getStid()));
				prep.setString(2, c.getName());
				prep.setString(3, c.getUrl().toString());
				prep.addBatch();
			}
			log.info("Rows created.");
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}

		try {
			log.info("ExecuteBatch");
			prep.executeBatch();
			log.info("Batch finished. Closing..");
			prep.close();
			log.info("Closed");

		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
		
		
		log.info("Channels insert completed.");
	}

	
	/**
	 * Method that insert Program list to database table named as channel.
	 * @param programs Programs to stored
	 * @param channel Channel which has that programs
	 */
	public void insert(Programs programs, Channel channel) {

		insert(programs, channel.getName().replaceAll("\\W", ""));

	}

	/**
	 * Method that insert Program list to database table named as String argument.
	 * @param programs Programs to stored
	 * @param tableName Name of table
	 */
	public void insert(Programs programs, String tableName) {

		Statement stat = null;
		try {
			stat = conn.createStatement();
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}

		try {
			log.info("Drop table " + tableName + "...");
			stat.executeUpdate("drop table if exists " + tableName + ";");
			log.info("Table dropped");
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}

		try {
			stat.execute("PRAGMA foreign_keys = ON");
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
		/*
		 * Table: PROGRAM prid INTEGER NOT NULL name TEXT NOT NULL year INTEGER
		 * NOT NULL month INTEGER NOT NULL day INTEGER NOT NULL hour INTEGER
		 * minute INTEGER url TEXT NOT NULL info TEXT PRIMARY KEY(prid)
		 */

		try {
			stat.executeUpdate("create table " + tableName + " ("
					+ "prid INTEGER NOT NULL, " + "name TEXT NOT NULL, "
					+ "year INTEGER NOT NULL, " + "month INTEGER NOT NULL, "
					+ "day INTEGER NOT NULL, " + "hour INTEGER, "
					+ "minute INTEGER, " + "url TEXT NOT NULL, "
					+ "info TEXT, " + "PRIMARY KEY(prid));");
			log.info("Table " + tableName + " created");
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}

		PreparedStatement prep = null;

		try {
			prep = conn.prepareStatement("insert into " + tableName
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?);");

			log.info("Create rows");

			for (Program p : programs) {

				prep.setString(1, Integer.toString(p.getPrid()));
				prep.setString(2, p.getName());
				prep.setString(3, Integer.toString(p.getYear()));
				prep.setString(4, Integer.toString(p.getMonth()));
				prep.setString(5, Integer.toString(p.getDay()));
				prep.setString(6, Integer.toString(p.getHour()));
				prep.setString(7, Integer.toString(p.getMinute()));
				prep.setString(8, p.getUrl().toString());
				prep.setString(9, p.getInfo());
				prep.addBatch();
			}

		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}

		try {
			log.info("ExecuteBatch");
			prep.executeBatch();
			log.info("Batch finished. Closing...");
			prep.close();
			log.info("Closed");

		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}

		log.info( tableName + " - programs inserted.");
	}

}
