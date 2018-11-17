import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player {

	private int finalScore = 0;
	private boolean lastHit = false, multiplier;
	private int multi = 1;
	private String name;

	public void setScore(int s) {
		finalScore = s;
	}

	public void setName(String n) {
		name = n;
	}

	public void updateScore(int score) {

		File SuccessFile = new File("success.WAV");
		File FailureFile = new File("failure.WAV");

		if (lastHit && score > 0) {
			multi++;
			multiplier = true;

		} else if (score > 0) {
			lastHit = true;
		} else {
			lastHit = false;
			multi = 1; // reseting multi to 1, score is 0 when a player misses
			multiplier = false;
		}

		// Sound output
		if (score > 0) {
			// Sound output for success
			Clip SuccessClip = null;
			try {
				SuccessClip = AudioSystem.getClip();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				SuccessClip.open(AudioSystem.getAudioInputStream(SuccessFile));
				SuccessClip.start();
				try {
					Thread.currentThread().sleep(SuccessClip.getMicrosecondLength() / 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			Clip FailureClip = null;
			try {
				FailureClip = AudioSystem.getClip();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				FailureClip.open(AudioSystem.getAudioInputStream(FailureFile));
				FailureClip.start();
				try {
					Thread.currentThread().sleep(FailureClip.getMicrosecondLength() / 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		finalScore += multi * score;

	}

	public int getFinalScore() {
		return finalScore;
	}

	public String getName() {
		return name;
	}

	public boolean getMultiplier() {
		return multiplier;
	}

}
