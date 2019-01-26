import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Leaderboards {

	static ArrayList<Player> Players; // stores all player data from leaderboards.txt
	// streams for reading and writing to leaderboards.txt
	static BufferedReader leaderboardsInput;
	static BufferedWriter leaderboardsOutput;

	public Leaderboards() {
		// TODO Auto-generated constructor stub
		try {
			// initialize reader stream by wrapping buffered reader with file reader
			leaderboardsInput = new BufferedReader(new FileReader("leaderboards.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Players = new ArrayList<Player>(); // initialize players list
		try {
			String Line;
			// read text file and acquire player data and store to player list
			while (leaderboardsInput.ready()) {
				Player tempP = new Player();
				Line = leaderboardsInput.readLine();
				String[] words = Line.split("\t");
				tempP.setName(words[0]);
				tempP.setScore(Integer.parseInt(words[1]));
				Players.add(tempP);
			}
			leaderboardsInput.close(); // closes the file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// this function will add a new player to the players list and sort by score
	// is only called when the game is completed
	public static void updateRecords(Player tempP) {
		Players.add(tempP); // add a player
		Collections.sort(Players, new SortByScore()); // collections library used for sorting algorithm
		try {
			leaderboardsOutput = new BufferedWriter(new FileWriter("leaderboards.txt"));// open file to be written to
																						// using wrapper
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			leaderboardsOutput.write(getRecords());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			leaderboardsOutput.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			leaderboardsOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// returns one string with all player names and scores
	// this is because gui logic to show leader board currently only accepts a
	// single string
	public static String getRecords() {
		Player tempP = new Player();
		String records = ""; // empty string, all player names and scores are concatenated to this
		for (int i = 0; i < Players.size(); i++) {
			tempP = Players.get(i);
			records += tempP.toString();
		}
		return records;

	}

}