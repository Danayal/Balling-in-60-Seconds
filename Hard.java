
public class Hard extends Command {

	// This class is one of the 3 children templates that has its own generateSpin
	// and generateDifficulty
	public Hard(Player p) {
		super(p);
	}

	@Override
	void generateSpin() {
		//Spin has a 50/50 chance of happening since it will generate either 0 or a 1
		int testAcc = Randomized.getRandBinary();
		if (testAcc == 0) {
			Spin = (byte) 0b000;
		} else {
			Spin = (byte) 0b100;
		}

	}

	@Override
	void generateDifficulty() {
		difficulty = (byte) 0b10000000;
	}

}
