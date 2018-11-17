import java.io.*;
import jssc.SerialPort;
import jssc.SerialPortException;

public class CommunicationThread implements Runnable, DataInput {

	private CommuncationSubject cs;
	private ScoreObserver so;
	private int rounds;
	private byte byteOne, byteTwo;
	Player p;
	Command cmd;


	
	
	// Takes a player and runs the game for this player - having his name and setting his score
	// Takes a command to have the difficulty set
	public CommunicationThread(Player p, Command c) {
		
		this.cmd = c;
		this.p = p;
		rounds = 0;
		cs = new CommuncationSubject(p);
		so = new ScoreObserver(p);
		cs.registerObserver(so);
		Thread t = new Thread(this);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean readBoolean() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte readByte() throws IOException {
		// TODO Auto-generated method stub
		SerialPort serialPort = new SerialPort("COM3");
		byte[] p = new byte[1];
		try {
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);
			p = serialPort.readBytes(1);
			serialPort.closePort();
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
		return p[0];
	}

	@Override
	public char readChar() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double readDouble() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float readFloat() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void readFully(byte[] arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFully(byte[] arg0, int arg1, int arg2) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int readInt() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String readLine() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long readLong() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short readShort() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String readUTF() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int readUnsignedByte() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int readUnsignedShort() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int skipBytes(int arg0) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// 15 shots for the player
		while (rounds <= 15) {
			
			cmd.setDifficulty(DifficultyMenu.getDifficulty());
			cmd.sendCommand();
			
//			try {
//				byteOne = readByte();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			try {
				byteTwo = readByte();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
	
			//byteOne = 0b00100000;
			byteOne = 0b01100000;
			
			
			cs.getData(byteOne, byteTwo);
			
			System.out.println();
			rounds++;

		}

	}

}
