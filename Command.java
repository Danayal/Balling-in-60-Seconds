import java.io.*;

import jssc.SerialPort;
import jssc.SerialPortException;

public class Command implements DataOutput {

	Player p;
	int difficulty;
	
	public Command(Player p, int d) {
		this.p = p;
		difficulty = d;
	}

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
		int testAcc = getAcceleration();
		int testTarget = getTarget();
		int testSpin = getSpin();

		/*
		 * Byte configuration: [7][6][5][4][3][2][1][0] [7][6] --> identifying bits [5]
		 * --> success bit [4] --> multiplier(streak) bit [3] --> Acceleration command
		 * (High/Low jerk) [2] --> Spin bit [1][0] --> Target bits (Target number
		 * identifier)
		 */

		byte Acc, Spin, Tar, streak, packet, diff;

		// ------------------------------------------------
		if (testAcc == 0) {
			Acc = (byte) 0b0000;
		} else {
			Acc = (byte) 0b1000;
		}
		// -------------------------------------------------

		// -------------------------------------------------
		if (testTarget == 1) {
			Tar = (byte) 0b00;
		} else if (testTarget == 2) {
			Tar = (byte) 0b01;
		} else if (testTarget == 3) {
			Tar = (byte) 0b10;
		} else {
			Tar = (byte) 0b11;
		}
		// ------------------------------------------------

		// --------------------------------------------------
		if (testSpin == 0) {
			Spin = (byte) 0b000;
		} else {
			Spin = (byte) 0b100;
		}
		// ---------------------------------------------------

		// -----------------------------------------------------
		if (p.getMultiplier()) { // Set true when the player has 2 consecutive success shots
			streak = (byte) 0b10000;
		} else {
			streak = (byte) 0b0;
		}
		//--------------------------------------------------
		
		//--------------------------
		if(difficulty == 0) {
			diff = (byte) 0b00000000;
		}
		else if(difficulty == 1) {
			diff = (byte) 0b01000000;
		}
		else if(difficulty == 2) {
			diff = (byte) 0b10000000;
		}
		else {
			diff = (byte) 0b11000000;
		}

		packet = (byte) (Tar | Spin | streak | Acc | diff);
		byte[] packet_arr = new byte[1];
		packet_arr[0] = packet;

//		try {
//			write(packet_arr); // Send the byte to the serial port
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void write(int arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(byte[] arg0) throws IOException {
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
