import java.io.*;

public class Driver {

	public static void main(String[] args) {

		// Initializing a player to set his name and difficulty before game starts
		Player playerOne = new Player();

		// Creating a stream to read from the keyboard
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// Player enters name
		System.out.print("Player name: ");
		String temp = null; // temp string to store player's name
		try {
			temp = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		playerOne.setName(temp);

		// Player enters difficulty
		System.out.print("Difficulty (0 - Easy, 1 - Normal, 2 - Hard): ");
		int tempDifficulty = 0; // temp int to store player's difficulty
		try {
			tempDifficulty = br.read();// Reads keyboard input, in ASCII
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (tempDifficulty < 48 || tempDifficulty > 50) // Checks if input is within set limits
		{
			System.out.println("Error, Press 0,1 or 2");
			return;// If wrong difficulty entered, terminate
		}
		playerOne.setDifficulty(tempDifficulty - 48); // Convert ASCII to integer number

		// Game thread starts
		System.out.println("GAME STARTED!!!");
		GameThread g = new GameThread(playerOne); // Starts Game THread

		// Game ends
		// Player's name and score are displayed
		System.out.println("GAME OVER!");
		// Setup Leaderboards and update Records with new player data
		Leaderboards L = new Leaderboards();
		System.out.println(playerOne.getName() + " " + playerOne.getFinalScore());
		L.updateRecords(playerOne);

		// Displaying Leaderboards

		System.out.println("\nLeaderboards: ");
		System.out.println(L.getRecords());

	}

}
