import java.util.ArrayList;
import java.util.Arrays;


public class StringToDelta {
	
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

	 public static void main( String args[] )
	  {
		//GetDB
		//DbInterface di = new DbInterface();
		//DbInterface.openDbConnection();
		//string_format
		//DbInterface.insertNewSong("Six", "All that Remains", "1", "STRING_FORMAT", "1 0 0 5");
		
		//System.out.println(DbInterface.tableDump());
		
		//DbInterface.closeDbConnection();
		 
		 String sampleSong = "[[C5],[E5],[G5],[E5],[D5,F5,A5],[C5,G5,B5],[F5,C6],[A5]]";
		 ArrayList indexOfNote = new ArrayList(Arrays.asList(NOTE_NAMES));
		 int i = indexOfNote.indexOf("F") * 12;
		 //remove the end and beginning bracket
		 String temp = sampleSong.substring(1, sampleSong.length() - 1);
		 ArrayList<String> songEntities = new ArrayList(Arrays.asList(temp.split(",")));
		 for(String s : songEntities){
			 System.out.println(s);
		 }
		 
		 System.out.println("Index of F is: " + i);
	  }
	 
		private static String getDeltaFormat(String track) {
			//split by space delimiter
			String[] allNotes = track.split(" "); 
			//change each value to int value
			int[] intNotes = stringNotesToIntNotes(allNotes);
			return null;
		}
		
		private static int[] stringNotesToIntNotes(String[] allNotes) {
			//for(String note)
			return null;
		}
}
