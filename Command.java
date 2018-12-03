import java.io.*;

import jssc.SerialPort;
import jssc.SerialPortException;

public abstract class Command implements DataOutput {

	/*
	 * The command class makes use of the template design pattern The default "recipe"
	 * is to add randomly generated required Jerks (ACC), Target numbers, streak
	 * values However the Command has 3 children Easy, Medium and Hard. These will
	 * add their own generateSpin and generateDifficulty methods. Because we
	 * implemented Template Design Pattern in the command class, we do not have to
	 * code multiple classes that handle byte commands to arduino depending on
	 * difficulty (with alot of common methods and implementations).
	 * UTILIZES ZIGBEE WIRELESS MODULE
	 */
	private Player player;

	// bytes for different attributes
	// byte configuration --> [7][6][5][4][3][2][1][0]
	private byte Acc; // bit [3]
	protected byte Spin; // bit [2]
	private byte Tar; // bits [1][0]
	private byte streak; // bit [4]
	// difficulty bits sent to arduino to set delay between shots
	protected byte difficulty; // bits[7][6]

	// final byte packet that will be broadcasted
	private byte packet;

	// constructor
	// Command needs to take a player to send streak LED command
	public Command(Player p) {
		player = p;
	}

	public void sendCommand() {

		generateTarget(); // common method
		generateJerk(); // common method
		generateSpin(); // different implementations for different difficulties
		generateDifficulty(); // different implementations for different difficulties
		generateStreak(); // common method
		sendPacket(); // common method

	}

	// Target number is randomly generated, and Target bits are set accordingly
	void generateTarget() {
		int testTarget = Randomized.getRandTarget();
		if (testTarget == 1) {
			Tar = (byte) 0b00;
		} else if (testTarget == 2) {
			Tar = (byte) 0b01;
		} else if (testTarget == 3) {
			Tar = (byte) 0b10;
		} else {
			Tar = (byte) 0b11;
		}
	}

	// High/Low jerk command is randomly generated, and Acceleration bit is set
	// accordingly
	void generateJerk() {
		int testAcc = Randomized.getRandBinary();
		if (testAcc == 0) {
			Acc = (byte) 0b0000;
		} else {
			Acc = (byte) 0b1000;
		}
	}

	// Streak Led is used for feedback
	// Using the player's class, the command class can know if the player is on a
	// streak
	// if player is on a streak, generate a feedback LED
	void generateStreak() {
		if (player.getMultiplier()) {
			streak = (byte) 0b10000;
		} else {
			streak = (byte) 0b0;
		}
	}

	void sendPacket() {
		//UTILIZES ZIGBEE WIRELESS MODULE

		// bit-wise ORing to send the final byte to the stream
		packet = (byte) (Tar | Spin | streak | Acc | difficulty);
		byte[] packet_arr = new byte[1];
		packet_arr[0] = packet;

		// Using DataOutput write method to write the packet to the stream
		try {
			write(packet_arr); // Send the byte to the serial port + UTILIZES ZIGBEE WIRELESS MODULE
			
			System.out.println(Integer.toBinaryString(packet_arr[0]) + " Sent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Methods that will be implemented by the 3 template children, Easy, Medium and
	// Hard
	abstract void generateSpin();

	abstract void generateDifficulty();

	// DataOutput methods

	@Override
	public void write(int arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	// this function will accept an array of bytes and write the bytes one by one to
	// SETUP THE ZIGBEE SERIAL PORT STREAM
	@Override
	public void write(byte[] arg0) throws IOException { //UTILIZES ZIGBEE WIRELESS MODULE
		// TODO Auto-generated method stub
		SerialPort serialPort = new SerialPort("COM6");
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

	//Unimplemented Methods
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
