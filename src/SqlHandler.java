

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Class provides saving to database
 * @author Maciek
 *
 */
public class SqlHandler {
	private Connection conn = null;
	private Statement state; 
	private static Logger log = Logger.getLogger(SqlHandler.class);

	/**
	 * Create one handler to database.
	 * @param file File of database
	 */
	public SqlHandler() {

		try {
			Class.forName("org.sqlite.JDBC");
			log.info("Driver was found.");

		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			log.error("Missed driver. You should download driver first:  http://www.xerial.org/maven/repository/artifact/org/xerial/sqlite-jdbc/3.7.2");
			
		}
//		log1.debug("SqlHandler created.");
		
		connect();
		createTables();
	}

	/**
	 * Method that opens database and connects with.
	 */
	public void connect() {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
			log.info("Database connection was established.");
		} catch (SQLException e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
		try {
			state = conn.createStatement();
			log.debug("Statement created.");
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

	public boolean createTables(){
		String createCategories = "CREATE TABLE IF NOT EXISTS categories (cid INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(255))"; 
		String createMoviesList = "CREATE TABLE IF NOT EXISTS movies_list (mid INTEGER PRIMARY KEY AUTOINCREMENT, cid integer, director varchar(255), name varchar(255))"; 
		String createDvdList = "CREATE TABLE IF NOT EXISTS dvd_list (dvd_id INTEGER PRIMARY KEY AUTOINCREMENT, mid integer, lent integer )"; 
		String createLoanList = "CREATE TABLE IF NOT EXISTS loan_list (loan_id INTEGER PRIMARY KEY AUTOINCREMENT, dvd_id integer, user_name varchar(255), user_surname varchar(255), lent_date varchar(255), return_date varchar(255) )"; 
		try {
			state.execute(createCategories);
			state.execute(createMoviesList);
			state.execute(createDvdList);
			state.execute(createLoanList);
		} catch (SQLException e) {
			 System.err.println("Blad przy tworzeniu tabeli");
	            e.printStackTrace();
	            return false;
		}
		return true;
	}
	
	
	
	public boolean insertMovie(int cid, String director, String name){
		 try {
	            PreparedStatement prepStmt = conn.prepareStatement(
	                    "insert into movies_list values (NULL, ?, ?, ?);");
	            prepStmt.setInt(1, cid);
	            prepStmt.setString(2, director);
	            prepStmt.setString(3, name);
	            prepStmt.execute();
	        } catch (SQLException e) {
	            System.err.println("Blad przy wstawianiu czytelnika");
	            e.printStackTrace();
	            return false;
	        }
		return true;
	}
	public boolean insertCategories(String name){
		 try {
	            PreparedStatement prepStmt = conn.prepareStatement(
	                    "insert into categories values (NULL, ?);");
	            prepStmt.setString(1, name);
	            prepStmt.execute();
	        } catch (SQLException e) {
	            System.err.println("Blad przy wstawianiu czytelnika");
	            e.printStackTrace();
	            return false;
	        }
		return true;
	}
	
	public List<Movie> getMovies(){
		List<Movie> movies = new LinkedList<Movie>();
		try {
			ResultSet result = state.executeQuery("SELECT * FROM movies_list");
			int mid;
			int cid; 
			String name;
			String director; 
			while(result.next()){
				mid = result.getInt("mid");
				cid = result.getInt("cid");
				name = result.getString("name");
				director = result.getString("director");
				movies.add(new Movie(mid, cid, name, director));
			}
		} catch (Exception e) {
			return null;
		}
		return movies;
	}
	
	public Movie findMovieByID(int mid){
		Movie movie;
		try {
			ResultSet result = state.executeQuery("SELECT * FROM movies_list");
			int tempMid;
			int tempCid; 
			String tempName;
			String tempDirector; 
			while(result.next()){
				tempMid = result.getInt("mid");
				if(tempMid == mid){
					tempCid = result.getInt("cid");
					tempName = result.getString("name");
					tempDirector = result.getString("director");
					movie = new Movie(tempMid, tempCid, tempName, tempDirector);
					return movie;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return movie = new Movie();
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
//	public void insert(Channels channels) {
//
//		
//		try {
//			log.info("Drop table...");
//			stat.executeUpdate("drop table if exists CHANNEL;");
//			log.info("Table dropped");
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			log.error(e.getStackTrace());
//		}
//
//		try {
//			stat.execute("PRAGMA foreign_keys = ON");
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			log.error(e.getStackTrace());
//		}
//
//		try {
//			stat.executeUpdate("create table CHANNEL ("
//					+ "stid INTEGER NOT NULL, " + "name TEXT NOT NULL, "
//					+ "url TEXT NOT NULL, " + "PRIMARY KEY(stid));");
//			log.info("Table CHANNEL created");
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			log.error(e.getStackTrace());
//		}
//
//		PreparedStatement prep = null;
//
//		try {
//			prep = conn
//					.prepareStatement("insert into CHANNEL values (?, ?, ?);");
//
//			log.info("Create rows");
//			for (Channel c : channels) {
//				prep.setString(1, Integer.toString(c.getStid()));
//				prep.setString(2, c.getName());
//				prep.setString(3, c.getUrl().toString());
//				prep.addBatch();
//			}
//			log.info("Rows created.");
//			
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			log.error(e.getStackTrace());
//		}
//
//		try {
//			log.info("ExecuteBatch");
//			prep.executeBatch();
//			log.info("Batch finished. Closing..");
//			prep.close();
//			log.info("Closed");
//
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			log.error(e.getStackTrace());
//		}
//		
//		
//		log.info("Channels insert completed.");
//	}
//
	
	/**
	 * Method that insert Program list to database table named as channel.
	 * @param programs Programs to stored
	 * @param channel Channel which has that programs
	 */
/*	public void insert(Programs programs, Channel channel) {

		insert(programs, channel.getName().replaceAll("\\W", ""));

	}
*/
	/**
	 * Method that insert Program list to database table named as String argument.
	 * @param programs Programs to stored
	 * @param tableName Name of table
	 */
//	public void insert(Programs programs, String tableName) {
//
//		Statement stat = null;
//		try {
//			stat = conn.createStatement();
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			log.error(e.getStackTrace());
//		}
//
//		try {
//			log.info("Drop table " + tableName + "...");
//			stat.executeUpdate("drop table if exists " + tableName + ";");
//			log.info("Table dropped");
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			log.error(e.getStackTrace());
//		}
//
//		try {
//			stat.execute("PRAGMA foreign_keys = ON");
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			log.error(e.getStackTrace());
//		}
//		/*
//		 * Table: PROGRAM prid INTEGER NOT NULL name TEXT NOT NULL year INTEGER
//		 * NOT NULL month INTEGER NOT NULL day INTEGER NOT NULL hour INTEGER
//		 * minute INTEGER url TEXT NOT NULL info TEXT PRIMARY KEY(prid)
//		 */
//
//		try {
//			stat.executeUpdate("create table " + tableName + " ("
//					+ "prid INTEGER NOT NULL, " + "name TEXT NOT NULL, "
//					+ "year INTEGER NOT NULL, " + "month INTEGER NOT NULL, "
//					+ "day INTEGER NOT NULL, " + "hour INTEGER, "
//					+ "minute INTEGER, " + "url TEXT NOT NULL, "
//					+ "info TEXT, " + "PRIMARY KEY(prid));");
//			log.info("Table " + tableName + " created");
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			log.error(e.getStackTrace());
//		}
//
//		PreparedStatement prep = null;
//
//		try {
//			prep = conn.prepareStatement("insert into " + tableName
//					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?);");
//
//			log.info("Create rows");
//
//			for (Program p : programs) {
//
//				prep.setString(1, Integer.toString(p.getPrid()));
//				prep.setString(2, p.getName());
//				prep.setString(3, Integer.toString(p.getYear()));
//				prep.setString(4, Integer.toString(p.getMonth()));
//				prep.setString(5, Integer.toString(p.getDay()));
//				prep.setString(6, Integer.toString(p.getHour()));
//				prep.setString(7, Integer.toString(p.getMinute()));
//				prep.setString(8, p.getUrl().toString());
//				prep.setString(9, p.getInfo());
//				prep.addBatch();
//			}
//
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			log.error(e.getStackTrace());
//		}
//
//		try {
//			log.info("ExecuteBatch");
//			prep.executeBatch();
//			log.info("Batch finished. Closing...");
//			prep.close();
//			log.info("Closed");
//
//		} catch (SQLException e) {
//			log.error(e.getMessage());
//			log.error(e.getStackTrace());
//		}
//
//		log.info( tableName + " - programs inserted.");
//	}

}
