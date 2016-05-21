

import java.sql.Connection;
import java.sql.Date;
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
		String createMoviesList = "CREATE TABLE IF NOT EXISTS movies_list (mid INTEGER PRIMARY KEY AUTOINCREMENT, category varchar(255), director varchar(255), name varchar(255), UNIQUE (name) )"; 
		String createDvdList = "CREATE TABLE IF NOT EXISTS dvd_list (dvd_id INTEGER PRIMARY KEY AUTOINCREMENT, mid integer, available boolean )"; 
		try {
			state.execute(createMoviesList);
			state.execute(createDvdList);
		} catch (SQLException e) {
			 System.err.println("Blad przy tworzeniu tabeli");
	            return false;
		}
		return true;
	}
	
	
	
	
	
	public boolean insertMovie(String category, String director, String name){
		 try {
	            PreparedStatement prepStmt = conn.prepareStatement(
	                    "insert into movies_list values (NULL, ?, ?, ?);");
	            prepStmt.setString(1, category);
	            prepStmt.setString(2, director);
	            prepStmt.setString(3, name);
	            prepStmt.execute();
	            log.info("dodano film " + name);
	        } catch (SQLException e) {
	            log.error("movie " + name + " alredy exist");
	            return false;
	        }
		return true;
	}


	public boolean insertDvd(int mid, Boolean avaliable) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement("insert into dvd_list values (NULL, ?, ?);");
			prepStmt.setInt(1, mid);
			prepStmt.setBoolean(2, avaliable);
			prepStmt.execute();
			log.info("dvd " + mid +  " added");
		} catch (SQLException e) {
			System.err.println("Blad przy wstawianiu plyty dvd");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	@Override
	public MoviesList getAllMovies(){
		MoviesList movies = new MoviesList();
		try {
			ResultSet result = state.executeQuery("SELECT * FROM movies_list");
			int mid;
			String category; 
			String name;
			String director; 
			while(result.next()){
				mid = result.getInt("mid");
				category = result.getString("category");
				name = result.getString("name");
				director = result.getString("director");
				movies.add(new Movie(mid, category, name, director));
				log.info("wyjeto film z bazy");
			}
		} catch (Exception e) {
			log.error("blad przy wzieciu szystkich filmow");
			log.error(e.getMessage());
			log.error(e.getStackTrace());
			return null;
		}
		return movies;
	}
	public DVDList getAllDvds(){
		DVDList dvdList = new DVDList();
		try {
			ResultSet result = state.executeQuery("SELECT * FROM dvd_list");
			while(result.next()){
				int dvdId = result.getInt("dvd_id");
				int mid = result.getInt("mid");
				Boolean available = result.getBoolean("available");
				dvdList.add(new DVD(dvdId, mid, available));
				log.info("wyciagnieto dvd");				
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("blad przy wyci¹ganiu dvd");
		}
		return dvdList;
	}
	
	
	
	
	
	
	public Movie findMovieByID(int mid){
		Movie movie;
		try {
			ResultSet result = state.executeQuery("SELECT * FROM movies_list where mid = " + mid );
			int tempMid;
			String tempCategory; 
			String tempName;
			String tempDirector; 
			while(result.next()){
				tempMid = result.getInt("mid");
				tempCategory = result.getString("category");
				tempName = result.getString("name");
				tempDirector = result.getString("director");
				movie = new Movie(tempMid, tempCategory, tempName, tempDirector);
				return movie;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	public MoviesList findMovieByName(String name){
		MoviesList moviesList = new MoviesList();
		try {
			ResultSet result = state.executeQuery("SELECT * FROM movies_list where name=\"" + name +"\"");
			int tempMid;
			String tempCategory;  
			String tempName;
			String tempDirector; 
			while(result.next()){
				tempMid = result.getInt("mid");
				tempCategory = result.getString("category");
				tempName = result.getString("name");
				tempDirector = result.getString("director");
				moviesList.add(new Movie(tempMid, tempCategory, tempName, tempDirector));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moviesList;
	}
}
//public boolean insertCategory(String name){
//
//try {
//       PreparedStatement prepStmt = conn.prepareStatement(
//               "insert into categories values (NULL, ?);");
//       prepStmt.setString(1, name);
//       prepStmt.execute();
//   } catch (SQLException e) {
//   	log.error("category: " + name +  " arledy exist");
//       return false;
//   }
//return true;
//}
//public boolean insertLoan(int dvdId, String userName, String userSurname, Date lentDate, Date returnDate) {
//try {
//	PreparedStatement prepStmt = conn.prepareStatement("insert into loan_list values (NULL, ?, ?, ?);");
//	prepStmt.setInt(1, dvdId);
//	prepStmt.setString(2, userName);
//	prepStmt.setString(3, userSurname);
//	prepStmt.setDate(4, lentDate);
//	prepStmt.setDate(5, returnDate);
//	prepStmt.execute();
//} catch (SQLException e) {
//	System.err.println("Blad przy wstawianiu wypozyczenia");
//	e.printStackTrace();
//	return false;
//}
//return true;
//}

//public Category findCategoryByID(int cid){
//
//log.debug("Finding category by ID");	
//
//Category category;
//try {
//	
//	log.debug("SQL Query: " + "SELECT * FROM categories where cid = " + cid);
//	ResultSet result = state.executeQuery("SELECT * FROM categories where cid = " + cid);
//
//	while(result.next()){
//		
//		
//		int tempCid = result.getInt("cid");
//		String tempName = result.getString("name");
//		log.debug("Found category: " + tempCid + " " + tempName);
//		category = new Category(tempCid, tempName);
//		return category;
//	}
//} catch (Exception e) {
//	log.error("Error in searching category by ID");
//	e.printStackTrace();
//}
//
//log.debug("Category with ID = " + cid +" not found");
//
//return null;
//}
//
//public Loan findLoanByID(int loanId){
//Loan loan;
//try {
//	ResultSet result = state.executeQuery("SELECT * FROM loan_list where mid = " + loanId );
//	int tempLoanId;
//	int tempDvdId;
//	String tempUserName;
//	String tempUserSurname;
//	Date tempLentDate;
//	Date tempReturnDate;
//	while(result.next()){
//		tempLoanId = result.getInt("loan_id");
//		tempDvdId = result.getInt("dvd_id");
//		tempUserName = result.getString("user_name");
//		tempUserSurname = result.getString("user_surname");
//		tempLentDate = result.getDate("lent_date");
//		tempReturnDate = result.getDate("return_date");
//		loan = new Loan(tempLoanId, tempDvdId, tempUserName, tempUserSurname, tempLentDate, tempReturnDate);
//		return loan;
//	}
//} catch (Exception e) {
//	e.printStackTrace();
//}
//return null;
//}
//
//}
	