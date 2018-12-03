import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/* This Class is a child of SoundBehaviour and uses Stategy Pattern to quickly on 
* the fly switch between sounds to play
* 	Failure Sound will be played by this class
*/

public class Failure implements SoundBehaviour {
	Clip clip = null;

	public void playsound() {
		// An on the fly, anonymous Thread is created so that sound playing is not
		// blocking
		Thread t = new Thread() {
			public void run() {
				// Open File
				File f = new File("failure.wav");
				clip = null;
				try {
					clip = AudioSystem.getClip();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {

					clip.open(AudioSystem.getAudioInputStream(f));
					clip.start();
					try {
						Thread.currentThread();
						// Current Thread will run only until the sound file is playing.
						// Then it will terminate current thread and stop playing the sound.
						// This will not affect the thread running the gae
						Thread.currentThread().sleep(clip.getMicrosecondLength() / 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
