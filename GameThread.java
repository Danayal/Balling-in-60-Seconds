
public class GameThread implements Runnable {

	// variables
	Player player; // game running for this player
	Command cmd;
	SensorData concreteSubject;
	SoundObserver sound;
	int rounds; // rounds for the game
	static boolean running;
	// constructor
	static boolean isRunning()
	{
		return running;
	}
	public GameThread(Player p) {
		// Initializing
		player = p;
		concreteSubject = new SensorData();
		sound = new SoundObserver();
		rounds = 0;
		cmd = null;

		// register observers
		concreteSubject.registerObserver(player);
		concreteSubject.registerObserver(sound);

		Thread t = new Thread(this);
		running = true;
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Player's difficulty level determines how the command will be created
	private void setCommand(Player p) {
		System.out.println(p.getDifficulty());
		if (player.getDifficulty() == 0) {
			cmd = new Easy(p);
		} else if (player.getDifficulty() == 1) {
			cmd = new Medium(p);
		} else if (player.getDifficulty() == 2) {
			cmd = new Hard(p);
		}
	}

	// Game runs on a separate thread
	public void run() {

		// command is set with the associated difficulty and player
		setCommand(player);

		// Game loop starts
		while (rounds < 15) {

			// send a command
			cmd.sendCommand();

			// subject read data and updates observers(player + sound)
			concreteSubject.getData();

			rounds++;
		}
		// Game loop ends
		running = false;

	}
}
