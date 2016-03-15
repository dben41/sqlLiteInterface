

import java.sql.*;

public class DbInterface
{
  static Connection c = null;
  static Statement stmt = null;
  
  public static void main( String args[] )
  {
	  openDbConnection(); 
	  //insertNewSong("Six", "All that Remains", "1", "INTERVAL_FORMAT", "1 0 0 5");
	  //deleteRecord(3);
	  System.out.println(tableDump());
	  System.out.println("--------------------------");
	  System.out.println(getRecordSid("Purplee Haze", "Jimi Hendrix", "Guitar 1"));
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
   * Inserts a new song, checks to see if meta data exists in 'song' table already. Overwrites writing into formatType table.
   */
  public static void insertNewSong(String title, String artist, String track, String formatType, String melody){
	  int sid = -1;
	  try {
		  stmt = c.createStatement();
		  String sql;
		  //check to see if it exists already
		  sid = getRecordSid(title, artist, track);
		  if(sid == -1) {
		      sql = "INSERT INTO SONG (TITLE,ARTIST,TRACK) " +
		                   "VALUES ('" + title + "', '" + artist + "', '" + track + "');"; 
		      stmt.executeUpdate(sql);
		      sid = getLastRecord("SONG");
		  }
	      sql = "INSERT INTO " + formatType + " (SID, MELODY) " +
                  "VALUES (" + sid + ", '" + melody + "');"; 
	      stmt.executeUpdate(sql);
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Inserted (" + sid + ", " + title + ", " + artist + ", " + track + ", " + formatType + ", " + melody + ") into database successfully"); 
  }
  
  /*
   * returns sid, allows for
   */
  public static int getRecordSid(String title, String artist, String track){
	  int result = -1; //-1 indicates error
	  try {
		  //just return song, don't wory about song, yet
		  stmt = c.createStatement();
		  ResultSet rs = stmt.executeQuery( "SELECT * FROM song WHERE TITLE = '" + title + "' AND ARTIST = '" + artist + "' AND TRACK = '" + track + "'; " );
		  while ( rs.next() ) {
			  result = rs.getInt("sid");
		  }
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    return result;
  }
  
  /*
   * This will probably be just for testing, we'll probably searching by melody
   */
  public static String getRecord(int sid){
	  String result = "";
	  try {
		  //just return song, don't wory about song, yet
		  stmt = c.createStatement();
		  ResultSet rs = stmt.executeQuery( "SELECT * FROM song WHERE SID = " + sid + ";" );
		  while ( rs.next() ) {
			  result += "Song " + sid + ": " + rs.getString("title") + ", " + rs.getString("artist") + ", " + rs.getString("track");
		  }
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    return result;
  }
  
  /*
   * Returns whole table
   */
  public static String tableDump(){
	  String result = "";
	  try {
		  //just return song, don't wory about song, yet
		  stmt = c.createStatement();
		  ResultSet rs = stmt.executeQuery( "SELECT * FROM string_format;" );
		  while ( rs.next() ) {
			  result += "SID: " + rs.getInt("sid") + ", " + rs.getString("title") + ", " + rs.getString("artist") + ", " + rs.getString("track") + "\n";
		  }
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    return result;
  }
  
  /*
   * This will probably be just for testing, we'll probably searching by melody
   */
  public static void updateRecord(String table, String columnName, String columnValue, int sid){
	  try {
		  
		  stmt = c.createStatement();
	      String sql = "UPDATE " + table + " set " + columnName + " = " + columnValue + " where SID = " + sid + ";";
	      stmt.executeUpdate(sql);
	      //c.commit(); //uncomment if db not in auto-commit mode
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
  }
  
  /*
   * This will probably be just for testing
   */
  public static void deleteRecord(int sid){
	  try {
		  //just return song, don't wory about song, yet
		  stmt = c.createStatement();
		  String sql = "DELETE from song where SID = " + sid + ";";
		  stmt.executeUpdate(sql);
	      //c.commit(); //uncomment if db not in auto-commit mode
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
  }
  
  public static int getLastRecord(String table){
	  int sid = -1;
	  try {
		  ResultSet rs = stmt.executeQuery( "SELECT SID FROM " + table + ";" );
		  
		  while ( rs.next() ) {
			  sid = rs.getInt("sid");
		  }
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    return sid;
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