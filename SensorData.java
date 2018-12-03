import java.io.*;
import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SensorData implements Subject, DataInput {

	private ArrayList<Observer> observers;

	// Two bytes are received from the zigbee stream
	// SensorData class reads the two bytes and updates the observers with the data
	// read
	// observers will be the Player class and Sound class
	private byte byteOne, byteTwo;

	public SensorData() {
		observers = new ArrayList<Observer>();
	}

	public void registerObserver(Observer o) {
		observers.add(o);
	}

	public void removeObsever(Observer o) {
		observers.remove(observers.indexOf(o));
	}

	// all observers are updated with new data
	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			Observer observer = (Observer) observers.get(i);
			observer.update(byteOne, byteTwo);
		}
	}

	// after receiving the two bytes from the zigbee stream, all observers are
	// notified
	public void getData() {
		try {
			byteOne = readByte();
			System.out.print(" \n Reading From Byte 1 ");
			System.out.println(Integer.toBinaryString(byteOne));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			byteTwo = readByte();

			System.out.println("Reading From Byte 2");
		} catch (IOException e) {
			e.printStackTrace();
		}

//		byteOne = 122;
//	    byteTwo = 123;
		notifyObservers();

	}

	// DataInput methods
	@Override
	public boolean readBoolean() throws IOException {
		return false;
	}

	// this function reads a single byte from the zigbee stream
	public byte readByte() throws IOException {
		SerialPort serialPort = new SerialPort("COM6");
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

}
