import java.util.ArrayList;
import java.util.Arrays;


public class StringToDelta {
	
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

	 public static void main( String args[] )
	  {
		 String sampleSong = "[[C5],[E5],[G5],[E5],[D5,F5,A5],[C5,G5,B5],[F5,C6],[A5]]";
		 
		 String deltaFormatOfSong = getDeltaFormat(sampleSong);
		 
		 System.out.println("Delta Format of Song: " + deltaFormatOfSong);
 
	  }
	 
	 	/*
	 	 * This converts a melody in string_format into delta_format.
	 	 */
		private static String getDeltaFormat(String track) {
			//Convert from the string format to number format 
			ArrayList songEntitiesInts = stringNotesToIntNotes(track);
			
			//Iterate through all the elements as a two-game
			 ArrayList previousEntity = (ArrayList)songEntitiesInts.get(0);
			 String deltaString = "";
			 for(int i = 1; i < songEntitiesInts.size(); i++){
				 ArrayList currentEntity = (ArrayList)songEntitiesInts.get(i);
				 String currentMatrix = "[";
				 //loop through all the notes 
				 for(int j = 0; j < previousEntity.size(); j++){
					 int previousNote = (int) previousEntity.get(j);
					 for(int k = 0; k < currentEntity.size(); k++){
						 int currentNote = (int) currentEntity.get(k); 
						 int delta = currentNote - previousNote; 
						 String deltaToString = "";
						 if(delta > 0){
							 deltaToString = "+" + delta + "";
						 }else{
							 deltaToString = "" + delta + "";
						 }
						 if(k + 1 != currentEntity.size()){
							 currentMatrix += deltaToString + ",";
						 }else{
							 currentMatrix += deltaToString;
						 }		
					 }
					 if(j + 1 == previousEntity.size()){
						 currentMatrix += "],";
					 }else{
						 currentMatrix += ";";
					 }
				 }
				 previousEntity = currentEntity;
				 
				 //add interval array
				 deltaString += currentMatrix;
			 }
			
			 deltaString = deltaString.substring(0, deltaString.length() -1);
			 return "[" + deltaString  + "]"; 
		}
		
		private static ArrayList stringNotesToIntNotes(String track) {
			ArrayList indexOfNote = new ArrayList(Arrays.asList(NOTE_NAMES)); 
			 //remove the end and beginning bracket
			 String temp = track.substring(2, track.length() - 2);
			 String[] splitArray = temp.split("\\],\\[");
			 ArrayList<String> songEntities = new ArrayList(Arrays.asList(splitArray));
			 ArrayList songEntitiesInts = new ArrayList();
			 //loop through every note or chord in melody
			 for(String entity : songEntities){
				 //loop thru every entity, find the number representation and delta
				 ArrayList<String> entityArray = new ArrayList(Arrays.asList(entity.split(",")));
				 ArrayList<Integer> entityArrayInts = new ArrayList<Integer>();

				 for(String note: entityArray){
					 
					 
					 String noteName = null;
					 int octave = -1;	
					 String temp2 = note.substring(1,2);
					 
					 //check for sharps
					 if(temp2.equals("#")){
						 noteName = note.substring(0,2);
						 octave = Integer.parseInt(note.substring(2));
					 }else{
						 noteName = note.substring(0,1);
						 octave = Integer.parseInt(note.substring(1));
					 }
					 
					 int i = (octave * 12) + indexOfNote.indexOf(noteName);

					 entityArrayInts.add(i);
					 
				 }
				 songEntitiesInts.add(entityArrayInts);
			 }
			 return songEntitiesInts;			 
			 
		}
}
