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

	private int finalScore;
	private boolean lastHit, multiplier;
	private int multi;
	private String name;
	
	public Player(){
		finalScore = 0;
		 lastHit = false;
		 multiplier = false;
		  multi = 1;
		  name = "null";
	}
	public void setScore(int s) {
		finalScore = s;
	}
	public void setName(String n) {
		name = n;
	}

	public void updateScore(int score) {

		//for sound output
		File SuccessFile = new File("success.wav");
		File FailureFile = new File("failure.wav");

		
		//Logic for multiplier
		
		if (lastHit && score > 0) { //if last hit and current hit are successfull
			multi++; 				//incerement multiplier counter to add to the score
			multiplier = true;		//set the multiplier to true to send a feedback to the player

		} else if (score > 0) {
			lastHit = true; //Last hit is set to true after one successful shot
		} else {
			lastHit = false;
			multi = 1; // reseting multi to 1, score is 0 when a player misses
			multiplier = false; //feedback LED becomes 0 and does not light up
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
					Thread.currentThread().sleep(SuccessClip.getMicrosecondLength()/1000);
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
					Thread.currentThread().sleep(FailureClip.getMicrosecondLength()/1000);
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

	//final score of player after game has ended
	public int getFinalScore() {
		return finalScore;
	}
	
	public String getName() {
		return name;
	}

	//used in the command class to send the feedback i.e. streak LED
	public boolean getMultiplier() {
		return multiplier;
	}

}
