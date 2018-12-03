import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/* This Class is a child of SoundBehaviour and uses Stategy Pattern to quickly on 
* the fly switch between sounds to play.
* 	Background Music will be played by this sound
*/
public class Background implements SoundBehaviour {
	Clip clip = null;
	public void playsound() {
		// An on the fly, anonymous Thread is created so that sound playing is not
		// blocking
		Thread t = new Thread() {
			public void run() {

				// Open Sound File
				File f = new File("background.wav");
				try {
					clip = AudioSystem.getClip();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					// Load sound file stream into Clip and play
					clip.open(AudioSystem.getAudioInputStream(f));
					clip.start();
					// Since this is background music it must loop conitnously until current thread
					// ends
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					try {
						// Because clips stop playing once the thread that executed them have terminated
						// We will keep the sound playing as long as the game is running
						while (GameThread.isRunning()) {
							Thread.currentThread().sleep(10);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Output stream must be closed and flushed
				clip.flush();
				clip.close();

			}
		};
		t.start();
	}

	public void stopsound() {
		clip.stop();
	}

}
