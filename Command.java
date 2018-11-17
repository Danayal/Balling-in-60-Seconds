import java.io.*;

import jssc.SerialPort;
import jssc.SerialPortException;

public class Command implements DataOutput {

	private Player p;
	private int difficulty;
	DifficultyMenu d;
	
	//Player passed to indicate which player gets which command

	public Command(Player pl) {
		difficulty = 0;
		this.p = pl;
	}
	
	public String getPlayerName() {
		return p.getName();
	}
	public int getFinalScore() {
		return p.getFinalScore();
	}
	
	public Player getPlayer() {
		return p;
	}
	
	//difficulty will be set by the user in the gui
	public void setDifficulty(int d) {
		difficulty = d;
	}

	//Creating random numbers for the commands
	private int getAcceleration() {
		return Randomized.getRandBinary();
	}

	private int getTarget() {
		return Randomized.getRandTarget();
	}

	private int getSpin() {
		return Randomized.getRandBinary();
	}

	public void sendCommand() {
		
		//Random generated variables
		int testAcc = getAcceleration();
		int testTarget = getTarget();
		int testSpin = getSpin();

		/*
		 * Byte configuration: [7][6][5][4][3][2][1][0] [7][6] --> difficulty bits [5]
		 * --> success bit [4] --> multiplier(streak) bit [3] --> Acceleration command
		 * (High/Low jerk) [2] --> Spin bit [1][0] --> Target bits (Target number
		 * identifier)
		 */

		byte Acc=0, Spin=0, Tar=0, streak=0, packet=0, diff=0;

		//high/low jerk
		if (testAcc == 0) {
			Acc = (byte) 0b0000;
			System.out.println("low jerk");
		} else {
			Acc = (byte) 0b1000;
			System.out.println("high jerk");
		}

		//target number player should hit
		if (testTarget == 1) {
			Tar = (byte) 0b00;
		} else if (testTarget == 2) {
			Tar = (byte) 0b01;
		} else if (testTarget == 3) {
			Tar = (byte) 0b10;
		} else {
			Tar = (byte) 0b11;
		}

		//if spin generator is 1 and difficulty is hard or expert then send a command spin
		boolean enableSpin = (difficulty == 2 || difficulty == 3);
		if (testSpin == 1 && enableSpin) {
			Spin = (byte) 0b100;
			System.out.println("spin");
		} else {
			Spin = (byte) 0b000;
			System.out.println("no spin");
		}

		// Set true when the player has 2 consecutive success shots
		if (p.getMultiplier()) { 
			streak = (byte) 0b10000;
			System.out.println("multiplier");
		} else {
			streak = (byte) 0b0;
			System.out.println("no multiplier");
		}
		
		if(difficulty == 0) {
			diff = (byte) 0b00000000; //easy mode
		}
		else if(difficulty == 1) {
			diff = (byte) 0b01000000; //normal mode
		}
		else if(difficulty == 2) {
			diff = (byte) 0b10000000; //hard mode
		}
		else {
			diff = (byte) 0b11000000; //expert mode
		}
		System.out.println(difficulty);

		//bit-wise ORing to send the final byte to arduino
		packet = (byte) (Tar | Spin | streak | Acc | diff);
		byte[] packet_arr = new byte[1];
		packet_arr[0] = packet;

		try {
			write(packet_arr); // Send the byte to the serial port
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void write(int arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(byte[] arg0) throws IOException {
		// TODO Auto-generated method stub
		SerialPort serialPort = new SerialPort("COM3");
		try {
			serialPort.openPort(); // open serial port
			serialPort.setParams(serialPort.BAUDRATE_9600, serialPort.DATABITS_8, serialPort.STOPBITS_1,
					serialPort.PARITY_NONE);
			serialPort.writeByte(arg0[0]);
			serialPort.closePort();
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void write(byte[] arg0, int arg1, int arg2) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeBoolean(boolean arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeByte(int arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeBytes(String arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeChar(int arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeChars(String arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeDouble(double arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeFloat(float arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeInt(int arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeLong(long arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeShort(int arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeUTF(String arg0) throws IOException {
		// TODO Auto-generated method stub

	}

}
