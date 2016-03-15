
public class StringToDelta {
	 public static void main( String args[] )
	  {
		//GetDB
		DbInterface di = new DbInterface();
		DbInterface.openDbConnection();
		//string_format
		DbInterface.insertNewSong("Six", "All that Remains", "1", "STRING_FORMAT", "1 0 0 5");
		
		System.out.println(DbInterface.tableDump());
		
		DbInterface.closeDbConnection();
	  }
}
