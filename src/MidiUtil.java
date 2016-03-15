import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Patch;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MidiUtil {
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    static HashMap<String, Integer> noteMap = null;
    
    static String fileName = "midiFiles/misc/TwinkleMutli.mid";
    
	public static void main(String[] args) {
		//GetDB
		DbInterface di = new DbInterface();
		DbInterface.openDbConnection();
		
		//get artist
		String artist = "";
		
		//get title
		String title = "";

		//Get all tracks in the string format
		ArrayList<String> tracks = getStringFormat(fileName);
		
		for(String track : tracks){
			String deltaTrack = getDeltaFormat(track);
		}
		
		/*
		int trackNumber = 1;
		for(String track : tracks){
			//if doesn't exist, commit
		    DbInterface.insertNewSong(title, artist, trackNumber + "", "string_format", track);
		    
		    //get the delta format
		    String deltaTrack = null;
		    
		    DbInterface.insertNewSong(title, artist, trackNumber + "", "delta_format", deltaTrack);
		}
		*/
		
		
		

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

	/*
	 * Returns the String Format
	 */
	public static ArrayList<String> getStringFormat(String filePath){
		ArrayList<String> tracks = new ArrayList<String>();
		initializeNoteMap();
		try {
			//open the MIDI file
			Sequence sequence = MidiSystem.getSequence(new File(filePath));
			
			//loop through all the tracks in the MIDI sequence
			int trackNumber = 0;
	        for (Track track :  sequence.getTracks()) {
	        	//not all tracks contain useful MIDI info, only commit things that are
	        	String trackString = null;
	        	trackNumber++;
	        	//System.out.println("Track " + trackNumber + ": size = " + track.size());
	        	 //keep track of all the notes played, eliminate repeats
	        	 LinkedHashSet<String> notes = new LinkedHashSet<String>();
                 
                 //loop through each MIDI event in track
	        	 for (int i=0; i < track.size(); i++) { 
	                 MidiEvent event = track.get(i);
	                 MidiMessage message = event.getMessage();    
	                 
	                 if (message instanceof ShortMessage) {
	                     ShortMessage sm = (ShortMessage) message;
	                     if (sm.getCommand() == NOTE_ON) {
	                    	 //initialize with empty string
	                    	 if(trackString == null)trackString = "[";
	                    	 
	                         int key = sm.getData1();
	                         int octave = (key / 12);
	                         int note = key % 12;
	                         String noteName = NOTE_NAMES[note];
	                         notes.add(noteName + octave);
	                         //System.out.println("Note on, " + noteName + octave );
	                     } else if (sm.getCommand() == NOTE_OFF && notes.size() != 0) {
	                    	 //returns the chord nomenclature if applicable, else returns extra note
	                         String wholeMelody = concatenateNotes(notes);
	                         notes.clear();
	                         //append to the output string, be sure to add space, the delimiter 
	                         trackString += "[" + wholeMelody + "],"; 
	                         //System.out.println(wholeMelody);
	                     } 
	                 }
	             }
	        //add to list of strings to be returned if not null	 
	        if(trackString != null) tracks.add(trackString.substring(0,trackString.length()-1) + "]");

	        }
	        
		} catch (InvalidMidiDataException | IOException e) {
			e.printStackTrace();
		}
		return tracks;
	}
	
	/*
	 * Assigns a number to each note so that they can be ordered and compared later.
	 */
	private static void initializeNoteMap() {
		noteMap = new HashMap<String, Integer>();
		noteMap.put("C", 0);
		noteMap.put("C#", 1);
		noteMap.put("D", 2);
		noteMap.put("D#", 3);
		noteMap.put("E", 4);
		noteMap.put("F", 5);
		noteMap.put("F#", 6);
		noteMap.put("G", 7);
		noteMap.put("G#", 8);
		noteMap.put("A", 9);
		noteMap.put("A#", 10);
		noteMap.put("B", 11);
		
	}

	/*
	 * When multiple notes are played, we want them to appear in the form x0/x0/x0
	 */
	private static String concatenateNotes(LinkedHashSet<String> notes) {
		String[] noteArray = notes.toArray(new String[notes.size()]);
		noteArray = orderNotes(noteArray);
		if(noteArray.length == 1) return noteArray[0];
		String totalString = "";
		for(String note : noteArray){
			totalString += note + ",";
		}
		totalString = totalString.substring(0,totalString.length()-1);
		return totalString;
	}

	/*
	 * When multiple notes are played in a chord, we want them to be in order in relation to music.
	 * This uses selection sort, because only a few notes will be played at a time.
	 */
	private static String[] orderNotes(String[] unorderedList) {
		int i, j, minIndex;
		String tmp;
				
	      int n = unorderedList.length;
	      for (i = 0; i < n - 1; i++) {
	            minIndex = i;
	            for (j = i + 1; j < n; j++){
	            	//deal with flats
	            	int firstNote, firstOctave, secondNote, secondOctave;
	            	
	            	if(unorderedList[j].contains("#")){
	            		firstNote = (int) noteMap.get(unorderedList[j].substring(0, 2).toString());
		            	firstOctave = Integer.parseInt(unorderedList[j].substring(2, 3));
	            	}else{
	            		firstNote = (int) noteMap.get(unorderedList[j].substring(0, 1).toString());
		            	firstOctave = Integer.parseInt(unorderedList[j].substring(1, 2));
	            	}
	            	
	            	if(unorderedList[minIndex].contains("#")){
	            		secondNote = (int) noteMap.get(unorderedList[minIndex].substring(0, 2).toString());
		            	secondOctave = Integer.parseInt(unorderedList[minIndex].substring(2, 3));
	            	}else{
	            		secondNote = (int) noteMap.get(unorderedList[minIndex].substring(0, 1).toString());
		            	secondOctave = Integer.parseInt(unorderedList[minIndex].substring(1, 2));
	            	}
	            	
	                  if ((firstNote < secondNote && firstOctave == secondOctave)|| firstOctave < secondOctave)
	                        minIndex = j;
	            }
	            if (minIndex != i) {
	                  tmp = unorderedList[i];
	                  unorderedList[i] = unorderedList[minIndex];
	                  unorderedList[minIndex] = tmp;
	            }
	      }
	      
	      return unorderedList;
	}

}
