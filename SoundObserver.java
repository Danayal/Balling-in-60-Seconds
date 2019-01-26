public class SoundObserver extends Sound implements Observer {

	// Sound class has a ball and a board byte
	// ball and board bytes are used to determine a successful shot
	// if shot is successful, a success sound is played as a feedback
	// else failure sound is played as a feedback for the player to do better next
	// shot
	// This will be done using the success boolean
	private byte ball, board;
	private boolean success;

	public SoundObserver() {
		ball = 0;
		board = 0;
		success = false;

		// Background music runs constantly
		// Once soundObserver is called background music is played
		this.setBehaviour(new Background());
		this.play();

	}

	public void soundPerShot() {
		boolean targetHit = false;
		// bit[5] received indicates if target was hit
		// if hit, boolean targetHit is set to true
		if ((board & 0b00100000) == 0b00100000) {
			targetHit = true;
		}

		// if target is hit and byte sent from ball is greater than 0
		// success boolean is set to true
		if (targetHit && (ball & 0b00111111) > 0) {
			success = true;
		} else {
			success = false;
		}
		// Playing the sound based on player's shot
		if (success) {

			this.setBehaviour(new Success()); // On the fly switch sound 
		} else {

			this.setBehaviour(new Failure()); // On the fly switch sound 
		}

		this.play();

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

		// bytes identified
		// Sound class now calls soundPerShot to play the appropriate sound based on
		// player's shot
		soundPerShot();
	}

	void setBehaviour(SoundBehaviour sb) {
		this.sb = sb;
	}

	void play() {
		sb.playsound();
	}

	void stop() {
		sb.stopsound();
	}

}
