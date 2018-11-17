
/*
 * The purpose of this class to randomize the commands that 
 * will be given to the player in the game
 */
import java.util.Random;

public class Randomized {

	private int RandBinary;
	private int RandTargets;

	public static int getRandBinary() {
		Random r = new Random();
		int value = r.nextInt(2); // Generate a random number between 0 and 1
		return value;
	}

	public Randomized() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static int getRandTarget() {
		Random r = new Random();
		int value = 1 + r.nextInt(4); // Generate a random number between 1 and 4
		return value;
	}

}
