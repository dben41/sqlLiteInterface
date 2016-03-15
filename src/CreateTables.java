//maven
//jet brains

import java.sql.*;

public class CreateTables
{
  static Connection c = null;
  static Statement stmt = null;
  
  public static void main( String args[] )
  {
	  openDbConnection();
	  
	  //drop tables
	  dropTable("song");
	  dropTable("interval_format");
	  dropTable("string_format");
	  
	  //create tables
	  createSongTable();
	  createIntervalFormatTable();
	  createStringFormatTable();
	  
	  
	  closeDbConnection();
  }
  
  /*
   * Sets up the DB and opens a connection
   */
  public static void openDbConnection(){
	  try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:songs.db");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully"); 
  }
  
  /*
   * Creates the song table in the db
   */
  public static void createSongTable(){
	  try {
		stmt = c.createStatement();
		String sql = "CREATE TABLE song " +
	                 "(SID INTEGER PRIMARY KEY AUTOINCREMENT," +
	                 " TITLE           TEXT    NOT NULL, " +
	                 " ARTIST           TEXT    NOT NULL, " +
	                 " TRACK            TEXT     NOT NULL)"; 
		stmt.executeUpdate(sql);
		System.out.println("Song table created successfully");
	} catch (SQLException e) {
		e.printStackTrace();
	}
  }
  
  /*
   * Creates the song table in the db
   */
  public static void dropTable(String tableName){
	  try {
		stmt = c.createStatement();
		String sql = "DROP TABLE " + tableName + ";";
		stmt.executeUpdate(sql);
		System.out.println("Table " + tableName + " dropped successfully");
	} catch (SQLException e) {
		e.printStackTrace();
	}
  }
  
  /*
   * Creates the song table in the db
   */
  public static void createIntervalFormatTable(){
	  try {
		stmt = c.createStatement();
		String sql = "CREATE TABLE interval_format " +
	                 "(SID INTEGER PRIMARY KEY  AUTOINCREMENT, " +
	                 " MELODY           TEXT    NOT NULL)"; 
		stmt.executeUpdate(sql);
		System.out.println("Interval format table created successfully");
	} catch (SQLException e) {
		e.printStackTrace();
	}
  }
  
  /*
   * Creates the song table in the db
   */
  public static void createStringFormatTable(){
	  try {
		stmt = c.createStatement();
		String sql = "CREATE TABLE string_format " +
	                 "(SID INTEGER PRIMARY KEY  AUTOINCREMENT, " +
	                 " MELODY           TEXT    NOT NULL)"; 
		stmt.executeUpdate(sql);
		System.out.println("String format table created successfully");
	} catch (SQLException e) {
		e.printStackTrace();
	}
  }
  
  /*
   * Closes the db connection
   */
  public static void closeDbConnection(){
	  try {
	    stmt.close();
	    c.close();
	    System.out.println("DB closed successfully");
	} catch (SQLException e) {
		e.printStackTrace();
	}
  }
  
}