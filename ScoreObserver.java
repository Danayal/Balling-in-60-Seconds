
public class ScoreObserver implements Observer {

	private byte byteOne, byteTwo;
	private int score;
	private Player p;

	public ScoreObserver(Player p) {
		score = 0;
		this.p = p;
	}

	//the subject class reads the bytes from arduino and updates the scoring logic class
	@Override
	public void update(byte b1, byte b2) {
		// TODO Auto-generated method stub
		this.byteOne = b1;
		this.byteTwo = b2;
		//After reading the two bytes, the scoring logic calculates the scorePerShot
		generateScore();
	}

	//generateScore method generates the score of the player per shot
	public void generateScore() {

		byte ball, board;

		int accValuePlayer1 = 0;
		boolean targetHit = false;

		// Separate byteOne and byteTwo to ball and board
		if ((byteOne & 0b11000000) == 0b11000000) {
			board = byteOne;
			ball = byteTwo;
		} else {
			board = byteTwo;
			ball = byteOne;
		}

		// board
		if ((board & 0b00100000) == 0b00100000) {
			targetHit = true; 
		}
		// ball
		accValuePlayer1 = ball & 0b00111111;

		// Processing data
		if (accValuePlayer1 > 0 && targetHit) {
			score = accValuePlayer1;
		} else {
			score = 0;
		}

		//after score per shot is determined it updates the player his scorePerShot
		//and the player class calculates the total score
		this.p.updateScore(score);
	}

}
