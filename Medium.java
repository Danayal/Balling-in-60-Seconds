
public class Medium extends Command {

	public Medium(Player p) {
		super(p);
	}

	@Override
	void generateSpin() {
		//medium difficulty does not have spin
		Spin = (byte)0b000;
	}

	@Override
	void generateDifficulty() {
		difficulty = (byte)0b01000000;
	}

}
