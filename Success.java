import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Success implements SoundBehaviour {
	/*
	 * This Class is a child of SoundBehaviour and uses Stategy Pattern to quickly
	 * on the fly switch between sounds to play sucessful shot Sound will be played by this
	 * class
	 */

	Clip clip = null;

	public void playsound() {
		Thread t = new Thread() {
			public void run() {
				File f = new File("success.wav");
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
