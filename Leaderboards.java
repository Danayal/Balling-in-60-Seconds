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

	static ArrayList <Player> Players;
	static BufferedReader leaderboardsInput;
	static BufferedWriter leaderboardsOutput;

	public Leaderboards() {
		// TODO Auto-generated constructor stub
		try {
			leaderboardsInput = new BufferedReader(new FileReader("leaderboards.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Players = new ArrayList();
		try {
			String Line;
			while (leaderboardsInput.ready()) {
				Player tempP = new Player();
				Line = leaderboardsInput.readLine();
				String[] words = Line.split("\t");
				tempP.setName(words[0]);
				tempP.setScore(Integer.parseInt(words[1]));
				Players.add(tempP);
			}
			leaderboardsInput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void updateRecords(Player tempP)
	{
		Players.add(tempP);
		Collections.sort(Players, new SortByScore());
		try {
			leaderboardsOutput = new BufferedWriter(new FileWriter("leaderboards.txt"));
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
		}		try {
			leaderboardsOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getRecords() {
		Player tempP = new Player(); 
		String records= "";
		for(int i=0; i < Players.size();i++)
		{
			tempP = Players.get(i);
			records += tempP.toString();
		}
		return records;
		
	}

}