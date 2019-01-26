
public class Easy extends Command {

	// This class is one of the 3 children templates that has its own generateSpin
	// and generateDifficulty
	public Easy(Player p) {
		super(p);
	}

	@Override
	void generateSpin() {
		// Easy difficulty does not have spin
		Spin = (byte) 0b000;
	}

	@Override
	void generateDifficulty() {
		// The byte below will be OR'd onto the byte to be sent
		difficulty = (byte) 0b00000000;
	}

}
