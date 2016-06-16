package engine;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import org.apache.log4j.Logger;

import datatypes.DVD;
import datatypes.DVDList;
import datatypes.Movie;
import datatypes.MoviesList;
import sqlinterface.DVDRentInterface;

/**
 * Class provides saving to database
 * @author Maciek
 *
 */
public class SqlHandler implements DVDRentInterface{
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
		String createMoviesList = "CREATE TABLE IF NOT EXISTS movies_list (mid INTEGER PRIMARY KEY AUTOINCREMENT, title varchar(128), director varchar(128), category varchar(128),  UNIQUE (title) )"; 
		String createDvdList = "CREATE TABLE IF NOT EXISTS dvd_list (dvd_id INTEGER PRIMARY KEY AUTOINCREMENT, mid integer, available boolean, user_name varchar(255), lent_date date)"; 
		
		try {
			state.execute(createMoviesList);
			state.execute(createDvdList);
		} catch (SQLException e) {
			log.error("Blad przy tworzeniu tabeli");
			log.error(e.getMessage());
			log.error(e.getStackTrace());		
			return false;
		}
		log.info("Database ready");
		return true;
	}
	
	
	
	
	@Override
	public boolean insertMovie(Movie movie) {
		try {
			
			String statement = "insert into movies_list values (NULL, ?, ?, ?);";

			PreparedStatement prepStmt = conn.prepareStatement(statement);
			prepStmt.setString(1, movie.getTitle());
			prepStmt.setString(2, movie.getDirector());
			prepStmt.setString(3, movie.getCategory());
			prepStmt.execute();
				
		} catch (SQLException e) {
			log.error("movie " + movie.getTitle() + " alredy exist");
			return false;
		}
		
		log.info("Movie added " + movie.getTitle());
		return true;
	}
	
	@Override
	public boolean updateMovie(Movie movie) {

//		movies_list
//		mid INTEGER PRIMARY KEY AUTOINCREMENT, 
//		category varchar(128), 
//		director varchar(128), 
//		name varchar(128), 
//		UNIQUE (name) 		
		

		try {
		
			String query = "update movies_list set title = ?, director = ?, category = ?  where mid = ?;";	
			
			PreparedStatement prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, movie.getTitle());
			prepStmt.setString(2, movie.getDirector());
			prepStmt.setString(3, movie.getCategory());			
			prepStmt.setInt(4, movie.getMid());
			
			prepStmt.executeUpdate();
			
		} catch (SQLException e) {
			log.error("Error removing movie " + movie.getMid());
			return false;
		}

		log.info("Movie " + movie.getMid() + " removed from movies_list");
		return true;
	}
	
	
	@Override
	public boolean deleteMovie(Movie movie) {

//		movies_list
//		mid INTEGER PRIMARY KEY AUTOINCREMENT, 
//		category varchar(128), 
//		director varchar(128), 
//		name varchar(128), 
//		UNIQUE (name) 		
		

		try {
		
			String query = "delete from movies_list where mid = ?;";
			
			PreparedStatement prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, movie.getMid());
			prepStmt.executeUpdate();			
			
			
		} catch (SQLException e) {
			log.error("Error removing movie " + movie.getMid());
			return false;
		}

		log.info("Movie " + movie.getMid() + " removed from movies_list");
		return true;
	}
	
	

	@Override
	public boolean insertDvd(DVD dvd) {
		try {
			
			// dvd_list
			// dvd_id INTEGER PRIMARY KEY AUTOINCREMENT,
			// mid integer,
			// available boolean,
			// user_name varchar(255),
			// lent_date date)

			String statement = "insert into dvd_list values (NULL, ?, ?, ?, ?);";

			PreparedStatement prepStmt = conn.prepareStatement(statement);
			prepStmt.setInt(1, dvd.getMid());
			prepStmt.setBoolean(2, dvd.isAvailable());
			prepStmt.setString(3, dvd.getUserName());
			prepStmt.setDate(4, dvd.getLentDate());
			prepStmt.execute();

		} catch (SQLException e) {
			log.error("Blad przy wstawianiu plyty dvd - mid: " + dvd.getMid());
			e.printStackTrace();
			return false;
		}

		log.info("DVD with mid: " + dvd.getMid() + " added");
		return true;
	}
	
	
	@Override
	public MoviesList getAllMovies() {

		MoviesList movies = new MoviesList();

		try {
			ResultSet result = state.executeQuery("SELECT * FROM movies_list");

			while (result.next()) {

				int mid = result.getInt("mid");
				String title = result.getString("title");
				String director = result.getString("director");
				String category = result.getString("category");

				movies.add(new Movie(mid, title, director, category));
				log.info("wyjeto film z bazy");
			}

		} catch (Exception e) {
			log.error("Blad przy wzieciu wszystkich filmow");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			return null;
		}
		return movies;
	}

	@Override
	public DVDList getAllDvds() {
		
		DVDList dvdList = new DVDList();
		
		try {
			ResultSet result = state.executeQuery("SELECT * FROM dvd_list");
			while (result.next()) {
				
				int dvdId = result.getInt("dvd_id");
				int mid = result.getInt("mid");
				Boolean available = result.getBoolean("available");
				String userName = result.getString("user_name");
				Date lentDate = result.getDate("lent_date");

				dvdList.add(new DVD(dvdId, mid, available, userName, lentDate));
				log.info("wyciagnieto dvd");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("blad przy wyci¹ganiu dvd");
			return null;
		}
		return dvdList;
	}
	
	@Override
	public int countAvaliableDvd(Movie movie) {

		int counter = 0;

		try {
			String query = "SELECT count(*) FROM dvd_list where mid = ? and available = ?";	
			
			PreparedStatement prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, movie.getMid());
			prepStmt.setBoolean(1, true);
			ResultSet result = state.executeQuery(query);

			while (result.next()) {
				counter = result.getInt("count");
			}

		} catch (Exception e) {
			log.error("blad przy liczeniu dostepnych dvd");
			e.printStackTrace();
		}
		return counter;
	}
	
	
	
	
	public Movie findMovieByID(int mid) {

		Movie movie = null;

		try {

			String query = "SELECT * FROM movies_list where mid = ?";
			PreparedStatement prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, mid);
			ResultSet result = prepStmt.executeQuery();
			
			
			int tempMid;
			String tempCategory;
			String tempTitle;
			String tempDirector;
			
			while (result.next()) {
				
				tempMid = result.getInt("mid");
				tempTitle = result.getString("title");
				tempDirector = result.getString("director");
				tempCategory = result.getString("category");
				movie = new Movie(tempMid, tempTitle, tempDirector, tempCategory);
				return movie;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("blad przy wyci¹ganiu dvd");
			return null;
		}
		return movie;
	}
	
	
	public DVD findDvdByID(int dvdID) {

		// dvd_list
		// dvd_id INTEGER PRIMARY KEY AUTOINCREMENT,
		// mid integer,
		// available boolean,
		// user_name varchar(255),
		// lent_date date)

		DVD dvd = null;

		try {
			ResultSet result = state.executeQuery("SELECT * FROM dvd_list where dvd_id = " + dvdID);

			int tempDvdID;
			int tempMid;
			boolean tempAvailable;
			String tempUserName;
			Date tempDate;

			while (result.next()) {
				tempDvdID = result.getInt("dvd_id");
				tempMid = result.getInt("mid");
				tempAvailable = result.getBoolean("available");
				tempUserName = result.getString("user_name");
				tempDate = result.getDate("lent_date");
				dvd = new DVD(tempDvdID, tempMid, tempAvailable, tempUserName, tempDate);
				return dvd;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("blad przy wyci¹ganiu dvd");
			return null;
		}
		return dvd;
	}
	
	
	@Override
	public Movie findMovieByTitle(String title) {

		Movie movie = null;

		try {

			String query = "select * from movies_list where UPPER(title) = UPPER(?)";

			PreparedStatement prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, title);
			ResultSet result = prepStmt.executeQuery();

			while (result.next()) {

				int tempMid = result.getInt("mid");
				String tempTitle = result.getString("title");
				String tempDirector = result.getString("director");
				String tempCategory = result.getString("category");

				movie = new Movie(tempMid, tempTitle, tempDirector, tempCategory);
				log.info("Movie found");
				return movie;

			}
		} catch (Exception e) {
			log.error("Error during movie searching");
			e.printStackTrace();
			return null;
		}
		return movie;
	}

	
	public DVDList findDvdByTitle(String title){
		
		DVDList dvdList = new DVDList();
		
		try {
			
			String query = "SELECT * from dvd_list where mid = ( Select mid from movies_list where UPPER(title) = UPPER(?))";
			PreparedStatement prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, title);
			ResultSet result = prepStmt.executeQuery();
			
			

			
			while(result.next()){
				int dvdId = result.getInt("dvd_id");
				int mid = result.getInt("mid");
				Boolean available = result.getBoolean("available");
				String userName = result.getString("user_name");
				Date lentDate = result.getDate("lent_date");
				dvdList.add(new DVD(dvdId, mid, available, userName, lentDate));
				log.info("found dvd " + title + " " + dvdId );
			}
		} catch (Exception e) {
			log.error("Error during searching dvd by title");
			e.printStackTrace();
		}
		return dvdList;
	}
	
	
	
	public boolean rentDVD(DVD dvd, String user) {
		
//		dvd_list
//			dvd_id INTEGER PRIMARY KEY AUTOINCREMENT,
//			mid integer,
//			available boolean,
//			user_name varchar(255),
//			lent_date date)

		try {
		
			String query = "update dvd_list set available = 0, user_name = ?, lent_date = ? where dvd_id = ?";
			
			PreparedStatement prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, user);
			
			java.sql.Date ourJavaDateObject = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			prepStmt.setDate(2, ourJavaDateObject);
			prepStmt.setInt(3, dvd.getDvdId());
			prepStmt.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
			e.getErrorCode();
			log.error("Error during renting DVD " + dvd.getDvdId());
			return false;
		}

		log.info("DVD " + dvd.getDvdId() + " rent");
		return true;

	}
	
	
	
	public boolean returnDVD(DVD dvd) {

//		dvd_list
//			dvd_id INTEGER PRIMARY KEY AUTOINCREMENT,
//			mid integer,
//			available boolean,
//			user_name varchar(255),
//			lent_date date)

		try {
		
			String query = "update dvd_list set available = 1, user_name = null, lent_date = null where dvd_id = ?";
			
			PreparedStatement prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, dvd.getDvdId());
			prepStmt.executeUpdate();			
			

			
			
		} catch (SQLException e) {
			log.error("Error during returning DVD " + dvd.getDvdId());
			return false;
		}

		log.info("DVD " + dvd.getDvdId() + " returned");
		return true;

	}
	
	@Override
	public boolean deleteDVD(DVD dvd) {

//		dvd_list
//			dvd_id INTEGER PRIMARY KEY AUTOINCREMENT,
//			mid integer,
//			available boolean,
//			user_name varchar(255),
//			lent_date date)

		try {
		
			String query = "delete from dvd_list where dvd_id = ?;";
			
			PreparedStatement prepStmt = conn.prepareStatement(query);
			prepStmt.setInt(1, dvd.getDvdId());
			prepStmt.executeUpdate();			
			
			
		} catch (SQLException e) {
			log.error("Error removing DVD " + dvd.getDvdId());
			return false;
		}

		log.info("DVD " + dvd.getDvdId() + " removed from dvd_list");
		return true;
	}	
}
