
public class Player implements Observer {
	
	// Variables
	private String name; // player's name
	private int difficulty; // Game difficulty for the player
	private boolean multiplier; // Feedback boolean -- set to true if player on streak
	private int scorePerShot, finalScore; // to find player's final score

	// Player as an observer class gets 2 bytes from the subject class(SensorData)
	// Player's 2 bytes are initialized
	// 2 bytes are processed to initialize the ball and board bytes
	// ball and board bytes are used to generate the scorePerShot of the player
	private byte ball, board;

	// 2 variables used to determine if a player is on a streak
	// LastHit is a boolean to keep track of the player's previous shot
	boolean lastHit;
	// multi is an integer which multiplies the score
	int multi;

	// constructors
	public Player() {
		name = "";
		difficulty = 0;
		scorePerShot = 0;
		finalScore = 0;
		ball = 0;
		board = 0;
		lastHit = false;
		multi = 0;
	}

	// Methods

	public String toString() {
		return name + "\t" + finalScore + "\n";
	}

	public void setScore(int s) {
		finalScore = s;
	}

	public void setName(String n) {
		name = n;
	}

	public void setDifficulty(int d) {
		difficulty = d;
	}

	public String getName() {
		return name;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public boolean getMultiplier() {
		return multiplier;
	}

	public void generateScorePerShot() {

		// Process data to generate score per shot

		boolean targetHit = false;
		// bit[5] received indicates if target was hit
		// if hit, boolean targetHit is set to true
		if ((board & 0b00100000) == 0b00100000) {
			targetHit = true;
		}

		// check score sent by the ball
		scorePerShot = ball & 0b00111111;

		// if the target was correctly hit, scorePerShot is unchanged
		// if the target was not hit, scorePerShot is set to 0
		if (!targetHit) {
			scorePerShot = 0;
		}

		System.out.print((int) scorePerShot + "\t");
		System.out.println(Integer.toBinaryString(scorePerShot));

		// ScorePerShot is calculated
		// Player updates his final score
		updateFinalScore(scorePerShot);
	}

	public void updateFinalScore(int s) {

		// s is player's score per shot
		// Logic below is to determine if player is on a streak

		// Previous shot and current shot are successful
		if (lastHit && s > 0) { //
			multi++; // increment multiplier counter to add to the score
			multiplier = true; // set the multiplier to true to send a feedback to the player
		}
		// only current shot is a success --> current shot becomes the previous shot
		// for the next round
		else if (s > 0) {
			lastHit = true;
			System.out.println("Success");
		} else {
			lastHit = false;
			multi = 1; // reseting multi to 1, score is 0 when a player misses
			multiplier = false; // feedback LED becomes 0 and does not light up
			System.out.println("Fail");
		}

		// final score is updated based on player's score and streak status
		finalScore += multi * s;

	}

	public int getFinalScore() {
		return finalScore;
	}

	@Override
	public void update(byte byteOne, byte byteTwo) {

		// byteOne and byteTwo are updated from the subject
		// ball and board bytes are initialized from byteOne and byteTwo

		// Byte read from stream has bits[7][6] as identifier bits
		// bits[7][6] = 11 --> byte is received from the board
		// bits[7][6] = 01 --> byte is received from the ball
		if ((byteOne & 0b11000000) == 0b11000000) {
			board = byteOne;
			ball = byteTwo;
		} else if ((byteOne & 0b01000000) == 0b01000000) {
			board = byteTwo;
			ball = byteOne;
		}

		// bytes are processed --> player class generates scorePerShot and updates the
		// final score
		generateScorePerShot();

	}

}
